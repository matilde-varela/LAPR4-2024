package followup.server.threads;

import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.network.FollowUpRequestCodes;
import jobs4u.base.network.SerializationUtil;
import jobs4u.base.network.data.DataBlock;
import jobs4u.base.network.data.DataDTO;
import jobs4u.base.usermanagement.domain.BasePasswordPolicy;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientConnectionThread implements Runnable {

    private final Socket socket;
    private DataOutputStream sOut;
    private DataInputStream sIn;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);


    public ClientConnectionThread(Socket clientSock) {
        socket = clientSock;
        try {
            sOut = new DataOutputStream(socket.getOutputStream());
            sIn = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.error("Error while creating socket", e);
        }
    }

    @Override
    public void run() {
        Thread requestThread = null;
        DataDTO dataDTO;
        int code;

        try {
            dataDTO = receiveRequest();
        } catch (IOException e) {
            LOGGER.error("Error while receiving request", e);
            throw new RuntimeException(e + "\nCouldn't read first data request from input stream.");
        }

        if (!authenticationProtocol(dataDTO)) {
            sendEmptyResponse(FollowUpRequestCodes.ERR);
            closeConnection();
            return;
        } else {
            sendEmptyResponse(FollowUpRequestCodes.ACK);
        }

        do {
            try {
                dataDTO = receiveRequest();
                code = dataDTO.code();

                if (code == FollowUpRequestCodes.EMAIL.getCode()) {
                    requestThread = new Thread(new EmailSendingRequestThread(dataDTO, new DataOutputStream(socket.getOutputStream())));

                } else if (code == FollowUpRequestCodes.UNSEENNOTIFLIST.getCode()) {
                    requestThread = new Thread(new NotificationRequestThread(dataDTO, new DataOutputStream(socket.getOutputStream())));

                } else if (code == FollowUpRequestCodes.APPLIST.getCode() || code == FollowUpRequestCodes.JOBOPLIST.getCode()) {
                    requestThread = new Thread(new ListingRequestThread(dataDTO, new DataOutputStream(socket.getOutputStream())));

                } else if (code == FollowUpRequestCodes.COMMTEST.getCode()) {
                    sendEmptyResponse(FollowUpRequestCodes.ACK);

                } else if (code == FollowUpRequestCodes.SEENNOTIFLIST.getCode()) {
                    requestThread = new Thread(new NotificationRequestThread(dataDTO, new DataOutputStream(socket.getOutputStream())));

                }else if (code == FollowUpRequestCodes.CHECKNOTIF.getCode()) {
                    requestThread = new Thread(new NotificationRequestThread(dataDTO, new DataOutputStream(socket.getOutputStream())));

                }else if (code == FollowUpRequestCodes.DISCONN.getCode()){
                    break;
                }
            } catch (IOException e) {
                LOGGER.error("Error while receiving request", e);
                throw new RuntimeException(e + "\nCouldn't create thread.\n");
            }

            if (code != FollowUpRequestCodes.DISCONN.getCode()) {
                assert requestThread != null;
                requestThread.start();

                try {
                    requestThread.join();
                } catch (InterruptedException e) {
                    LOGGER.error("Error while waiting for request", e);
                    throw new RuntimeException(e + "\nThread join error.");
                }
            }

        } while (code != FollowUpRequestCodes.DISCONN.getCode());

        sendEmptyResponse(FollowUpRequestCodes.ACK);
        closeConnection();
    }

    private DataDTO receiveRequest() throws IOException {
        int dataLength = sIn.readInt();
        byte[] dataBytes = new byte[dataLength];
        sIn.readFully(dataBytes);
        return DataDTO.fromByteArray(dataBytes);
    }

    private void sendEmptyResponse(FollowUpRequestCodes code) {
        DataDTO dataDTO = new DataDTO(code.getCode());
        try {
            byte[] bytes = dataDTO.toByteArray();
            sOut.writeInt(bytes.length);
            sOut.write(bytes);
            sOut.flush();
        } catch (IOException e) {
            LOGGER.error("Error while sending empty response.", e);

        }
    }

    public boolean authenticationProtocol(DataDTO dataDto) {
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(), new PlainTextEncoder());
        AuthenticationService authenticationService = AuthzRegistry.authenticationService();
        List<DataBlock> dataBlocks = dataDto.dataBlockList();

        Username username = (Username) SerializationUtil.deserialize(dataBlocks.get(0).data());
        String password = (String) SerializationUtil.deserialize(dataBlocks.get(1).data());

        return authenticationService.authenticate(username.toString(), password, BaseRoles.CANDIDATE_USER, BaseRoles.CUSTOMER_USER,
                BaseRoles.CUSTOMER_MANAGER).isPresent();
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.error("Error while closing connection.", e);
            throw new RuntimeException(e + "\nCouldn't close socket.");
        }
    }

}
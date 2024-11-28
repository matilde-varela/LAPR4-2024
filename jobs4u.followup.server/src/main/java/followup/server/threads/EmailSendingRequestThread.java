package followup.server.threads;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.network.FollowUpRequestCodes;
import jobs4u.base.network.SerializationUtil;
import jobs4u.base.network.data.DataBlock;
import jobs4u.base.network.data.DataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class EmailSendingRequestThread implements Runnable {

    private static final String SMTP_SERVER_DNS_NAME = "frodo.dei.isep.ipp.pt";
    private static final int FRODO_SMTP_PORT = 25;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);


    private final DataDTO data;
    private final DataOutputStream sOut;

    private String senderEmail;
    private String recipientEmail;
    private String topic;
    private String info;

    public EmailSendingRequestThread(DataDTO dataDTO, DataOutputStream dataOutputStream) {
        this.data = dataDTO;
        this.sOut = dataOutputStream;
    }

    @Override
    public void run() {
        Socket socket;
        PrintWriter commandPrinter;
        BufferedReader responseReader;

        //establish socket connection with frodo
        try {
            socket = new Socket(SMTP_SERVER_DNS_NAME, FRODO_SMTP_PORT);
            //sends commands to the server to perform actions, then clears buffer after
            commandPrinter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            //reads the smtp server responses line by line
            responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            LOGGER.error("Error while connecting to smtp server", e);
            throw new RuntimeException(e + "\nCouldn't connect to " + SMTP_SERVER_DNS_NAME);
        }

        processEmailData(data);

        //see SMTP Mail Message commands -> PL12 / Slide 10
        try {
            // Read server connection response
            System.out.println("Response: " + responseReader.readLine());

            commandPrinter.println("HELO " + SMTP_SERVER_DNS_NAME);
            commandPrinter.flush();
            System.out.println("HELO Response: " + responseReader.readLine());

            commandPrinter.println("MAIL FROM:<" + senderEmail + ">");
            commandPrinter.flush();
            System.out.println("MAIL FROM Response: " + responseReader.readLine());

            commandPrinter.println("RCPT TO:<" + recipientEmail + ">");
            commandPrinter.flush();
            System.out.println("RCPT TO Response: " + responseReader.readLine());

            commandPrinter.println("DATA");
            commandPrinter.flush();
            System.out.println("DATA Response: " + responseReader.readLine());

            // Send the email data
            commandPrinter.println("From: \"Jobs4U <automatic email>\" <" + senderEmail + ">");
            commandPrinter.println("To: <" + recipientEmail + ">");
            commandPrinter.println("Subject: " + topic);
            // empty line to indicate end of headers
            commandPrinter.println();
            commandPrinter.println(info);
            //signaling the end of the email message
            commandPrinter.println(".");

            // close connection (server side)
            commandPrinter.println("QUIT");
        } catch (IOException e) {
            LOGGER.error("Error while sending email.", e);
            System.out.println("Couldn't send email to " + recipientEmail);
        }

        try {
            sendEmptyResponse(FollowUpRequestCodes.ACK);
            socket.close();
        } catch (IOException e) {
            sendEmptyResponse(FollowUpRequestCodes.ERR);
            throw new RuntimeException(e + "\nCouldn't close socket");
        }

    }

    private void sendEmptyResponse(FollowUpRequestCodes code) {
        DataDTO dataDTO = new DataDTO(code.getCode());
        try {
            byte[] bytes = dataDTO.toByteArray();
            sOut.writeInt(bytes.length);
            sOut.write(bytes);
            sOut.flush();
        } catch (IOException e) {
            LOGGER.error("Error while sending empty response", e);
        }
    }

    private void processEmailData(DataDTO data) {
        List<DataBlock> dataBlocks = data.dataBlockList();

        senderEmail = (String) SerializationUtil.deserialize(dataBlocks.get(0).data());
        recipientEmail = (String) SerializationUtil.deserialize(dataBlocks.get(1).data());
        topic = (String) SerializationUtil.deserialize(dataBlocks.get(2).data());
        info = (String) SerializationUtil.deserialize(dataBlocks.get(3).data());
    }
}

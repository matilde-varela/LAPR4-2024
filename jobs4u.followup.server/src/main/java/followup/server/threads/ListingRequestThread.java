package followup.server.threads;

import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.network.FollowUpRequestCodes;
import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.network.SerializationUtil;
import jobs4u.base.network.data.DataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListingRequestThread implements Runnable {

    public final DataDTO data;
    private final DataOutputStream sOut;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);

    public ListingRequestThread(DataDTO dataDTO, DataOutputStream dataOutputStream) {
        this.data = dataDTO;
        this.sOut = dataOutputStream;
    }

    @Override
    public void run() {
        int code = data.code();
        Username username = (Username) SerializationUtil.deserialize(data.dataBlockList().get(0).data());
        DataDTO dataDTO = null;


        // check code
        //according to code redirection to the right service
        if (code == FollowUpRequestCodes.APPLIST.getCode()) {
            //get Application Service
            ApplicationManagementService appSrv = new ApplicationManagementService();
            Map<ApplicationDTO, Integer> applicationsDTOMap = appSrv.getApplicationsAndNumber(username);
            List<Map.Entry<ApplicationDTO, Integer>> applicationDTOList = new ArrayList<>(applicationsDTOMap.entrySet());


            dataDTO = new DataDTO(code);
            for (Map.Entry<ApplicationDTO, Integer> app : applicationDTOList) {
                byte[] key = SerializationUtil.serialize(app.getKey());
                dataDTO.addDataBlock(key.length, key);
                byte[] value = SerializationUtil.serialize(app.getValue());
                dataDTO.addDataBlock(value.length, value);
            }

        } else if (code == FollowUpRequestCodes.JOBOPLIST.getCode()) {
            //get Job Opening Service
            JobOpeningManagementService jobSrv = new JobOpeningManagementService();
            Iterable<JobOpeningDTO> jobOpeningsDTO = jobSrv.getJobOpeningFromUsername(username);
            dataDTO = DataDTO.turnListIntoDataDTO(code, jobOpeningsDTO);
        }

        //send response
        assert dataDTO != null;
        try {
            byte[] bytes = dataDTO.toByteArray();
            sOut.writeInt(bytes.length);
            sOut.write(bytes);
            sOut.flush();
        } catch (IOException e) {
            LOGGER.error("Couldn't send response.", e);
            //add logger comment
            throw new RuntimeException(e + "\nCouldn't serialize to stream and send response.\n");
        }
    }

}
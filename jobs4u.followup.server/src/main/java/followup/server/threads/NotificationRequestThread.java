package followup.server.threads;

import eapli.framework.time.util.Calendars;
import jobs4u.base.network.FollowUpRequestCodes;
import jobs4u.base.network.SerializationUtil;
import jobs4u.base.network.data.DataDTO;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;
import jobs4u.base.notificationmanagement.application.NotificationManagementService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationRequestThread implements Runnable {

    public final DataDTO data;
    private final DataOutputStream sOut;

    public NotificationRequestThread(DataDTO dataDTO, DataOutputStream dataOutputStream) {
        this.data = dataDTO;
        this.sOut = dataOutputStream;
    }

    @Override
    public void run() {
        // get the notificationManagementService
        NotificationManagementService notificationManagementService = new NotificationManagementService();
        Iterable<NotificationDTO> notificationsDTO = null;
        DataDTO dataDTO = null;

        if(data.code() == FollowUpRequestCodes.UNSEENNOTIFLIST.getCode()) {
            String username = (String) SerializationUtil.deserialize(data.dataBlockList().get(0).data());

            //get notificationDTO list
            //convert to dataDTO
            notificationsDTO = notificationManagementService.getUnseenNotificationsByUsername(username);
            dataDTO = DataDTO.turnListIntoDataDTO(data.code(), notificationsDTO);
        } else if(data.code() == FollowUpRequestCodes.SEENNOTIFLIST.getCode()) {
            String username = (String) SerializationUtil.deserialize(data.dataBlockList().get(0).data());

            String date = (String) SerializationUtil.deserialize(data.dataBlockList().get(1).data());

            Calendar newDate = null;

            try {
                newDate = Calendars.parse(date, "dd-MM-yyyy");
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
            //get notificationDTO list
            //convert to dataDTO
            notificationsDTO = notificationManagementService.getSeenNotificationsByUsernameAndDate(username, newDate);
            dataDTO = DataDTO.turnListIntoDataDTO(data.code(), notificationsDTO);
        } else if(data.code() == FollowUpRequestCodes.CHECKNOTIF.getCode()) {
            String username = (String) SerializationUtil.deserialize(data.dataBlockList().get(0).data());

            boolean answer = notificationManagementService.checkForUnseenNotificationsByUsername(username);
            dataDTO = new DataDTO(data.code());
            byte[] stringBytes = SerializationUtil.serialize(String.valueOf(answer));
            dataDTO.addDataBlock(stringBytes.length, stringBytes);
        }


        //send response
        try {
            byte[] bytes = dataDTO.toByteArray();
            sOut.writeInt(bytes.length);
            sOut.write(bytes);
            sOut.flush();
            sOut.flush();
        } catch (IOException e) {
            //add logger comment
            throw new RuntimeException(e + "\nCouldn't serialize to stream and send response.\n");
        }
    }

}

package jobs4u.base.notificationmanagement.dto;

import eapli.framework.validations.Preconditions;
import jobs4u.base.applicationmanagement.domain.ApplicationFile;
import jobs4u.base.notificationmanagement.domain.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class NotificationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Long id;
    private String notificationType;

    private String notificationRecipient;

    private String notificationBody;

    private Calendar notificationDate;

    private String notificationStatus;


    public NotificationDTO(Long id, String notificationType, String notificationRecipient, String notificationBody, Calendar notificationDate, String notificationStatus) {
        Preconditions.noneNull(id, notificationType, notificationRecipient, notificationBody, notificationDate, notificationStatus);
        this.id = id;
        this.notificationBody = notificationBody;
        this.notificationDate = notificationDate;
        this.notificationStatus = notificationStatus;
        this.notificationType = notificationType;
        this.notificationRecipient = notificationRecipient;
    }


    @Override
    public String toString() {
        return String.format("\n==================================================================\n" +
                "NOTIFICATION : %s\n" +
                "DATE : %s\n" +
                "==================================================================\n", notificationBody, notificationDate.getTime().toString());
    }

}


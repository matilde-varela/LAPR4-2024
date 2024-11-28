package jobs4u.base.applicationmanagement.application.eventhandlers;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.events.ApplicationStatusChangedEvent;
import jobs4u.base.notificationmanagement.application.NotificationManagementService;
import jobs4u.base.notificationmanagement.domain.*;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;

import java.util.Calendar;

public class NotifyCandidateOnApplicationStatusChangedController {

    final NotificationManagementService notificationManagementService = new NotificationManagementService();
    final JobOpeningManagementService jobOpeningManagementService = new JobOpeningManagementService();

    public void notifyCandidateOfChangedStatus(ApplicationStatusChangedEvent event) {
        Notification notification = new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CANDIDATE)),
                new NotificationRecipient(event.getEmailAddress().toString()),
                new NotificationBody(String.format("YOUR APPLICATION FOR THE JOB OF %s AT %s WAS %s.", jobOpeningManagementService.getJobOpeningByJobRef(event.getJobReference().toString()).get().function().toString().toUpperCase(), event.getJobReference().getCompanyCode(), event.getNewStatus())),
                new NotificationDate(Calendar.getInstance()),
                new NotificationStatus(String.valueOf(NotificationStatusEnum.UNSEEN)));

        notificationManagementService.saveNotification(notification);
    }

}

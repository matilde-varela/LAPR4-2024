package jobs4u.base.jobopeningmanagement.application.eventhandlers;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.events.ApplicationStatusChangedEvent;
import jobs4u.base.jobopeningmanagement.domain.events.JobOpeningPhaseChangedEvent;
import jobs4u.base.jobopeningmanagement.domain.events.JobOpeningResultsPublishedEvent;
import jobs4u.base.network.FollowUpConnectionService;
import jobs4u.base.notificationmanagement.application.NotificationManagementService;
import jobs4u.base.notificationmanagement.domain.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Optional;

public class NotifyCustomerOfChangeInJobOpeningPhasesController {

    final NotificationManagementService notificationManagementService = new NotificationManagementService();

    public void notifyCustomerOfChangedStatus(JobOpeningPhaseChangedEvent event) {
        if(!event.getNewPhase().equals("CONCLUDED")) {
            Notification notification = new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CUSTOMER)),
                    new NotificationRecipient(event.email().toString()),
                    new NotificationBody(String.format("YOUR JOB OPENING %s IS ON IT'S %s PHASE.", event.getJobReference().toString(), event.getNewPhase())),
                    new NotificationDate(Calendar.getInstance()),
                    new NotificationStatus(String.valueOf(NotificationStatusEnum.UNSEEN)));

            notificationManagementService.saveNotification(notification);
        }else{
            Notification notification = new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CUSTOMER)),
                    new NotificationRecipient(event.email().toString()),
                    new NotificationBody(String.format("YOUR JOB OPENING %s HAS CONCLUDED.", event.getJobReference().toString())),
                    new NotificationDate(Calendar.getInstance()),
                    new NotificationStatus(String.valueOf(NotificationStatusEnum.UNSEEN)));

            notificationManagementService.saveNotification(notification);
        }
    }


}

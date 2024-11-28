package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatusEnum;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class InMemoryNotificationRepository extends InMemoryDomainRepository<Notification, Long>
        implements NotificationRepository {
    @Override
    public boolean checkForUnseenNotificationsByUsername(String username) {
        return match(e -> e.notificationRecipient().recipientOfNotification().equals(username) && e.status().statusOfNotification().equals("UNSEEN")).iterator().hasNext();
    }

    @Override
    public Iterable<Notification> getUnseenNotificationsByUsername(String username) {
        return match(e -> e.notificationRecipient().recipientOfNotification().equals(username) && e.status().statusOfNotification().equals("UNSEEN"));
    }

    @Override
    public Iterable<Notification> getSeenNotificationsByUsernameAndDate(String username, Calendar date) {
        return match(e -> e.notificationRecipient().recipientOfNotification().equals(username) && e.status().statusOfNotification().equals("SEEN") && e.dateOfCreation().dateOfNotification().before(date));
    }
}

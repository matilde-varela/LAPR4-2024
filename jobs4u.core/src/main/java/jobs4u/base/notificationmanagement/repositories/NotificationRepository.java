package jobs4u.base.notificationmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface NotificationRepository extends DomainRepository<Long, Notification> {

    boolean checkForUnseenNotificationsByUsername(String username);

    Iterable<Notification> getUnseenNotificationsByUsername(String username);

    Iterable<Notification> getSeenNotificationsByUsernameAndDate(String username, Calendar date);

}

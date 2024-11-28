package jobs4u.base.notificationmanagement.application;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;
import jobs4u.base.notificationmanagement.dto.NotificationDTOService;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationManagementService {

    private final NotificationRepository notificationRepository = PersistenceContext.repositories().notifications();

    private final NotificationDTOService notificationDTOService = new NotificationDTOService();

    public boolean checkForUnseenNotificationsByUsername(String username) {
        return notificationRepository.checkForUnseenNotificationsByUsername(username);
    }

    public Iterable<NotificationDTO> getUnseenNotificationsByUsername(String username) {
        Iterable<Notification> notifications = notificationRepository.getUnseenNotificationsByUsername(username);
        for (Notification notification1 : notifications){
            notification1.notificationWasSeen();
            notificationRepository.save(notification1);
        }
        return notificationDTOService.convertToDTO(notifications);
    }

    public Iterable<NotificationDTO> getSeenNotificationsByUsernameAndDate(String username, Calendar date) {
        return notificationDTOService.convertToDTO(notificationRepository.getSeenNotificationsByUsernameAndDate(username,date));
    }

    public void saveNotification(Notification notification){
        notificationRepository.save(notification);
    }

}

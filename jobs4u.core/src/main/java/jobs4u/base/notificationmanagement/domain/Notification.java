package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import jobs4u.base.applicationmanagement.domain.*;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "T_NOTIFICATION")
public class Notification implements AggregateRoot<Long>, DTOable<NotificationDTO> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private NotificationType notificationType;

    private NotificationRecipient notificationRecipient;

    private NotificationBody notificationBody;

    private NotificationDate notificationDate;

    private NotificationStatus notificationStatus;

    public Notification(NotificationType notificationType, NotificationRecipient notificationRecipient, NotificationBody notificationBody, NotificationDate notificationDate, NotificationStatus notificationStatus) {
        Preconditions.noneNull(notificationType, notificationRecipient, notificationBody, notificationDate, notificationStatus);

        this.notificationBody = notificationBody;
        this.notificationDate = notificationDate;
        this.notificationStatus = notificationStatus;
        this.notificationType = notificationType;
        this.notificationRecipient = notificationRecipient;
    }

    public Notification() {
        // ORM
    }

    public NotificationBody message() {
        return notificationBody;
    }

    public NotificationDate dateOfCreation() {
        return notificationDate;
    }

    public NotificationRecipient notificationRecipient() {
        return notificationRecipient;
    }

    public NotificationStatus status() {
        return notificationStatus;
    }

    public NotificationType type() {
        return notificationType;
    }

    public void notificationWasSeen(){
        this.notificationStatus = new NotificationStatus(String.valueOf(NotificationStatusEnum.SEEN));
    }

    @Override
    public boolean sameAs(Object other) {
        if (this == other) return true;
        if (!(other instanceof Notification)) return false;
        Notification that = (Notification) other;
        return Objects.equals(id, that.id) && Objects.equals(notificationBody, that.notificationBody) &&
                Objects.equals(notificationDate, that.notificationDate) && Objects.equals(notificationRecipient, that.notificationRecipient) && Objects.equals(notificationType, that.notificationType) && Objects.equals(notificationStatus, that.notificationStatus);
    }

    @Override
    public Long identity() {
        return id;
    }

    @Override
    public NotificationDTO toDTO() {
        return new NotificationDTO(id, notificationType.typeOfNotification(), notificationRecipient.recipientOfNotification(), notificationBody.bodyOfNotification(), notificationDate.dateOfNotification(), notificationStatus.statusOfNotification());
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }


}

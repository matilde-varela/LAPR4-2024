package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Date;
import java.util.Objects;

@Embeddable
public class NotificationRecipient implements ValueObject {

    private String recipient;

    public NotificationRecipient(String recipient){
        Preconditions.noneNull(recipient);
        this.recipient = recipient;
    }

    public NotificationRecipient() {}

    public String recipientOfNotification() {
        return recipient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationRecipient)) return false;
        NotificationRecipient that = (NotificationRecipient) o;
        return Objects.equals(recipient, that.recipient);
    }

}

package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Date;
import java.util.Objects;

@Embeddable
public class NotificationStatus implements ValueObject {

    private String status;

    public NotificationStatus(String status){
        Preconditions.noneNull(status);
        this.status = status;
    }

    public NotificationStatus() {}

    public String statusOfNotification() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationStatus)) return false;
        NotificationStatus that = (NotificationStatus) o;
        return Objects.equals(status, that.status);
    }

}

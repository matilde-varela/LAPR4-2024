package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;
import jobs4u.base.applicationmanagement.domain.ApplicationStatus;

import java.util.Objects;

@Embeddable
public class NotificationType implements ValueObject {

    private String type;

    public NotificationType(String type){
        Preconditions.noneNull(type);
        this.type = type;
    }

    public NotificationType() {}

    public String typeOfNotification() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationType)) return false;
        NotificationType that = (NotificationType) o;
        return Objects.equals(type, that.type);
    }
}

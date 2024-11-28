package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class NotificationBody implements ValueObject {

    private String body;

    public NotificationBody(String body){
        Preconditions.noneNull(body);
        this.body = body;
    }

    public NotificationBody() {}

    public String bodyOfNotification() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationBody)) return false;
        NotificationBody that = (NotificationBody) o;
        return Objects.equals(body, that.body);
    }

}

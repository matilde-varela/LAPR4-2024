package jobs4u.base.notificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class NotificationDate implements ValueObject {

    private Calendar date;

    public NotificationDate(Calendar date){
        Preconditions.noneNull(date);
        this.date = date;
    }

    public NotificationDate() {}

    public Calendar dateOfNotification() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationDate)) return false;
        NotificationDate that = (NotificationDate) o;
        return Objects.equals(date, that.date);
    }

}

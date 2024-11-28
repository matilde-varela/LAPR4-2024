package jobs4u.base.jobopeningmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Description implements ValueObject {

    private String descriptionText;

    public Description(String text) {
        Preconditions.noneNull(text);
        Preconditions.nonEmpty(text);
        this.descriptionText = text;
    }

    public Description() {
        //for ORM
    }

    public static Description valueOf(final String text) {
        return new Description(text);
    }

    public String description() {
        return descriptionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description)) return false;
        Description that = (Description) o;
        return Objects.equals(descriptionText, that.descriptionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descriptionText);
    }
}

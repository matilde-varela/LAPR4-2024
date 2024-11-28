package jobs4u.base.interviewmodelmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class InterviewModelDescription implements ValueObject {

    private String description;

    public InterviewModelDescription(String description) {
        Preconditions.noneNull(description);
        Preconditions.nonEmpty(description);
        this.description = description;
    }

    public InterviewModelDescription() {
        //for ORM
    }

    public String description(){
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterviewModelDescription)) return false;
        InterviewModelDescription that = (InterviewModelDescription) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}

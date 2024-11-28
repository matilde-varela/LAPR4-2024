package jobs4u.base.interviewmodelmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class InterviewModelName implements ValueObject, Comparable<InterviewModelName> {

    private String name;

    public InterviewModelName(String name) {
        Preconditions.noneNull(name);
        Preconditions.nonEmpty(name);
        this.name = name;
    }

    public InterviewModelName() {
        //for ORM
    }

    @Override
    public int compareTo(InterviewModelName o) {
        return 0;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterviewModelName)) return false;
        InterviewModelName that = (InterviewModelName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

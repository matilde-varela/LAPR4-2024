package jobs4u.base.requirementsmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;

import java.util.Objects;

@Embeddable
public class RequirementName implements ValueObject, Comparable<RequirementName> {

    private String name;

    public RequirementName(String name) {
        Preconditions.noneNull(name);
        Preconditions.nonEmpty(name);
        this.name = name;
    }

    protected RequirementName() {
        //for ORM
    }

    @Override
    public int compareTo(RequirementName o) {
        return 0;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequirementName)) return false;
        RequirementName that = (RequirementName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

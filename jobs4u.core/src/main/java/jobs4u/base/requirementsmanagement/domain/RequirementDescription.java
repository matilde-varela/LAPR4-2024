package jobs4u.base.requirementsmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class RequirementDescription implements ValueObject {

    private String description;

    public RequirementDescription(String description) {
        Preconditions.noneNull(description);
        Preconditions.nonEmpty(description);
        this.description = description;
    }

    protected RequirementDescription() {
        //for ORM
    }

    public String description(){
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequirementDescription)) return false;
        RequirementDescription that = (RequirementDescription) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}

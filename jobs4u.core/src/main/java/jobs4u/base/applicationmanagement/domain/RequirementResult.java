package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class RequirementResult implements ValueObject {
    private Boolean requirementResult;

    private String reqJustification;

    protected RequirementResult(Boolean answer) {
        Preconditions.noneNull(answer);

        this.requirementResult = answer;
        this.reqJustification = "";
    }

    @Override
    public String toString() {
        return "requirementResult was " + requirementResult +
                ". Justification: '" + reqJustification +".";
    }
    protected RequirementResult() {}

    protected RequirementResult(Boolean answer, String reqJustification) {
        Preconditions.noneNull(answer, reqJustification);
        Preconditions.nonEmpty(reqJustification);

        this.reqJustification = reqJustification;
        this.requirementResult = answer;
    }

    public Boolean requirementResult() {
        return requirementResult;
    }

    public static RequirementResult valueOf(final Boolean answer) {
        return new RequirementResult(answer);
    }

    public static RequirementResult valueOf(final Boolean answer, final String justification) {
        return new RequirementResult(answer, justification);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequirementResult)) return false;
        RequirementResult that = (RequirementResult) o;
        return Objects.equals(requirementResult, that.requirementResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requirementResult);
    }
}

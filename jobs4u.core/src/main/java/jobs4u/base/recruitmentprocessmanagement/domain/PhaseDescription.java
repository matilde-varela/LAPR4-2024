package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

@Embeddable
public class PhaseDescription implements ValueObject {

    private String descriptionText;

    protected PhaseDescription() {
        // for ORM
    }

    public PhaseDescription(String descriptionText) {
        Preconditions.noneNull(descriptionText);
        Preconditions.nonEmpty(descriptionText);
        this.descriptionText = descriptionText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    @Override
    public String toString() {
        return descriptionText;
    }
}

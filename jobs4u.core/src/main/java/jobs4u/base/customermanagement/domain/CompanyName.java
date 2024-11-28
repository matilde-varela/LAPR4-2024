package jobs4u.base.customermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

@Embeddable
public class CompanyName implements ValueObject {

    private String companyName;

    public CompanyName(String companyName) {
        Preconditions.noneNull(companyName);
        Preconditions.nonEmpty(companyName);
        this.companyName = companyName;
    }

    protected CompanyName() {
    }

    public String companyName() {
        return this.companyName;
    }
}

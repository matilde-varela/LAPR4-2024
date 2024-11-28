package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

@Embeddable
public class RecruitmentProcessStatus implements ValueObject {

    private String statusDescription;

    protected RecruitmentProcessStatus(){
        //for ORM
    }

    public RecruitmentProcessStatus(String statusDescription) {
        Preconditions.noneNull(statusDescription);
        this.statusDescription = String.valueOf(statusDescription);
    }

    public RecruitmentProcessStatus(RecruitmentProcessStatusEnum statusDescription) {
        Preconditions.noneNull(statusDescription);
        this.statusDescription = String.valueOf(statusDescription);
    }

    public static RecruitmentProcessStatus valueOf(RecruitmentProcessStatusEnum enumVal) {
        return new RecruitmentProcessStatus(enumVal);
    }

    public String currentStatus() {
        return statusDescription;
    }
}

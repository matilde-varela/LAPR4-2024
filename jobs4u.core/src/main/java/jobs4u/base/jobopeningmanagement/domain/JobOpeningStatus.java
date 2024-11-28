package jobs4u.base.jobopeningmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

@Embeddable
public class JobOpeningStatus implements ValueObject {
    private String statusDescription;

    protected JobOpeningStatus() {
        //for ORM
    }

    public JobOpeningStatus(JobOpeningStatusEnum enumVal) {
        Preconditions.noneNull(enumVal);
        this.statusDescription = String.valueOf(enumVal);
    }

    public void setStatusDescriptionAsUNFINISHED(){
        this.statusDescription = String.valueOf(JobOpeningStatusEnum.UNFINISHED);
    }
    public void setStatusDescriptionAsSTARTED(){
        this.statusDescription = String.valueOf(JobOpeningStatusEnum.STARTED);
    }

    public void setStatusDescriptionAsNOT_STARTED(){
        this.statusDescription = String.valueOf(JobOpeningStatusEnum.NOT_STARTED);
    }

    public void setStatusDescriptionAsENDED(){
        this.statusDescription = String.valueOf(JobOpeningStatusEnum.ENDED);
    }

    public static JobOpeningStatus valueOf(JobOpeningStatusEnum enumVal) {
        return new JobOpeningStatus(enumVal);
    }

    public String getStatusDescription() {
        return statusDescription;
    }
}

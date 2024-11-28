package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ApplicationStatus implements ValueObject {

    private String statusDescription;

    protected ApplicationStatus(){
        //for ORM
    }

    public ApplicationStatus(ApplicationStatusEnum enumVal){
        Preconditions.noneNull(enumVal);
        this.statusDescription = String.valueOf(enumVal);
    }

    public void updateStatusDescriptionAsNOT_CHECKED(){
        this.statusDescription = String.valueOf(ApplicationStatusEnum.NOT_CHECKED);
    }
    public void updateStatusDescriptionAsACCEPTED(){
        this.statusDescription = String.valueOf(ApplicationStatusEnum.ACCEPTED);
    }

    public void updateStatusDescriptionAsREJECTED(){
        this.statusDescription = String.valueOf(ApplicationStatusEnum.REJECTED);
    }

    public static ApplicationStatus valueOf(ApplicationStatusEnum enumVal){
        return new ApplicationStatus(enumVal);
    }

    public String getStatusDescription(){
        return statusDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationStatus)) return false;
        ApplicationStatus that = (ApplicationStatus) o;
        return Objects.equals(statusDescription, that.statusDescription);
    }


}

package jobs4u.base.workmodemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;

import java.util.Objects;

@Entity
@Table(name = "T_WORKMODE")
public class WorkMode implements DTOable<WorkModeDTO>, ValueObject, AggregateRoot<String> {

    @Id
    private String denomination;

    public WorkMode(String denomination) {
        Preconditions.noneNull(denomination);
        Preconditions.nonEmpty(denomination, "Contract type denomination must not be empty");
        this.denomination = denomination;
    }

    protected WorkMode() {
        //for ORM
    }

    public static WorkMode valueOf(final String workModeDenomination) {
        return new WorkMode(workModeDenomination);
    }

    public String denomination() {
        return denomination;
    }

    @Override
    public WorkModeDTO toDTO() {
        return new WorkModeDTO(this.denomination);
    }

    @Override
    public boolean sameAs(Object other) {
        return false;
    }

    @Override
    public String identity() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkMode)) return false;
        WorkMode workMode = (WorkMode) o;
        return Objects.equals(denomination, workMode.denomination);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }
}

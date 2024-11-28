package jobs4u.base.contracttypemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;

import java.util.Objects;

@Entity
@Table(name = "T_CONTRACTTYPE")
public class ContractType implements DTOable<ContractTypeDTO>, ValueObject, AggregateRoot<String> {

    @Id
    private String denomination;

    public ContractType(String denomination) {
        Preconditions.noneNull(denomination);
        Preconditions.nonEmpty(denomination, "Contract type denomination must not be empty");
        this.denomination = denomination;
    }

    protected ContractType() {
        // Required by JPA
    }

    public static ContractType valueOf(String contractTypeDenomination) {
        return new ContractType(contractTypeDenomination);
    }

    public String getDenomination() {
        return denomination;
    }

    @Override
    public ContractTypeDTO toDTO() {
        return new ContractTypeDTO(denomination);
    }

    @Override
    public boolean sameAs(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        ContractType that = (ContractType) other;
        return denomination.equals(that.denomination);
    }

    @Override
    public String identity() {
        return denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContractType)) return false;
        ContractType that = (ContractType) o;
        return Objects.equals(getDenomination(), that.getDenomination());
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }
}
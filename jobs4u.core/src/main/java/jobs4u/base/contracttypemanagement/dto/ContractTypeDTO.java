package jobs4u.base.contracttypemanagement.dto;

import eapli.framework.validations.Preconditions;

import java.util.Objects;

public class ContractTypeDTO {

    private final String contractTypeName;

    public ContractTypeDTO(String denomination) {
        Preconditions.nonEmpty(denomination);
        Preconditions.noneNull(denomination);
        this.contractTypeName = denomination;
    }

    public String contractTypeName(){
        return contractTypeName;
    }

    @Override
    public String toString() {
        return contractTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContractTypeDTO)) return false;
        ContractTypeDTO that = (ContractTypeDTO) o;
        return Objects.equals(contractTypeName, that.contractTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractTypeName);
    }
}

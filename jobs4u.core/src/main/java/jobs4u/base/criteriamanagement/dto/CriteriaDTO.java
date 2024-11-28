package jobs4u.base.criteriamanagement.dto;

import eapli.framework.validations.Preconditions;

import java.util.Objects;


public class CriteriaDTO {

    private String denomination;

    protected CriteriaDTO() {
        //for ORM
    }

    public CriteriaDTO(String denomination) {
        Preconditions.noneNull(denomination);
        Preconditions.nonEmpty(denomination);
        this.denomination = denomination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CriteriaDTO)) return false;
        CriteriaDTO that = (CriteriaDTO) o;
        return Objects.equals(denomination, that.denomination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination);
    }

    public String getDenomination() {
        return denomination;
    }

    @Override
    public String toString() {
        return denomination;
    }
}

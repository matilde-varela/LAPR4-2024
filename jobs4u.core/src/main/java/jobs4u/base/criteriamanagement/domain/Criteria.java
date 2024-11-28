package jobs4u.base.criteriamanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobs4u.base.criteriamanagement.dto.CriteriaDTO;

@Entity
@Table(name = "T_CRITERIA")
public class Criteria implements DTOable<CriteriaDTO>, AggregateRoot<String> {
    @Id
    private String criteriaDenomination;

    //class where criteria is to be applied
    private String classNameCriteria;

    protected Criteria() {
        //for ORM
    }

    public Criteria(String criteriaDenomination, String classNameCriteria) {
        Preconditions.noneNull(criteriaDenomination, classNameCriteria);
        Preconditions.nonEmpty(criteriaDenomination);
        Preconditions.nonEmpty(classNameCriteria);
        this.criteriaDenomination = criteriaDenomination;
        this.classNameCriteria = classNameCriteria;
    }

    @Override
    public CriteriaDTO toDTO() {
        return new CriteriaDTO(criteriaDenomination);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public String identity() {
        return this.criteriaDenomination;
    }

    public String getCriteriaDenomination() {
        return criteriaDenomination;
    }

    public String getClassNameCriteria() {
        return classNameCriteria;
    }
}

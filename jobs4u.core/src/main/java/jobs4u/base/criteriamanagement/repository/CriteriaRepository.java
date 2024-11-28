package jobs4u.base.criteriamanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.criteriamanagement.domain.Criteria;

import java.util.List;

public interface CriteriaRepository
        extends DomainRepository<String, Criteria> {

        List<Criteria> jobOpeningCriteria();
}

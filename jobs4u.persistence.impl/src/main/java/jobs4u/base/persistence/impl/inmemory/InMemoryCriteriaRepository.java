package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.criteriamanagement.domain.Criteria;
import jobs4u.base.criteriamanagement.repository.CriteriaRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCriteriaRepository
        extends InMemoryDomainRepository<Criteria, String>
        implements CriteriaRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public List<Criteria> jobOpeningCriteria() {
        List<Criteria> criteria = new ArrayList<>();
        Iterable<Criteria> matches =  match(e -> e.getClassNameCriteria().equals("JobOpening"));

        for (Criteria criterion : matches) {
            criteria.add(criterion);
        }
        return criteria;
    }
}

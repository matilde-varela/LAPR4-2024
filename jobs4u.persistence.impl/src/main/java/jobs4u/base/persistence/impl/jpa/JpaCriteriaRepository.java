package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jobs4u.base.Application;
import jobs4u.base.criteriamanagement.domain.Criteria;
import jobs4u.base.criteriamanagement.repository.CriteriaRepository;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;

import java.util.ArrayList;
import java.util.List;

public class JpaCriteriaRepository
        extends JpaAutoTxRepository<Criteria, String, String>
        implements CriteriaRepository {


    public JpaCriteriaRepository(final TransactionalContext autoTx) {
        super(autoTx, "criteriaDenomination");
    }

    public JpaCriteriaRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "criteriaDenomination");
    }


    @Override
    public List<Criteria> jobOpeningCriteria() {
        List<Criteria> jobOpeningCriteria = new ArrayList<>();
        Iterable<Criteria> criteria = match("e.classNameCriteria = :name", "name", JobOpening.class.getSimpleName());

        for (Criteria criterion : criteria) {
            jobOpeningCriteria.add(criterion);
        }
        return jobOpeningCriteria;
    }
}

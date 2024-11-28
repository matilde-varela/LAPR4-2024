package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jobs4u.base.Application;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.repository.RecruitmentProcessRepository;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class JpaRecruitmentProcessRepository
        extends JpaAutoTxRepository<RecruitmentProcess, Long, Long>
        implements RecruitmentProcessRepository {

    public JpaRecruitmentProcessRepository(final TransactionalContext autoTx) {
        super(autoTx, "recruitmentID");
    }

    public JpaRecruitmentProcessRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "recruitmentID");
    }

    @Override
    public Optional<RecruitmentProcess> getRecruitmentProcessByJobReference(JobReference jobReference) {
        Map<String, Object> map = new HashMap<>();
        map.put("job", jobReference);
        return matchOne("e.jobOpening.jobReference = :job", map);
    }
}

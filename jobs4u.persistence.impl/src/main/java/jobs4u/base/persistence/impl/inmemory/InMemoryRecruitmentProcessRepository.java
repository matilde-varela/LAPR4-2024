package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.dto.RecruitmentProcessDTO;
import jobs4u.base.recruitmentprocessmanagement.repository.RecruitmentProcessRepository;

import java.util.Optional;

public class InMemoryRecruitmentProcessRepository
        extends InMemoryDomainRepository<RecruitmentProcess, Long>
        implements RecruitmentProcessRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<RecruitmentProcess> getRecruitmentProcessByJobReference(JobReference jobReference) {
        return matchOne(e -> e.identity().equals(jobReference));

    }


}

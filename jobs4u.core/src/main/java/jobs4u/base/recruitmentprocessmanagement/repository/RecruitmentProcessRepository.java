package jobs4u.base.recruitmentprocessmanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;

import java.util.Optional;

public interface RecruitmentProcessRepository
        extends DomainRepository<Long, RecruitmentProcess> {

    Optional<RecruitmentProcess> getRecruitmentProcessByJobReference(JobReference jobReference);

}

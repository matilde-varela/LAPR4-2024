package jobs4u.base.rankmanagement.persistence;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.rankmanagement.domain.Rank;

import java.util.Optional;

public interface RankRepository extends DomainRepository<JobReference, Rank> {

    Optional<Rank> getRankFromJobReference(String jobReference);
}

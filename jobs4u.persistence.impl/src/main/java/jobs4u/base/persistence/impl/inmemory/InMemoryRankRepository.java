package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.rankmanagement.domain.Rank;
import jobs4u.base.rankmanagement.persistence.RankRepository;

import java.util.Optional;

public class InMemoryRankRepository
        extends InMemoryDomainRepository<Rank, JobReference>
        implements RankRepository {

    @Override
    public Optional<Rank> getRankFromJobReference(String jobReference) {
        return matchOne(e -> e.jobReference().toString().equals(jobReference));
    }
}

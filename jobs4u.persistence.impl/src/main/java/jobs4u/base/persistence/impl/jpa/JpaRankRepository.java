package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;
import jobs4u.base.Application;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.rankmanagement.domain.Rank;
import jobs4u.base.rankmanagement.persistence.RankRepository;

import java.util.Optional;

public class JpaRankRepository
        extends JpaAutoTxRepository<Rank, JobReference, JobReference>
        implements RankRepository {

    public JpaRankRepository(final TransactionalContext autoTx) {
        super(autoTx, "jobReference");
    }

    public JpaRankRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "jobReference");
    }

    @Override
    public Optional<Rank> getRankFromJobReference(String jobReference) {
        String[] jobReferenceParts = jobReference.split("-");
        String query = "SELECT e \n" +
                "FROM Rank e \n" +
                "WHERE e.jobReference.companyCode = :code\n" +
                "AND e.jobReference.sequentialCode = :sequential";
        final TypedQuery<Rank> q = createQuery(query, Rank.class);
        q.setParameter("code", jobReferenceParts[0]);
        q.setParameter("sequential", jobReferenceParts[1]);
        return Optional.of(q.getSingleResult());
    }
}

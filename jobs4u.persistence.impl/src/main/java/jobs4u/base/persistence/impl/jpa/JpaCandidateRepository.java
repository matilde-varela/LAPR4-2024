package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;
import jobs4u.base.Application;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaCandidateRepository extends JpaAutoTxRepository<Candidate, Long, PhoneNumber>
        implements CandidateRepository {

    public JpaCandidateRepository(final TransactionalContext autoTx) {
        super(autoTx, "phoneNumber");
    }

    public JpaCandidateRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "phoneNumber");
    }

    @Override
    public Optional<Candidate> ofIdentity(PhoneNumber entityPhoneNumber) {
        return Optional.empty();
    }

    @Override
    public void deleteOfIdentity(PhoneNumber entityPhoneNumber) {

    }

    @Override
    public Optional<Candidate> findByUsername(Username name) {
        return Optional.empty();

    }
    @Override
    public Optional<Candidate> findByPhoneNumber(final PhoneNumber phoneNumber){
        final Map<String, Object> params = new HashMap<>();
        params.put("phoneNumber", phoneNumber);
        return matchOne("e.phoneNumber = :phoneNumber", params);
    }

    @Override
    public boolean checksIfExits(PhoneNumber number) {
        final Map<String, Object> params = new HashMap<>();
        params.put("phoneNumber", number);
        return matchOne("e.phoneNumber = :phoneNumber", params).isPresent();
    }

    @Override
    public Iterable<Candidate> findByActive(boolean b) {
        final TypedQuery<Candidate> q = createQuery(
                "SELECT c " +
                        "FROM Candidate c " +
                        "INNER JOIN SystemUser s " +
                        "ON c.systemUser.email = s.email " +
                        "WHERE s.active = :b",
                Candidate.class
        );
        q.setParameter("b", b);
        return q.getResultList();
    }
}

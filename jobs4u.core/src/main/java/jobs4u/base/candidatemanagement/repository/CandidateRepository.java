package jobs4u.base.candidatemanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;

import javax.swing.text.StyledEditorKit;
import java.util.Optional;

public interface CandidateRepository extends DomainRepository<PhoneNumber, Candidate> {
    Optional<Candidate> findByUsername(Username name);

    Optional<Candidate> findByPhoneNumber(final PhoneNumber number);

    boolean checksIfExits(final PhoneNumber number);

    Iterable<Candidate> findByActive(boolean b);
}

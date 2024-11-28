package jobs4u.base.candidatemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.Optional;

public class EnableDisableCandidateController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final static CandidateManagementService candidateService = new CandidateManagementService();
    private final UserManagementService userService = AuthzRegistry.userService();
    private final CandidateRepository candidateRepository= PersistenceContext.repositories().candidates();

    public Iterable<CandidateDTO> activeCandidates() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);

        return candidateService.activeCandidates();
    }
    public Iterable<CandidateDTO> deactivatedCandidates() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);

        return candidateService.deactivatedCandidates();
    }

    public void deactivateCandidate(final CandidateDTO candidateDTO) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);

        Candidate candidate = candidateService.getCandidateByPhoneNumber(candidateDTO.getCandidatePhoneNumber()).get();
        SystemUser user = userService.deactivateUser(candidate.user());
        candidate.updateUser(user);
        candidateRepository.save(candidate);
    }

    public void activateCandidate(final CandidateDTO candidateDTO) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);

        Candidate candidate = candidateService.getCandidateByPhoneNumber(candidateDTO.getCandidatePhoneNumber()).get();
        SystemUser user = userService.activateUser(candidate.user());
        candidate.updateUser(user);
        candidateRepository.save(candidate);
    }

}

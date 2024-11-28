package jobs4u.base.candidatemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.List;

public class ListCandidatesController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CandidateManagementService candidateManagementService = new CandidateManagementService();
    private final ListCandidatesDTOService dtoService = new ListCandidatesDTOService();


    public List<CandidateDTO> getCandidatesList(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);

        List<Candidate> candidatesList =candidateManagementService.getCandidatesList();

        return dtoService.toDTO(candidatesList);
    }
}

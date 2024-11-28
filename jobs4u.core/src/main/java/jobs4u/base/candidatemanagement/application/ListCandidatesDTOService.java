package jobs4u.base.candidatemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.validations.Preconditions;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.*;

public class ListCandidatesDTOService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public List<CandidateDTO> toDTO(List<Candidate> listToDisplay){
        Preconditions.noneNull(listToDisplay);
        Preconditions.nonEmpty(listToDisplay);

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);

        List<CandidateDTO> dtoList = new ArrayList<>();
        for (Candidate candidate : listToDisplay){
            dtoList.add(candidate.toDTO());
        }
        return dtoList;
    }


}

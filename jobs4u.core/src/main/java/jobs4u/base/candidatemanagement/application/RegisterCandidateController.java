package jobs4u.base.candidatemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.Optional;

public class RegisterCandidateController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final CandidateManagementService candidateManagementService = new CandidateManagementService();

    public boolean registerCandidate(String name, String email, String extension, String number){
        Optional<SystemUser> operator = authz.loggedinUserWithPermissions(BaseRoles.OPERATOR);
        PhoneNumber phoneNumber = new PhoneNumber(extension, number);
        operator.ifPresent(systemUser -> candidateManagementService.registerCandidate(name, email, phoneNumber));

        return true;
    }
}

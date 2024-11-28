package jobs4u.base.candidatemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.network.ConnectionRelatedController;
import jobs4u.base.network.FollowUpConnectionService;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ListApplicationsAndStateController implements ConnectionRelatedController {

    FollowUpConnectionService connectionService;
    AuthorizationService authorizationService;

    public ListApplicationsAndStateController() {
        this.connectionService = new FollowUpConnectionService();
        this.authorizationService = AuthzRegistry.authorizationService();
    }

    public Username getSessionCredentials() {
        Optional<UserSession> session = authorizationService.session();
        if (session.isPresent()) {
            SystemUser user = session.get().authenticatedUser();
            return user.identity();
        }
        throw new NoSuchElementException("No session found");
    }
    public Pair<Boolean, String> establishConnection(Username username, String password) {
        return connectionService.establishConnection(username, password);
    }

//    public List<ApplicationDTO> applicationDTOList(Username username){
//        return connectionService.receiveCandidateApplicationList(username);
//    }

    public List<Map.Entry<ApplicationDTO, Integer>> applicationDTOList(Username username){
        return connectionService.receiveCandidateApplicationAndNumberList(username);
    }

    public Pair<Boolean, String> closeConnection() {
        return FollowUpConnectionService.closeConnection();
    }
}

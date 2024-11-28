package jobs4u.base.jobopeningmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.network.ConnectionRelatedController;
import jobs4u.base.network.FollowUpConnectionService;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.NoSuchElementException;


public class ListCustomerJobOpeningsController implements ConnectionRelatedController {

    FollowUpConnectionService connectionService;
    AuthorizationService authorizationService;

    public ListCustomerJobOpeningsController(Username username, String password) {
        this.connectionService = new FollowUpConnectionService();
        this.authorizationService = AuthzRegistry.authorizationService();
        Pair <Boolean, String> pair = establishConnection(username, password);
        if (!pair.getLeft()) {
            throw new NoSuchElementException(pair.getRight());
        }
    }

    public Pair<Boolean, String> establishConnection(Username username, String password) {
       return connectionService.establishConnection(username, password);
    }

    public List<JobOpeningDTO> jobOpeningsDataList(Username username){
        return connectionService.receiveJobOpeningsDataList(username);
    }

    public Pair<Boolean, String> closeConnection() {
        return FollowUpConnectionService.closeConnection();
    }
}

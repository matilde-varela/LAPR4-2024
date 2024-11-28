package jobs4u.base.candidatemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.Password;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.network.FollowUpConnectionService;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.apache.commons.lang3.tuple.Pair;

import java.util.NoSuchElementException;
import java.util.Optional;

public class SendNotificationEmailController {
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final JobOpeningManagementService jobOpeningManagementService = new JobOpeningManagementService();

    private final FollowUpConnectionService followUpConnectionService = new FollowUpConnectionService();


    public Iterable<JobOpeningDTO> getJobOpeningsList() {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);

        return jobOpeningManagementService.activeJobOpenings();
    }

    private Pair<Boolean, String> establishConnection(Username username, String password) {
        return followUpConnectionService.establishConnection(username, password);
    }

    public boolean sendEmail(JobOpeningDTO jobOpeningDTO, String userPassword) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);
        try {
            //get job opening and applications list
            JobOpening jobOpening = jobOpeningManagementService.getJobOpening(jobOpeningDTO);
            Iterable<Application> applications = jobOpening.getApplications();
            //get customer manager
            Optional<SystemUser> customerManager = authorizationService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);

            String customerManagerEmail = "";
            if (customerManager.isPresent()) {
                customerManagerEmail = customerManager.get().email().toString();
            }
            //Conection to server
            Username user = customerManager.get().username();
            Pair<Boolean, String> connection = establishConnection(user, userPassword);

            if (!connection.getKey()) {
                throw new IllegalArgumentException("Error: Could not establish connection" + connection.getValue());
            }
            for (Application application : applications) {
                if (application.requirementResult() != null) {
                    followUpConnectionService.sendEmail(customerManagerEmail, application.candidate().email().toString(), "Verification Process Result", "Hello, this email is intended to inform you that the result of your verification process was: " + application.requirementResult().toString() + ". Best Regards.");
                }
            }
            FollowUpConnectionService.closeConnection();
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}

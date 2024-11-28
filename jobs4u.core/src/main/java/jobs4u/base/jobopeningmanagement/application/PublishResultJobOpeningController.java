package jobs4u.base.jobopeningmanagement.application;

import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.domain.events.ApplicationStatusChangedEvent;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.network.FollowUpConnectionService;
import jobs4u.base.rankmanagement.domain.Rank;
import jobs4u.base.rankmanagement.domain.RankOrder;
import jobs4u.base.rankmanagement.persistence.RankRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PublishResultJobOpeningController {
    private static final AuthorizationService authz = AuthzRegistry.authorizationService();
    private static final JobOpeningManagementService joSvc = new JobOpeningManagementService();
    private static final RankRepository rankRepository = PersistenceContext.repositories().ranks();
    private static final CustomerRepository customerRepository = PersistenceContext.repositories().customers();
    private static final EventPublisher dispatcher = InProcessPubSub.publisher();
    private static final ApplicationRepository appRepo = PersistenceContext.repositories().applications();
    private static final FollowUpConnectionService connectionService = new FollowUpConnectionService();
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);

    public Iterable<JobOpeningDTO> getJobOpeningsList() {
        Optional<SystemUser> customerManagerOpt = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);

        if (customerManagerOpt.isPresent()) {
            return joSvc.jobOpeningsInResultListOfCustomerManager(customerManagerOpt.get().username());
        } else {
            throw new IllegalArgumentException("Couldn't retrieve the job openings.");
        }
    }

    public void publishResultForJobOpening(JobOpeningDTO jobOpeningDTO, String userPassword) {
        Optional<SystemUser> customerManagerOpt = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);

        if (customerManagerOpt.isPresent()) {
            publishResults(jobOpeningDTO, customerManagerOpt.get().email(), userPassword);
        } else {
            throw new IllegalArgumentException("Couldn't retrieve logged in user.");
        }
    }

    private void publishResults(JobOpeningDTO jobOpeningDTO, EmailAddress customerManager, String userPassword) {
        Optional<Rank> r = rankRepository.ofIdentity(new JobReference(jobOpeningDTO.getJobReference()));

        if (r.isEmpty()) {
            throw new IllegalArgumentException("Coudln't obtain the rank for the selected job opening.");
        }

        Rank rank = r.get();
        final List<Candidate> acceptedCandidateList = new ArrayList<>();
        String newStatus;

        for (RankOrder entry : rank.rankOrder()) {
            if (entry.numberRanked() <= jobOpeningDTO.getNumVacancies()) {
                Application app = entry.acceptApplication();
                newStatus = app.applicationStatus().getStatusDescription();
                acceptedCandidateList.add(app.candidate());
                appRepo.save(app);
                sendEmail(customerManager, entry.candidateEmail(), rank.identity(), userPassword);
            } else {
                Application app = entry.rejectApplication();
                newStatus = app.applicationStatus().getStatusDescription();
                appRepo.save(app);
            }
            dispatcher.publish(new ApplicationStatusChangedEvent(entry.candidateEmail(), rank.identity(), newStatus));
        }
        Optional<Customer> customer = customerRepository.getCustomerByCustomerCode(jobOpeningDTO.getCustomerCode());
        sendEmail(customerManager, customer.get().customerUser().email(), acceptedCandidateList, rank.identity(), userPassword);
    }

    private void sendEmail(EmailAddress senderEmail, EmailAddress receiverEmail, JobReference jo, String userPassword) {
        try {
            Username user = getSessionCredentials();
            Pair<Boolean, String> connection = establishConnection(user, userPassword);
            if (!connection.getKey()) {
                throw new IllegalArgumentException("Error: Could not establish connection" + connection.getValue());
            }
            String text = "Hello, this is Jobs4u, we came to tell you that your application has been accepted for the job opening: " + jo.toString();
            connectionService.sendEmail(senderEmail.toString(), receiverEmail.toString(), "JobOpening Result", text);
            FollowUpConnectionService.closeConnection();
        } catch (NoSuchElementException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void sendEmail(EmailAddress senderEmail, EmailAddress receiverEmail, List<Candidate> cand, JobReference jo, String userPassword) {
        try {
            Username user = getSessionCredentials();
            Pair<Boolean, String> connection = establishConnection(user, userPassword);
            if (!connection.getKey()) {
                throw new IllegalArgumentException("Error: Could not establish connection" + connection.getValue());
            }
            connectionService.sendEmail(senderEmail.toString(), receiverEmail.toString(), "JobOpening Result", emailInfo(jo, cand));
            FollowUpConnectionService.closeConnection();
        } catch (NoSuchElementException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private String emailInfo(JobReference jobReference, List<Candidate> acceptedCandidatesList) {
        StringBuilder builder = new StringBuilder();
        String information = "Hello, this is Jobs4u, we came to tell you that the candidates have been selected for the job opening: ";
        builder.append(information).append(jobReference.toString()).append("\n\n").append("Candidates Information:\n");
        for (Candidate candidate : acceptedCandidatesList) {
            builder.append(candidate.toString()).append("\n");
        }
        return builder.toString();
    }

    private Username getSessionCredentials() {
        Optional<UserSession> session = authz.session();
        if (session.isPresent()) {
            SystemUser user = session.get().authenticatedUser();
            return user.identity();
        }
        throw new NoSuchElementException("No session found");
    }

    private Pair<Boolean, String> establishConnection(Username username, String password) {
        return connectionService.establishConnection(username, password);
    }
}

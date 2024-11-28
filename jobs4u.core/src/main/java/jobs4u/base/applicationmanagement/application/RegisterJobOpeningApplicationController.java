package jobs4u.base.applicationmanagement.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.domain.ApplicationFile;
import jobs4u.base.candidatemanagement.application.RegisterCandidateController;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.domain.events.ApplicationStatusChangedEvent;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;


import java.util.*;

@UseCaseController
public class RegisterJobOpeningApplicationController {

    private final RegisterCandidateController controller = new RegisterCandidateController();

    private final AuthorizationService authz;

    private final CandidateRepository candidateRepository;

    private final EventPublisher dispatcher;

    private final ApplicationManagementService applicationManagementService;

    private final JobOpeningRepository jobOpeningRepository;

    private final JobOpeningManagementService jobOpeningManagementService;

    public RegisterJobOpeningApplicationController(){
        this.dispatcher = InProcessPubSub.publisher();
        this.authz = AuthzRegistry.authorizationService();
        this.applicationManagementService = new ApplicationManagementService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.candidateRepository = PersistenceContext.repositories().candidates();
        this.jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
    }

    public List<JobOpeningDTO> getJobOpeningsList() {
        return jobOpeningManagementService.getJobOpeningsList();
    }

    public boolean registerCandidate(String name, String email, String extension, String number){
        return controller.registerCandidate(name, email, extension, number);
    }

    public Optional<Application> registerApplication(Set<ApplicationFile> files, Date applicationDate, PhoneNumber phone, JobOpeningDTO jobOpeningDTO){

        Optional<Application> application = registerApplication(files, applicationDate, phone);

        if (application.isPresent()){
            JobOpening jobOpening = jobOpeningManagementService.getJobOpening(jobOpeningDTO);

            Set<Application> orign = jobOpening.getApplications();

            Set<Application> newApplications = new HashSet<>(orign);
            newApplications.add(application.get());

            jobOpening.setApplications(newApplications);
            jobOpeningRepository.save(jobOpening);
        }

        dispatcher.publish(new ApplicationStatusChangedEvent(application.get().candidate().email(), new JobReference(jobOpeningDTO.getJobReference()), "RECEIVED"));

        return application;
    }

    private Optional<Application> registerApplication(Set<ApplicationFile> files, Date applicationDate, PhoneNumber phone){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);

        Optional<Candidate> opCandidate = candidateRepository.findByPhoneNumber(phone);

        if (opCandidate.isPresent()){
            Candidate candidate = opCandidate.get();
            return Optional.of(applicationManagementService.registerApplication(files,
                    applicationDate, candidate));
        } else {
            return Optional.of(applicationManagementService.registerApplicationWithoutCandidate(files, applicationDate));
        }
    }

    public boolean getCandidate(PhoneNumber number) {
        Optional<Candidate> opCandidate = candidateRepository.findByPhoneNumber(number);

        return opCandidate.isPresent();
    }
}

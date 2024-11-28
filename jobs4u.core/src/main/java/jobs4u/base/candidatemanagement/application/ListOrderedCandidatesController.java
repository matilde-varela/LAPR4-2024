package jobs4u.base.candidatemanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ListOrderedCandidatesController {

    private final AuthorizationService authz;

    private final CandidateRepository candidateRepository;

    private final CandidateManagementService candidateManagementService;

    private final ApplicationManagementService applicationManagementService;

    private final ApplicationRepository applicationRepository;

    private final JobOpeningRepository jobOpeningRepository;

    private final JobOpeningManagementService jobOpeningManagementService;


    public ListOrderedCandidatesController(){
        this.authz = AuthzRegistry.authorizationService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.applicationManagementService = new ApplicationManagementService();
        this.candidateManagementService = new CandidateManagementService();
        this.jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
        this.applicationRepository = PersistenceContext.repositories().applications();
        this.candidateRepository = PersistenceContext.repositories().candidates();
    }

    public Iterable<JobOpeningDTO> getJobOpeningList() {
        Optional<SystemUser> customerManager = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);
        return customerManager.map(systemUser -> jobOpeningManagementService.getJobOpeningListInAnalysisPhase(systemUser.username())).orElse(null);
    }

    public List <ApplicationDTO> getApplicationsOrderedByInterviewResult(JobOpeningDTO jobOpeningDTO){
        JobOpening job = jobOpeningManagementService.getJobOpening(jobOpeningDTO);

        List<ApplicationDTO> applicationListDTO = applicationManagementService.getApplicationsWithInterviewGrade(job);

        List<ApplicationDTO> orderApplicationDTOList = applicationManagementService.getApplicationsOrderedByInterviewResult(applicationListDTO);

        return orderApplicationDTOList;
    }
}

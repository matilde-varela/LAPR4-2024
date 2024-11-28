package jobs4u.base.applicationmanagement.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UseCaseController
public class ListJobOpeningApplicationsController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final JobOpeningManagementService jobOpeningManagementService = new JobOpeningManagementService();

    private final ApplicationManagementService applicationManagementService = new ApplicationManagementService();

    public List<JobOpeningDTO> getJobOpeningsList() {
        return jobOpeningManagementService.getJobOpeningsList();
    }

    public List<ApplicationDTO> getApplicationsList(JobOpeningDTO jobOpeningDTO) {
        JobOpening jobOpening = jobOpeningManagementService.getJobOpening(jobOpeningDTO);

        List<ApplicationDTO> applicationDTO = new ArrayList<>();
        if (jobOpening != null){
            applicationDTO = applicationManagementService.getApplicationsList(jobOpening);
        }

        return applicationDTO;
    }

}

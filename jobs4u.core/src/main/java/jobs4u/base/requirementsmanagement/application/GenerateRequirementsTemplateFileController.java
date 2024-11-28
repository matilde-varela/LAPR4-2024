package jobs4u.base.requirementsmanagement.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.jobopeningmanagement.application.JobOpeningListDTOService;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;

@UseCaseController
public class GenerateRequirementsTemplateFileController {

    private final AuthorizationService authz;

    private final JobOpeningManagementService jobOpeningManagementService;

    private final JobOpeningListDTOService jobOpeningListDTOService;

    private final RequirementsTemplateManagerService requirementsTemplateManagerService;


    public GenerateRequirementsTemplateFileController() {
        this.authz = AuthzRegistry.authorizationService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.jobOpeningListDTOService = new JobOpeningListDTOService();
        this.requirementsTemplateManagerService = new RequirementsTemplateManagerService();
    }

    public List<JobOpeningDTO> getJobOpeningList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);
        List<JobOpening> jobOpenings = new ArrayList<>();
        for (JobOpening job : jobOpeningManagementService.getUNFINISHEDJobOpenings()){
            jobOpenings.add(job);
        }
        return jobOpeningListDTOService.convertToDTO(jobOpenings);
    }

    public boolean exportTemplateFile(JobOpeningDTO jobOpeningDTO, String directoryPath) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.OPERATOR);
        return requirementsTemplateManagerService.generateNewTemplate(requirementsTemplateManagerService.getRequirementFromJobOpeningDTO(jobOpeningDTO), directoryPath);
    }


}

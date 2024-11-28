package jobs4u.base.interviewmodelmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.jobopeningmanagement.application.JobOpeningListDTOService;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;

public class GenerateInterviewModelTemplateFileController {

    private final AuthorizationService authz;

    private final JobOpeningManagementService jobOpeningManagementService;

    private final JobOpeningListDTOService jobOpeningListDTOService;

    private final InterviewTemplateManagerService interviewTemplateManagerService;


    public GenerateInterviewModelTemplateFileController() {
        this.authz = AuthzRegistry.authorizationService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.jobOpeningListDTOService = new JobOpeningListDTOService();
        this.interviewTemplateManagerService = new InterviewTemplateManagerService();
    }

    public List<JobOpeningDTO> getJobOpeningList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        List<JobOpening> jobOpenings = new ArrayList<>();
        for (JobOpening job : jobOpeningManagementService.getUNFINISHEDJobOpenings()){
            jobOpenings.add(job);
        }
        return jobOpeningListDTOService.convertToDTO(jobOpenings);
    }

    public boolean exportTemplateFile(JobOpeningDTO jobOpeningDTO, String directoryPath) {
        return interviewTemplateManagerService.generateNewTemplate(interviewTemplateManagerService.getInterviewFromJobOpening(jobOpeningDTO), directoryPath);
    }

}

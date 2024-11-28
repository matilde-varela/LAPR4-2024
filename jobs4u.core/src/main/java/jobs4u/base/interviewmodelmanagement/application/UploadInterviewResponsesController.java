package jobs4u.base.interviewmodelmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.recruitmentprocessmanagement.application.RecruitmentProcessManagementService;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;

public class UploadInterviewResponsesController {

    JobOpeningManagementService jobOpeningManagementService;
    ApplicationManagementService applicationManagementService;
    RecruitmentProcessManagementService recruitmentProcessManagementService;
    InterviewModelManagementService interviewModelManagementService;
    AuthorizationService auth;


    public UploadInterviewResponsesController() {
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.applicationManagementService = new ApplicationManagementService();
        this.recruitmentProcessManagementService = new RecruitmentProcessManagementService();
        this.interviewModelManagementService = new InterviewModelManagementService();
        this.auth = AuthzRegistry.authorizationService();
    }

    public List<JobOpeningDTO> getJobOpenings() {
        List <JobOpeningDTO> jobOpeningDTOList = auth.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER)
                .map(user -> jobOpeningManagementService.getJobOpeningsInInterviewAndAnalysisPhase(user.identity())).orElse(new ArrayList<>());
        if (!jobOpeningDTOList.isEmpty()){
            return jobOpeningDTOList;
        }
        throw new RuntimeException("\n[WARNING] Don't exist applications for the selected job opening.");
    }

    public List<ApplicationDTO> getApplications(JobOpeningDTO jobOpeningDTO) {
        JobOpening jobOpening = jobOpeningManagementService.getJobOpening(jobOpeningDTO);
        List<ApplicationDTO> applicationDTOList = applicationManagementService.getApplicationsFromJobReference(jobOpening.jobReference().toString());
        if (!applicationDTOList.isEmpty()){
            return applicationDTOList;
        }
        throw new RuntimeException("\n[WARNING] Don't exist applications for the selected job opening.");
    }

    public boolean uploadFile(ApplicationDTO applicationDTO, String interviewName, String filepath) {
        if(interviewModelManagementService.checkAnswersFileIsValid(interviewName, filepath)){
            applicationManagementService.uploadInterviewAnswerFile(applicationDTO, filepath);
            return true;
        }
        return false;
    }
}

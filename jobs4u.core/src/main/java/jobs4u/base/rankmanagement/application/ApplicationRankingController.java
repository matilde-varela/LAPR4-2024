package jobs4u.base.rankmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.domain.DescendingInterviewResultComparator;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.rankmanagement.dto.RankOrderDTO;
import jobs4u.base.recruitmentprocessmanagement.application.RecruitmentProcessManagementService;
import jobs4u.base.requirementsmanagement.application.ApplicationListDTOService;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.*;

public class ApplicationRankingController {


    private final AuthorizationService authorizationService;
    private final RecruitmentProcessManagementService recruitmentProcessManagementService;
    private final RankManagementService rankManagementService;
    private final JobOpeningManagementService jobOpeningManagementService;
    private final ApplicationManagementService applicationManagementService;
    private final ApplicationListDTOService applicationListDTOService;

    public ApplicationRankingController() {
        this.authorizationService = AuthzRegistry.authorizationService();
        this.recruitmentProcessManagementService = new RecruitmentProcessManagementService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.applicationManagementService = new ApplicationManagementService();
        this.rankManagementService = new RankManagementService();
        this.applicationListDTOService = new ApplicationListDTOService();
    }

    public Iterable<JobOpeningDTO> getJobOpeningList() {
        Optional<SystemUser> user = authorizationService.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);
        if (user.isPresent()) {
            Iterable<JobOpeningDTO> jobOpeningDTOS = jobOpeningManagementService.jobOpeningsOfCustomerManager(user.get().username());
            if (!jobOpeningDTOS.iterator().hasNext()) {
                throw new NoSuchElementException("You have no job openings.");
            }
            return jobOpeningDTOS;
        }
        throw new NoSuchElementException("It was not possible to retrieve the user's data.");
    }

    public Iterable<RankOrderDTO> getRankOrderList(JobOpeningDTO jobOpeningDTO) {
        String jobReference = jobOpeningDTO.getJobReference();
        if (recruitmentProcessManagementService.checkIfRecruitmentProcessIsInAnalysisPhase(jobReference)) {
            return rankManagementService.getRank(jobReference);
        }
        throw new NoSuchElementException("It was not possible to retrieve the job opening's rank order/applications.");
    }

    public String getApplicationFile(ApplicationDTO applicationDTO, String filename) {
        return applicationManagementService.getApplicationFileContent(applicationDTO, filename);
    }

    public void updateRanking(JobOpeningDTO jobOpeningDTO, ApplicationDTO applicationDTO, int order) {
        rankManagementService.updateRanking(jobOpeningDTO, applicationDTO, order);
    }

    public boolean checkNeededApplicationsAreRanked(JobOpeningDTO jobOpeningDTO) {
        return rankManagementService.checkNeededApplicationsAreRanked(jobOpeningDTO);
    }

    public Iterable<ApplicationDTO> getApplications(JobOpeningDTO jobOpeningDTO) {
        JobOpening jobOpening = jobOpeningManagementService.getJobOpening(jobOpeningDTO);
        Set<Application> applicationSet = applicationManagementService.getApplications(jobOpening);
        if (recruitmentProcessManagementService.checkRecruitmentProcessHasInterview(jobOpeningDTO.getJobReference())) {
            List<Application> applications = new ArrayList<>(applicationSet);
            applications.sort(new DescendingInterviewResultComparator());
            return applicationListDTOService.convertApplicationsToDTO(applications);
        }
        return applicationListDTOService.convertApplicationsToDTO(applicationSet);
    }

    public double getRankSystemConfig() {
        return RankManagementService.SYSTEM_CONFIG_NUMBER_RANK_ORDERS;
    }
}

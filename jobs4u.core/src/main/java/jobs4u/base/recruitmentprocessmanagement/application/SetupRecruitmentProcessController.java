package jobs4u.base.recruitmentprocessmanagement.application;

import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.dto.AllPhasesDTO;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class SetupRecruitmentProcessController {

    private static JobOpeningManagementService jobOpeningManagementService = new JobOpeningManagementService();

    private static RecruitmentProcessManagementService recruitmentProcessManagementService = new RecruitmentProcessManagementService();

    public static List<JobOpening> getAllIncompleteJobOpenings(){
        List<JobOpening> list = (List<JobOpening>) jobOpeningManagementService.getAllUnfinishedJobOpenings();
        return list;
    }

    public static boolean checkByJobReference(String customerCode, int sequentialCode){
        return jobOpeningManagementService.checkJobOpeningByJobRef(customerCode, sequentialCode);
    }

    public static Optional<JobOpening> getJobOpeningByJobReference(String customerCode, int sequentialCode) {
        return jobOpeningManagementService.getJobOpeningByJobRef(customerCode, sequentialCode);
    }

    public static RecruitmentProcess setupRecruitmentProcess(Calendar start, Calendar end, AllPhasesDTO allPhasesDTO, JobOpening incompleteJobOpening){

        RecruitmentProcess recruitmentProcess = recruitmentProcessManagementService.setupRecruitmentProcess(start, end, allPhasesDTO,incompleteJobOpening);

        return recruitmentProcess;
    }

    public static boolean setupJobOpeningWithRecruitmentProcess(RecruitmentProcess recruitmentProcess, JobOpening jobOpening){
        return jobOpeningManagementService.setupJobOpeningWithRecruitmentProcess(recruitmentProcess, jobOpening);
    }

}

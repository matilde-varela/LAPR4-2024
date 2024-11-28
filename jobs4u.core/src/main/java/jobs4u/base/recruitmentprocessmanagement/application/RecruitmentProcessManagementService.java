package jobs4u.base.recruitmentprocessmanagement.application;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.recruitmentprocessmanagement.domain.*;
import jobs4u.base.recruitmentprocessmanagement.dto.AllPhasesDTO;
import jobs4u.base.recruitmentprocessmanagement.dto.PhaseDTO;
import jobs4u.base.recruitmentprocessmanagement.dto.RecruitmentProcessDTO;
import jobs4u.base.recruitmentprocessmanagement.repository.RecruitmentProcessRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class RecruitmentProcessManagementService {

    private static final RecruitmentProcessRepository recruitmentProcessRepository = PersistenceContext.repositories().recruitmentProcesses();

    private static final JobOpeningManagementService jobOpeningManagementService = new JobOpeningManagementService();

    public static RecruitmentProcess setupRecruitmentProcess(Calendar start, Calendar end, AllPhasesDTO allPhasesDTO, JobOpening jobOpening){

        List<Phase> listPhases = new ArrayList<>();

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(start, end, listPhases, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.PLANNED)));

        for (PhaseDTO phaseDTO : allPhasesDTO.getListOfPhases()){
            Phase phase = new Phase(phaseDTO.getPhaseType(), phaseDTO.getDescription(), phaseDTO.getInitialDate(), phaseDTO.getFinalDate());
            listPhases.add(phase);
        }

        recruitmentProcess.addPhases(listPhases);

        recruitmentProcess = recruitmentProcessRepository.save(recruitmentProcess);

        return recruitmentProcess;
    }

    public boolean saveToRepository(RecruitmentProcess recruitmentProcess){
        try{
            recruitmentProcessRepository.save(recruitmentProcess);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public RecruitmentProcessDTO getRecruitmentProcessWithJobReference(JobReference jobReference){
        return recruitmentProcessRepository.getRecruitmentProcessByJobReference(jobReference).get().toDTO();
    }

    public boolean goBackAPhase(String jobReference) {
        List<RecruitmentProcessStatusEnum> recruitmentProcessStatusEnum = new ArrayList<>();
        for (RecruitmentProcessStatusEnum recruitmentProcessStatusEnum1 : RecruitmentProcessStatusEnum.values()){
            recruitmentProcessStatusEnum.add(recruitmentProcessStatusEnum1);
        }
        int i = 0;
        Optional<RecruitmentProcess> recruitmentProcess = recruitmentProcessRepository.getRecruitmentProcessByJobReference(new JobReference(jobReference));
        RecruitmentProcess recruitmentProcess1 = recruitmentProcess.get();
        try {
            while(!(String.valueOf(recruitmentProcessStatusEnum.get(i)).equals(recruitmentProcess1.recruitmentProcessStatus().currentStatus()))){
                i++;
            }

            recruitmentProcess1.changeStatus(String.valueOf(recruitmentProcessStatusEnum.get(i-1)));
            recruitmentProcessRepository.save(recruitmentProcess1);

            if(String.valueOf(RecruitmentProcessStatusEnum.CONCLUDED).equals(recruitmentProcess1.recruitmentProcessStatus().toString())){
                JobOpening jobOpening = jobOpeningManagementService.getJobOpeningByJobRef(jobReference).get();
                jobOpening.updateStatusToEnded();
                jobOpeningManagementService.saveToRepository(jobOpening);
            }

        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean goNextPhase(String jobReference) {
        List<RecruitmentProcessStatusEnum> recruitmentProcessStatusEnum = new ArrayList<>();
        for (RecruitmentProcessStatusEnum recruitmentProcessStatusEnum1 : RecruitmentProcessStatusEnum.values()){
            recruitmentProcessStatusEnum.add(recruitmentProcessStatusEnum1);
        }
        int i = 0;
        Optional<RecruitmentProcess> recruitmentProcess = recruitmentProcessRepository.getRecruitmentProcessByJobReference(new JobReference(jobReference));
        RecruitmentProcess recruitmentProcess1 = recruitmentProcess.get();
        try {
            while(!(String.valueOf(recruitmentProcessStatusEnum.get(i)).equals(recruitmentProcess1.recruitmentProcessStatus().currentStatus()))){
                i++;
            }

            recruitmentProcess1.changeStatus(String.valueOf(recruitmentProcessStatusEnum.get(i+1)));
            recruitmentProcessRepository.save(recruitmentProcess1);

            if(String.valueOf(RecruitmentProcessStatusEnum.CONCLUDED).equals(recruitmentProcess1.recruitmentProcessStatus().toString())){
                JobOpening jobOpening = jobOpeningManagementService.getJobOpeningByJobRef(jobReference).get();
                jobOpening.updateStatusToEnded();
                jobOpeningManagementService.saveToRepository(jobOpening);
            }

        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean checkIfRecruitmentProcessIsInScreeningPhase(String jobReference) {
        Optional<RecruitmentProcess> recruitmentProcess = recruitmentProcessRepository.getRecruitmentProcessByJobReference(new JobReference(jobReference));
        if(recruitmentProcess.isPresent()){
            String phase = recruitmentProcess.get().currentActivePhase();
            return phase.equals("Screening Phase");
        }
        return false;
    }

    public boolean checkIfRecruitmentProcessIsInInterviewPhase(String jobReference){
        Optional<RecruitmentProcess> recruitmentProcess = recruitmentProcessRepository.getRecruitmentProcessByJobReference(new JobReference(jobReference));
        if (recruitmentProcess.isPresent()){
            String phase = recruitmentProcess.get().currentActivePhase();
            return phase.equals("Interview Phase");
        }
        return false;
    }

    public boolean checkIfRecruitmentProcessIsInAnalysisPhase(String jobReference) {
        Optional<RecruitmentProcess> recruitmentProcess = recruitmentProcessRepository.getRecruitmentProcessByJobReference(new JobReference(jobReference));
        if(recruitmentProcess.isPresent()){
            String phase = recruitmentProcess.get().currentActivePhase();
            return phase.equals("Analysis Phase");
        }
        return false;
    }

    public boolean checkRecruitmentProcessHasInterview(String jobReference) {
        Optional<RecruitmentProcess> recruitmentProcess = recruitmentProcessRepository.getRecruitmentProcessByJobReference(new JobReference(jobReference));
        if(recruitmentProcess.isPresent()){
            return recruitmentProcess.get().hasInterview();
        }
        throw new RuntimeException("No recruitment process found");
    }
}

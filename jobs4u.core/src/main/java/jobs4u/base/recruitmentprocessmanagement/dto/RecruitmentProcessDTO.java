package jobs4u.base.recruitmentprocessmanagement.dto;

import jakarta.persistence.*;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.recruitmentprocessmanagement.domain.Phase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentPeriod;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcessStatus;

import java.util.List;

public class RecruitmentProcessDTO {

    private final String recruitmentPeriodStart;
    private final String recruitmentPeriodEnd;
    private final String recruitmentProcessStatus;
    private final List<PhaseDTO> phases;
    private final String jobOpening;


    public RecruitmentProcessDTO(String recruitmentPeriodStart, String recruitmentPeriodEnd, String recruitmentProcessStatus, List<PhaseDTO> phases, String jobOpening) {
        this.recruitmentPeriodStart = recruitmentPeriodStart;
        this.recruitmentPeriodEnd = recruitmentPeriodEnd;
        this.recruitmentProcessStatus = recruitmentProcessStatus;
        this.phases = phases;
        this.jobOpening = jobOpening;
    }

    public List<PhaseDTO> getPhases() {
        return phases;
    }

    public String getJobOpening() {
        return jobOpening;
    }

    public String getRecruitmentPeriodEnd() {
        return recruitmentPeriodEnd;
    }

    public String getRecruitmentPeriodStart() {
        return recruitmentPeriodStart;
    }

    public String getRecruitmentProcessStatus() {
        return recruitmentProcessStatus;
    }

    @Override
    public String toString() {
        StringBuilder message =  new StringBuilder();
        int flag = 0;

        message.append("=============================== Recruit Process ================================\n" +
                       "Recruitment Process Of Job Opening : " + jobOpening + "\n" +
                       "Start Date : '" + recruitmentPeriodStart+ "\n" +
                       "End Date : '" + recruitmentPeriodEnd+ "\n" +
                       "Status : '" + recruitmentProcessStatus + "\n" +
                       "=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=- Phase -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n");
        message.append("================================================================================\n");

        for (PhaseDTO phase : phases){
            message.append("|   " + phase.getPhaseType() + "   ");
        }

        message.append("|\n");


        for (PhaseDTO phase : phases){
            if(phase.getPhaseType().toUpperCase().equals(recruitmentProcessStatus)) {
                message.append("|      OPEN      ");
                flag++;
            } else if (flag == 0) {
                message.append("|     CLOSED     ");
            } else {
                message.append("|     PLANNED    ");
            }
        }

        message.append("|\n");

        message.append("================================================================================\n");

        return message.toString();
    }
}

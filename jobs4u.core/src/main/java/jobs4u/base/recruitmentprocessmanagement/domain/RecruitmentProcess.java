package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.time.domain.model.DateInterval;
import eapli.framework.time.util.Calendars;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.recruitmentprocessmanagement.dto.PhaseDTO;
import jobs4u.base.recruitmentprocessmanagement.dto.RecruitmentProcessDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "T_RECRUITMENTPROCESS")
public class RecruitmentProcess implements AggregateRoot<Long>, DTOable<RecruitmentProcessDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitmentID;

    private RecruitmentPeriod recruitmentPeriod;

    private RecruitmentProcessStatus recruitmentProcessStatus;

    @ElementCollection
    private List<Phase> phases;

    @OneToOne
    private JobOpening jobOpening;

    public RecruitmentProcess(Calendar initialDate, Calendar finalDate, List<Phase> phases, RecruitmentProcessStatus recruitmentProcessStatus) {
        Preconditions.noneNull(initialDate, finalDate, phases, recruitmentProcessStatus);
        Preconditions.ensure(initialDate.before(finalDate));
        this.recruitmentPeriod = new RecruitmentPeriod(new DateInterval(initialDate, finalDate));
        this.phases = phases;
        this.recruitmentProcessStatus = recruitmentProcessStatus;
    }

    protected RecruitmentProcess() {
        //for ORM
    }

    public String currentActivePhase() {
        if (recruitmentProcessStatus.currentStatus().equals(String.valueOf(RecruitmentProcessStatusEnum.PLANNED))) {
            return "No Phase Active.";
        } else if (recruitmentProcessStatus.currentStatus().equals(String.valueOf(RecruitmentProcessStatusEnum.CONCLUDED))) {
            return "Recruitment Process Concluded.";
        } else {
            switch (recruitmentProcessStatus.currentStatus()) {
                case "APPLICATION":
                    return "Application Phase";
                case "SCREENING":
                    return "Screening Phase";
                case "INTERVIEW":
                    return "Interview Phase";
                case "ANALYSIS":
                    return "Analysis Phase";
                case "RESULTS":
                    return "Results Phase";
            }
        }
        return "No Phase Active.";
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public Long identity() {
        return this.recruitmentID;
    }

    public List<Phase> allPhases() {
        return phases;
    }

    public void referToJobOpening(JobOpening jobOpening) {
        this.jobOpening = jobOpening;
    }

    public RecruitmentProcessStatus recruitmentProcessStatus() {
        return recruitmentProcessStatus;
    }

    public boolean hasInterview(){
        for (Phase phase : phases) {
            if (phase.description().equals(PhaseTypeEnum.INTERVIEW.toString())) {
                return true;
            }
        }
        return false;
    }

    public void addPhases(List<Phase> phases) {
        Preconditions.ensure(phases.size() == 4 || phases.size() == 5);
        for (int i = 0; i + 1 < phases.size(); i++) {
            Preconditions.ensure(phases.get(i).activePeriod().getEndDate().before(phases.get(i + 1).activePeriod().getStartDate()));
        }
        this.phases = phases;
    }

    public void changeStatus(String recruitmentProcessStatus) {
        this.recruitmentProcessStatus = new RecruitmentProcessStatus(recruitmentProcessStatus);
    }

    public RecruitmentPeriod getRecruitmentPeriod() {
        return recruitmentPeriod;
    }

    @Override
    public RecruitmentProcessDTO toDTO() {
        List<PhaseDTO> array = new ArrayList<>();
        for (Phase phase : phases) {
            array.add(phase.toDTO());
        }
        return new RecruitmentProcessDTO(recruitmentPeriod.getStartDate().getTime().toString(), recruitmentPeriod.getEndDate().getTime().toString(), recruitmentProcessStatus.currentStatus().toString(), array, jobOpening.jobReference().toString());
    }

}

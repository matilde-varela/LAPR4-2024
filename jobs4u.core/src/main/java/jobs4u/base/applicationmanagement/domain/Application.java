package jobs4u.base.applicationmanagement.domain;

import com.ibm.icu.impl.Pair;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatusEnum;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Entity
@Table(name = "T_APPLICATION")
public class Application implements AggregateRoot<Long>, DTOable<ApplicationDTO> {
    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column(nullable = false)
    private RequirementAnswer requirementAnswer;
    //    @Column(nullable = false)
    private RequirementResult requirementResult;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ApplicationFile> files;
    @Column(nullable = false)
    private Date applicationDate;
    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    @ManyToOne
    private Candidate candidate;
    @Column
    private Interview interview;


    public Application(RequirementAnswer requirementAnswer, RequirementResult requirementResult, Set<ApplicationFile> files, Date applicationDate, Interview interview) {

        Preconditions.noneNull(requirementAnswer, requirementResult, files, applicationDate);

        this.requirementAnswer = requirementAnswer;
        this.requirementResult = requirementResult;
        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = new ApplicationStatus();
        this.applicationStatus.updateStatusDescriptionAsNOT_CHECKED();
        this.interview = interview;
    }

    public Application(RequirementAnswer requirementAnswer, RequirementResult requirementResult, Set<ApplicationFile> files, Date applicationDate, Candidate candidate) {

        Preconditions.noneNull(requirementAnswer, requirementResult, files, applicationDate, candidate);

        this.requirementAnswer = requirementAnswer;
        this.requirementResult = requirementResult;
        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = new ApplicationStatus();
        this.applicationStatus.updateStatusDescriptionAsNOT_CHECKED();
        this.candidate = candidate;
    }

    public Application(Set<ApplicationFile> files, Date applicationDate, Candidate candidate) {

        Preconditions.noneNull(files, applicationDate, candidate);

        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = new ApplicationStatus();
        this.applicationStatus.updateStatusDescriptionAsNOT_CHECKED();
        this.candidate = candidate;
    }

    public Application(Set<ApplicationFile> files, Date applicationDate) {

        Preconditions.noneNull(files, applicationDate);

        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = new ApplicationStatus();
        this.applicationStatus.updateStatusDescriptionAsNOT_CHECKED();
    }


    public Application(RequirementAnswer requirementAnswer, RequirementResult requirementResult, Set<ApplicationFile> files, Date applicationDate, Candidate candidate, Interview interview) {

        Preconditions.noneNull(requirementAnswer, requirementResult, files, applicationDate, candidate);

        this.requirementAnswer = requirementAnswer;
        this.requirementResult = requirementResult;
        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = new ApplicationStatus();
        this.applicationStatus.updateStatusDescriptionAsNOT_CHECKED();
        this.candidate = candidate;
        this.interview = interview;
    }

    public Application(String requirementAnswer, Boolean requirementResult, Set<ApplicationFile> files, Date applicationDate, Candidate candidate, Interview interview) {

        Preconditions.noneNull(requirementAnswer, requirementResult, files, applicationDate, candidate);

        this.requirementAnswer = RequirementAnswer.valueOf(requirementAnswer);
        this.requirementResult = RequirementResult.valueOf(requirementResult);
        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = new ApplicationStatus();
        this.applicationStatus.updateStatusDescriptionAsNOT_CHECKED();
        this.candidate = candidate;
        this.interview = interview;
    }

    public Application(RequirementAnswer requirementAnswer, RequirementResult requirementResult, Set<ApplicationFile> files, Date applicationDate) {

        Preconditions.noneNull(requirementAnswer, requirementResult, files, applicationDate);

        this.requirementAnswer = requirementAnswer;
        this.requirementResult = requirementResult;
        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = new ApplicationStatus();
        this.applicationStatus.updateStatusDescriptionAsNOT_CHECKED();
    }


    public Application(String requirementAnswer, Boolean requirementResult, Set<ApplicationFile> files, Date applicationDate) {
        Preconditions.noneNull(requirementAnswer, requirementResult, files, applicationDate);

        this.requirementAnswer = RequirementAnswer.valueOf(requirementAnswer);
        this.requirementResult = RequirementResult.valueOf(requirementResult);
        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = new ApplicationStatus();
        this.applicationStatus.updateStatusDescriptionAsNOT_CHECKED();
    }

    protected Application() {
        //for ORM
    }

    public Candidate candidate() {
        return candidate;
    }

    public Interview interview() {
        return interview;
    }
    public RequirementResult requirementResult(){return requirementResult;}


    public String requirementAnswerFilePath() {
        return this.requirementAnswer.filepath();
    }

    public String interviewAnswerFilePath() {
        return this.interview.interviewAnswer().filepath();
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

    @Override
    public int compareTo(Long other) {
        return AggregateRoot.super.compareTo(other);
    }

    @Override
    public Long identity() {
        return this.id;
    }

    public ApplicationStatus applicationStatus() {
        return applicationStatus;
    }

    public Set<ApplicationFile> allFiles() {
        return this.files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Application)) return false;
        Application that = (Application) o;
        return Objects.equals(version, that.version) && Objects.equals(id, that.id) &&
                Objects.equals(requirementAnswer, that.requirementAnswer) && Objects.equals(requirementResult, that.requirementResult) && Objects.equals(files, that.files);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public ApplicationDTO toDTO() {
        return new ApplicationDTO(id, files, applicationDate,
                applicationStatus.getStatusDescription(), candidate.user().name().firstName(), candidate.user().username().toString(), interview, requirementAnswer, requirementResult);
    }

    public void updateRequirementAnswer(String filepath) {
        Preconditions.noneNull(filepath);
        this.requirementAnswer = RequirementAnswer.valueOf(filepath);
    }

    public void updateRequirementResult(Pair<Boolean, String> result) {
        Preconditions.nonEmpty(requirementAnswerFilePath());
        File requirementFile = new File(requirementAnswerFilePath());
        try {
            if (!requirementFile.isAbsolute()) {
                requirementFile = requirementFile.getCanonicalFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to get the absolute path of the requirement answer file.", e);
        }

        if (requirementFile.exists() && requirementFile.isFile()) {
            if (!result.second.isEmpty()) {
                this.requirementResult = RequirementResult.valueOf(result.first, result.second);
            } else {
                this.requirementResult = RequirementResult.valueOf(result.first);
            }
        } else {
            throw new IllegalArgumentException("No valid requirement answer file.");
        }
    }

    public void updateInterviewGrade(Pair<Integer, String> result) {
        Preconditions.nonEmpty(interviewAnswerFilePath());

        File interviewFile = new File(interviewAnswerFilePath());
        try {

            if (!interviewFile.isAbsolute()) {
                interviewFile = interviewFile.getCanonicalFile();
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to get the absolute path of interview answers file.", e);
        }

        if (interviewFile.exists() && interviewFile.isFile()) {

            this.interview.updateInterviewResult(result);

        } else {
            throw new IllegalArgumentException("No valid interview answer file.");
        }
    }
    public void updateApplicationSchedule(Date newSchedule) {
        Preconditions.noneNull(newSchedule);
        this.interview.updateSchedule(newSchedule);
    }

    public void acceptApplication() {
        this.applicationStatus = ApplicationStatus.valueOf(ApplicationStatusEnum.ACCEPTED);
    }

    public void rejectApplication() {
        this.applicationStatus = ApplicationStatus.valueOf(ApplicationStatusEnum.REJECTED);
    }
}

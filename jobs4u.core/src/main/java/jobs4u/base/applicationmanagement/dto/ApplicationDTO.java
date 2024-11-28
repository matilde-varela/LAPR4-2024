package jobs4u.base.applicationmanagement.dto;

import jobs4u.base.applicationmanagement.domain.ApplicationFile;
import jobs4u.base.applicationmanagement.domain.Interview;
import jobs4u.base.applicationmanagement.domain.RequirementAnswer;
import jobs4u.base.applicationmanagement.domain.RequirementResult;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


public class ApplicationDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Long id;
    private final Set<ApplicationFile> files;
    private final Date applicationDate;
    private final String applicationStatus;
    private final String candidateName;
    private final String candidate;
    private final Interview interview;
    private final RequirementAnswer requirementAnswer;
    private final RequirementResult requirementResult;


    public ApplicationDTO(Long id, Set<ApplicationFile> files, Date applicationDate, String applicationStatus,String candidateName, String candidate, Interview interview, RequirementAnswer requirementAnswer, RequirementResult requirementResult) {
        this.id = id;
        this.files = files;
        this.applicationDate = applicationDate;
        this.applicationStatus = applicationStatus;
        this.candidateName = candidateName;
        this.candidate = candidate;
        this.interview = interview;
        this.requirementAnswer = requirementAnswer;
        this.requirementResult = requirementResult;
    }

//    public ApplicationDTO(Long id, Set<ApplicationFile> files, Date applicationDate, String applicationStatus, String candidate, Interview interview) {
//        this.id = id;
//        this.files = files;
//        this.applicationDate = applicationDate;
//        this.applicationStatus = applicationStatus;
//        this.candidate = candidate;
//        this.interview = interview;
//    }


    @Override
    public String toString() {
        return String.format("\n=====================================================================\n" +
                        "#Application: %d\n" +
                        "#File: %s\n" +
                        "#Application Date: %s\n" +
                        "#Application Status: %s\n" +
                        "#Candidate name: %s\n" +
                        "#Candidate username: %s\n" +
                        "=====================================================================\n",
                id, files, applicationDate,
                applicationStatus, candidateName, candidate);
    }

    public Set<ApplicationFile> getApplicationFiles() {
        return files;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public String getCandidate(){
        return candidate;
    }

    public Long getId() {
        return id;
    }

    public Interview getInterview(){
        return interview;
    }

    public RequirementAnswer getRequirementAnswer(){
        return requirementAnswer;
    }

    public RequirementResult getRequirementResult(){
        return requirementResult;
    }

    public String getCandidateName(){
        return candidateName;
    }
}

package jobs4u.base.jobopeningmanagement.domain.events;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.general.domain.model.EmailAddress;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.jobopeningmanagement.domain.JobReference;

import java.util.List;

public class JobOpeningResultsPublishedEvent extends DomainEventBase implements DomainEvent {
    private EmailAddress senderEmail;
    private String companyCode;
    private String userPassword;
    private List<Candidate> acceptedCandidatesList;
    private JobReference jobReference;

    public JobOpeningResultsPublishedEvent(EmailAddress customerManager, List<Candidate> acceptedCandidateList, String customerCode, JobReference jobReference, String userPassword) {
        this.senderEmail = customerManager;
        this.acceptedCandidatesList = acceptedCandidateList;
        this.companyCode = customerCode;
        this.jobReference = jobReference;
        this.userPassword = userPassword;
    }

    public String userPassword() {
        return userPassword;
    }

    public String senderEmail() {
        return senderEmail.toString();
    }

    public String companyCode() {
        return companyCode;
    }

    public String topic() {
        return "JobOpening Result";
    }

    public String information() {
        StringBuilder builder = new StringBuilder();

        String information = "Hello, this is Jobs4u, we came to tell you that the candidates have been selected for the job opening: ";
        builder.append(information).append(jobReference.toString()).append("\n").append("» Candidates Information:");
        for (Candidate candidate : acceptedCandidatesList) {
            builder.append(candidate.toString()).append("\n");
        }
        return builder.toString();
    }
}

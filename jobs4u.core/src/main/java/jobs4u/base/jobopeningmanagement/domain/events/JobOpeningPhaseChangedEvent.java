package jobs4u.base.jobopeningmanagement.domain.events;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.general.domain.model.EmailAddress;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.jobopeningmanagement.domain.JobReference;

import java.util.List;

public class JobOpeningPhaseChangedEvent extends DomainEventBase implements DomainEvent {
    private EmailAddress email;
    private JobReference jobReference;
    private String newPhase;

    public JobOpeningPhaseChangedEvent(EmailAddress customerManager, JobReference jobReference, String newPhase) {
        this.email = customerManager;
        this.jobReference = jobReference;
        this.newPhase = newPhase;
    }

    public String email() {
        return email.toString();
    }

    public JobReference getJobReference() {
        return jobReference;
    }

    public String getNewPhase(){
        return newPhase;
    }

    public String topic() {
        return "JobOpening Result";
    }

    public String information() {
        return "Hello, this is Jobs4u, we came to tell you that your result has been accepted for the job opening: " + jobReference.toString();
    }
}

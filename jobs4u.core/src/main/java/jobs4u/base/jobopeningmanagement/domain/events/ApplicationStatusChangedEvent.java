package jobs4u.base.jobopeningmanagement.domain.events;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.general.domain.model.EmailAddress;
import jobs4u.base.jobopeningmanagement.domain.JobReference;

public class ApplicationStatusChangedEvent  extends DomainEventBase implements DomainEvent {

    private EmailAddress emailAddress;
    private JobReference jobReference;
    private String newStatus;

    public ApplicationStatusChangedEvent(EmailAddress emailAddress, JobReference jobReference, String newStatus) {
        this.emailAddress = emailAddress;
        this.jobReference = jobReference;
        this.newStatus = newStatus;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public JobReference getJobReference() {
        return jobReference;
    }

    public String getNewStatus() {
        return newStatus;
    }
}

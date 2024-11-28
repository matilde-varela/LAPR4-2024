package jobs4u.base.candidatemanagement.domain.events;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;


public class NewCandidateUserRegisteredEvent extends DomainEventBase implements DomainEvent {
    private static final long serialVersionUID = 1L;
    private final SystemUser systemUser;
    private final PhoneNumber phoneNumber;



    public NewCandidateUserRegisteredEvent(SystemUser systemUser, PhoneNumber phoneNumber) {
        this.systemUser = systemUser;
        this.phoneNumber = phoneNumber;
    }

    public SystemUser systemUser() {
        return this.systemUser;
    }

    public PhoneNumber phoneNumber() {
        return this.phoneNumber;
    }


}

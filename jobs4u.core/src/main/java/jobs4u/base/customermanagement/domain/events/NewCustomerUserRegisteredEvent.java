package jobs4u.base.customermanagement.domain.events;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.domain.events.DomainEventBase;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;

import jobs4u.base.customermanagement.domain.CompanyName;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.jobopeningmanagement.domain.Address;

public class NewCustomerUserRegisteredEvent extends DomainEventBase implements DomainEvent {
    private static final long serialVersionUID = 1L;
    private final CompanyName companyName;
    private final Address address;
    private final CustomerCode customerCode;
    private final SystemUser customerManager;
    private final SystemUser customerUser;

    public NewCustomerUserRegisteredEvent(CompanyName companyName, Address address, CustomerCode customerCode, SystemUser
            customerManager, SystemUser customerUser) {
        //for ORM
        this.companyName = companyName;
        this.address = address;
        this.customerCode = customerCode;
        this.customerManager = customerManager;
        this.customerUser = customerUser;
    }

    public CustomerCode customerCode() {
        return this.customerCode;
    }

    public CompanyName companyName() {
        return this.companyName;
    }

    public Address address() {
        return this.address;
    }

    public SystemUser customerManager() {
        return this.customerManager;
    }

    public SystemUser customerUser() {
        return customerUser;
    }
}

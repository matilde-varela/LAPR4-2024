package jobs4u.base.customermanagement.application.eventhandlers;

import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.events.NewCustomerUserRegisteredEvent;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;

class AddCustomerOnNewCustomerUserRegisteredController {
    private final CustomerRepository customerRepository = PersistenceContext.repositories().customers();

    public void registerNewCustomer(NewCustomerUserRegisteredEvent event) {
        customerRepository.save(new Customer(event.companyName(), event.address(),
                event.customerCode(), event.customerManager(), event.customerUser()));
    }
}

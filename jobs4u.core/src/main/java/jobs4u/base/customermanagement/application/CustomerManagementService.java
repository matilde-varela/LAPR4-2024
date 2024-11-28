package jobs4u.base.customermanagement.application;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserManagementService;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jobs4u.base.customermanagement.domain.CompanyName;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.dto.CustomerDTO;
import jobs4u.base.customermanagement.repository.CustomerRepository;

import jobs4u.base.customermanagement.domain.events.NewCustomerUserRegisteredEvent;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.CustomerListDTOService;
import jobs4u.base.jobopeningmanagement.domain.Address;
import jobs4u.base.usermanagement.application.GeneratePasswordService;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.*;


public class CustomerManagementService {

    private final CustomerListDTOService service = new CustomerListDTOService();
    private final GeneratePasswordService passSvc = new GeneratePasswordService();

    private final UserManagementService userSvc = AuthzRegistry.userService();
    private final CustomerRepository customerRepository = PersistenceContext
            .repositories().customers();

    private final EventPublisher dispatcher = InProcessPubSub.publisher();

    public void registerNewCustomer(String companyName, String address, String customerCode,
                                    SystemUser customerManager, String email) {
        String password = passSvc.generatePassword();
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.CUSTOMER_USER);

        SystemUser customerUser = userSvc.registerNewUser(email, password, companyName, "Customer", email, roles);

        final DomainEvent event = new NewCustomerUserRegisteredEvent(new CompanyName(companyName),
                new Address(address), new CustomerCode(customerCode), customerManager, customerUser);
        dispatcher.publish(event);
    }

    public List<CustomerDTO> getAssignedCustomerCodesList(Username username) {
        List<Customer> customersList = customerRepository.getCustomersByUsername(username);
        if (customersList == null){
            return new ArrayList<>();
        }
        return service.convertToDTO(customersList);
    }


    public Optional<Customer> getCustomerByDTO(CustomerDTO object) {
       return customerRepository.ofIdentity(CustomerCode.valueOf(object.customerCode()));
    }

    public Optional<Customer> getCustomerByCustomerCode(String customerCode) {
        return customerRepository.getCustomerByCustomerCode(customerCode);
    }
}

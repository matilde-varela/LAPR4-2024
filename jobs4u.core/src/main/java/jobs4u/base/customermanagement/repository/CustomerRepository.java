package jobs4u.base.customermanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository
        extends DomainRepository<CustomerCode, Customer> {

    List<Customer> getCustomersByUsername(Username username);

    Optional<Customer> getCustomerByCustomerCode(String customerCode);
}

package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;

import java.util.*;

public class InMemoryCustomerRepository
        extends InMemoryDomainRepository<Customer, CustomerCode>
        implements CustomerRepository {

    @Override
    public List<Customer> getCustomersByUsername(Username username) {
        List<Customer> assignedCustomers = new ArrayList<>();
        Iterable<Customer> entities = match(e -> e.customerManager().username().toString().equals(username));

        for (Customer Customer : entities) {
            assignedCustomers.add(Customer);
        }
        return assignedCustomers;
    }

    @Override
    public Optional<Customer> getCustomerByCustomerCode(String customerCode) {
        return matchOne(e -> e.customerCode().costumerCode().equals(customerCode));
    }
}

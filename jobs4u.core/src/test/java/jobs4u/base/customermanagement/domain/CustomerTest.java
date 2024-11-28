package jobs4u.base.customermanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.*;
import jobs4u.base.jobopeningmanagement.domain.Address;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class CustomerTest {


    public static SystemUser dummyUser(Role role) {
        // should we load from spring context?
        final SystemUserBuilder userBuilder = new SystemUserBuilder(new NilPasswordPolicy(), new PlainTextEncoder());
        return userBuilder.with("a@b.ro", "duMMy1", "dummy", "dummy", "a@b.ro").withRoles(role).build();
    }

    @Test
    public void ensureMustHaveName() {
        final var address = new Address("Rua None, 222, Paços, Porto, 4000-800");
        final var customerCode = new CustomerCode("ISEP");

        assertThrows(IllegalArgumentException.class, () -> new Customer(null, address,
                customerCode, dummyUser(BaseRoles.CUSTOMER_MANAGER), dummyUser(BaseRoles.CUSTOMER_USER)));
    }

    @Test
    public void ensureMustHaveCode() {
        final var address = new Address("Rua None, 222, Paços, Porto, 4000-800");

        assertThrows(IllegalArgumentException.class, () -> new Customer(new CompanyName("RCabos"), address,
                null, dummyUser(BaseRoles.CUSTOMER_MANAGER), dummyUser(BaseRoles.CUSTOMER_USER)));
    }

    @Test
    public void ensureMustHaveAddress() {
        final var customerCode = new CustomerCode("ISEP");

        assertThrows(IllegalArgumentException.class, () -> new Customer(new CompanyName("RCabos"), null,
                customerCode, dummyUser(BaseRoles.CUSTOMER_MANAGER), dummyUser(BaseRoles.CUSTOMER_USER)));
    }
}
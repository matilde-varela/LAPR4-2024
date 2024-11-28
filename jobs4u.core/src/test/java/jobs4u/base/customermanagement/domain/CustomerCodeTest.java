package jobs4u.base.customermanagement.domain;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class CustomerCodeTest {
    @Test
    public void ensureDoesntHaveMoreThanTenCharacters() {

        assertThrows(IllegalArgumentException.class, () -> new CustomerCode("MORETHANTEN"));
    }

    @Test
    public void ensureMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new CustomerCode(null));
    }


    @Test
    public void ensureMustNotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new CustomerCode("    "));
    }

}
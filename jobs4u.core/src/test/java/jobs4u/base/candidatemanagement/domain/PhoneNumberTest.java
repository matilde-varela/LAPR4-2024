package jobs4u.base.candidatemanagement.domain;

import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.jobopeningmanagement.domain.Address;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {
    private final PhoneNumber phoneNumber1 = new PhoneNumber("+351","910000000");
    private final PhoneNumber phoneNumber2 = new PhoneNumber("+351","910000002");

    @Test
    public void ensureEqualsWithEqualsPhoneNumbersPasses() throws Exception {

        final boolean expected = phoneNumber1.equals(phoneNumber1);

        assertTrue(expected);
    }
    @Test
    public void ensureEqualsWithDiferentPhoneNumbersFails() throws Exception {

        final boolean expected = phoneNumber1.equals(phoneNumber2);

        assertFalse(expected);
    }
    @Test
    public void ensurePhoneNumberWithoutExtensionFails() {
        assertThrows(NullPointerException.class, () -> new PhoneNumber(null, "910000000"));
    }
    @Test
    public void ensurePhoneNumberWithoutNumberFails() {
        assertThrows(NullPointerException.class, () -> new PhoneNumber("+351", null));
    }
    @Test
    public void ensureExtensionWithoutPlusFails(){
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("351", "12345678"));
    }
    @Test
    public void ensurePhoneNumberLessThan8DigitsFails() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("+351", "1234567"));
    }
    @Test
    public void ensurePhoneNumberPlusThan15DigitsFails() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("+351", "1234567890123456"));

    }

}
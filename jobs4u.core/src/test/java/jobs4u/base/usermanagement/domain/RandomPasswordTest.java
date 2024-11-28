package jobs4u.base.usermanagement.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomPasswordTest {

    @Test
    public void ensurePasswordHasEightCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new RandomPassword(2));
    }

    @Test
    public void ensurePasswordHasLowerLetters() {
        final var rrp = new RandomPassword();

        boolean hasLower = false;
        for (char c : rrp.toString().toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLower = true;
                break;
            }
        }
        assertTrue(hasLower);

    }

    @Test
    public void ensurePasswordHasUpperLetters() {
        final var rrp = new RandomPassword();

        boolean hasUpper = false;
        for (char c : rrp.toString().toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
                break;
            }
        }
        assertTrue(hasUpper);

    }

    @Test
    public void ensurePasswordHasDigits() {
        final var rrp = new RandomPassword();
        assertNotNull(rrp.toString());

        boolean hasDigit = false;
        for (char c : rrp.toString().toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        assertTrue(hasDigit);
    }

    @Test
    public void ensurePasswordHasNonAlphanumeric() {
        final var rrp = new RandomPassword();
        assertNotNull(rrp.toString());
        boolean hasNonAlpha = false;

        for (char c : rrp.toString().toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                hasNonAlpha = true;
                break;
            }
        }
        assertTrue(hasNonAlpha);
    }
}
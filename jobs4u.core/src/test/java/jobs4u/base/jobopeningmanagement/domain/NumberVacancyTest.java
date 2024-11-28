package jobs4u.base.jobopeningmanagement.domain;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class NumberVacancyTest {

    @Test
    public void ensureNumberVacanciesNullIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new NumberVacancy(null));
    }

    @Test
    public void ensureNumberVacanciesNegativeIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new NumberVacancy(-1));
    }

    @Test
    public void ensureNumberVacanciesZeroIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new NumberVacancy(0));
    }

}
package jobs4u.base.criteriamanagement.domain;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class CriteriaTest {

    @Test
    public void ensureCriteriaDenominationNullIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Criteria(null, Criteria.class.getSimpleName()));
    }

    @Test
    public void ensureCriteriaDenominationEmptyIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Criteria("", Criteria.class.getSimpleName()));
    }

    @Test
    public void ensureCriteriaClassNameNullIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Criteria("criteria", null));
    }

    @Test
    public void ensureCriteriaClassNameEmptyIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Criteria("criteria", ""));
    }

}
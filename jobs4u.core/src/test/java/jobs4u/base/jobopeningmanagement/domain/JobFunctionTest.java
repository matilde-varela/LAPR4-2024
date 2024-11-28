package jobs4u.base.jobopeningmanagement.domain;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class JobFunctionTest {

    @Test
    public void ensureJobFunctionNullIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new JobFunction(null));
    }

    @Test
    public void ensureJobFunctionEmptyIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new JobFunction(""));
    }

}
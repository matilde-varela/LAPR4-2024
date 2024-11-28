package jobs4u.base.workmodemanagement;

import jobs4u.base.workmodemanagement.domain.WorkMode;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class WorkModeTest {

    @Test
    public void ensureWorkModeNullIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new WorkMode(null));
    }

    @Test
    public void ensureWorkModeEmptyIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new WorkMode(""));
    }


}
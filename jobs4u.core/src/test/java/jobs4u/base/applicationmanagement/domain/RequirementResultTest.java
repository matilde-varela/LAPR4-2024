package jobs4u.base.applicationmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementResultTest {

    @Test
    public void ensureRequirementResultNullIsInvalid(){
        assertThrows(IllegalArgumentException.class, () -> new RequirementResult(null));
    }


}
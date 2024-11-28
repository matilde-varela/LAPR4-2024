package jobs4u.base.requirementsmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementDescriptionTest {

    @Test
    void ensureItHasADescription() {
        assertThrows(IllegalArgumentException.class, () -> new RequirementDescription(null));
    }
}
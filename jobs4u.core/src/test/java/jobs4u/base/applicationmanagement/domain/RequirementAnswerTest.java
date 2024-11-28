package jobs4u.base.applicationmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequirementAnswerTest {

    @Test
    public void ensureRequirementAnswerNullIsInvalid(){
        assertThrows(IllegalArgumentException.class, () -> new RequirementAnswer(null));
    }

    @Test
    public void ensureRequirementAnswerEmptyIsInvalid(){
        assertThrows(IllegalArgumentException.class, () -> new RequirementAnswer(""));
    }
}
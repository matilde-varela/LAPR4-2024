package jobs4u.base.interviewmodelmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterviewModelNameTest {

    @Test
    void ensureItHasAName() {
        assertThrows(IllegalArgumentException.class ,() -> new InterviewModelDescription(null));
    }
}
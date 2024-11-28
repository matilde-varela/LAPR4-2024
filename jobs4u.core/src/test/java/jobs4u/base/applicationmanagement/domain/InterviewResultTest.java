package jobs4u.base.applicationmanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterviewResultTest {
    
    Integer interviewGrade = 50;
    String justification = "It had all the files";

    @Test
    public void ensureMustHaveGrade(){
        assertThrows(IllegalArgumentException.class, () -> new InterviewResult(null,
                justification));

    }

    @Test
    public void ensureMustHaveJustification(){
        assertThrows(IllegalArgumentException.class, () -> new InterviewResult( interviewGrade,
            null));
    }

}
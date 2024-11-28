package jobs4u.base.applicationmanagement.domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class InterviewTest {

    String interviewTypeDenomination = "interview1";
    Date schedule = new Date(2024,Calendar.JANUARY, 5);
    InterviewResult interviewResult = new InterviewResult( 50, "wrong");
    InterviewAnswer interviewAnswer = InterviewAnswer.valueOf("../plugins-config-file/interview/i-answer-1.txt");

    @Test
    public void ensureMustHaveDenomination(){
        assertThrows(IllegalArgumentException.class, () -> new Interview(null, schedule, interviewResult,
                interviewAnswer));
    }

    @Test
    public void ensureMustHaveSchedule(){
        assertThrows(IllegalArgumentException.class, () -> new Interview(interviewTypeDenomination, null, interviewResult,
                interviewAnswer));
    }

    @Test
    public void ensureMustHaveDenominationResult(){
        assertThrows(IllegalArgumentException.class, () -> new Interview(interviewTypeDenomination, schedule, null,
                interviewAnswer));
    }

    @Test
    public void ensureMustHaveDenominationAnswer(){
        assertThrows(IllegalArgumentException.class, () -> new Interview(interviewTypeDenomination, schedule, interviewResult,
                null));
    }



}
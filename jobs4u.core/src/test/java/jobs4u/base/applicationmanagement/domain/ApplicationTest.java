package jobs4u.base.applicationmanagement.domain;

import com.ibm.icu.impl.Pair;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    File n = new File(new File("../plugins-config-file/requirement/r-answer-1.txt").getCanonicalPath());
    File i = new File(new File("../plugins-config-file/interview/i-answer-1.txt").getCanonicalPath());
    RequirementAnswer requirementAnswer = new RequirementAnswer(n.toString());
    RequirementResult requirementResult = new RequirementResult(true);
    Set<ApplicationFile> files = new HashSet<>();

    Date date = new Date(2024, Calendar.JANUARY, 5);
    InterviewAnswer interviewAnswer = new InterviewAnswer(i.toString());
    Interview interview = new Interview("interview1", new Date(2024, Calendar.FEBRUARY, 2),
            new InterviewResult( 50, "wrong"), interviewAnswer);

    public ApplicationTest() throws IOException {
    }


    @Test
    public void ensureMustHaveRequirementAnswer() {
        assertThrows(IllegalArgumentException.class, () -> new Application(null, requirementResult, files, date, interview));
    }

    @Test
    public void ensureMustHaveRequirementResult() {
        assertThrows(IllegalArgumentException.class, () -> new Application(requirementAnswer, null, files, date, interview));
    }

    @Test
    public void ensureMustHaveFile() {
        assertThrows(IllegalArgumentException.class, () -> new Application(requirementAnswer, requirementResult, null, date, interview));
    }

    @Test
    public void ensureMustHaveDate() {
        assertThrows(IllegalArgumentException.class, () -> new Application(requirementAnswer, requirementResult, files, null, interview));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveRequirementsToVerify() {
        Application a = new Application(new RequirementAnswer("not a file hehe"), requirementResult, files, date, interview);
        a.updateRequirementResult(Pair.of(true, ""));
    }

    @Test
    public void ensureMustHaveStatus() {
        Application application = new Application(requirementAnswer, requirementResult, files, date, interview);
        ApplicationStatus status = new ApplicationStatus(ApplicationStatusEnum.NOT_CHECKED);

        assertEquals(application.applicationStatus(), status);
    }

    @Test
    public void ensureExistInterview() {
        Application application = new Application(requirementAnswer, requirementResult, files, date, interview);
        Interview interview = new Interview("interview1", new Date(2024, Calendar.FEBRUARY, 2),
                new InterviewResult( 50, "wrong"), interviewAnswer);

        assertEquals(application.interview(), interview);
    }

}
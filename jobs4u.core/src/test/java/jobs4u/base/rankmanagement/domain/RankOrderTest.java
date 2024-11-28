package jobs4u.base.rankmanagement.domain;

import jobs4u.base.applicationmanagement.domain.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertThrows;

public class RankOrderTest {

    private static Application application;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        RequirementAnswer requirementAnswer1 = RequirementAnswer.valueOf("plugins-config-file/requirement/r-answer-1.txt");
        RequirementResult requirementResult = RequirementResult.valueOf(true);
        ApplicationFile file1 = new ApplicationFile(new File("output/candidate1/1-big-file-1.txt"));
        ApplicationFile file2 = new ApplicationFile(new File("output/candidate1/1-candidate-data.txt"));
        ApplicationFile file3 = new ApplicationFile(new File("output/candidate1/1-cv.txt"));
        ApplicationFile file4 = new ApplicationFile(new File("output/candidate1/1-email.txt"));
        ApplicationFile file5 = new ApplicationFile(new File("output/candidate1/1-report-1.txt"));
        Set<ApplicationFile> files1 = new HashSet<>();
        files1.add(file1);
        files1.add(file2);
        files1.add(file3);
        files1.add(file4);
        files1.add(file5);
        Date date = new Date(2024 - 1900, Calendar.JANUARY, 5);
        InterviewAnswer interviewAnswer1 = InterviewAnswer.valueOf("plugins-config-file/interview/i-answer-1.txt");
        Interview interview1 = new Interview("interview1", new Date(2024 - 1900, Calendar.MARCH, 3),
                new InterviewResult(60, "the grade is above 50"), interviewAnswer1);

        application = new Application(requirementAnswer1, requirementResult, files1, date, interview1);
    }

    @Test
    public void ensureRankOrderOrderValueNullIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new RankOrder(application, null));
    }


    @Test
    public void ensureRankOrderOrderValueZeroIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new RankOrder(application, 0));
    }

    @Test
    public void ensureRankOrderOrderValueNegativeIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new RankOrder(application, -1));
    }

    @Test
    public void ensureRankOrderApplicationNullIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new RankOrder(null, 1));
    }

}
package jobs4u.base.applicationmanagement.domain;

import java.util.Comparator;

public class DescendingInterviewResultComparator implements Comparator<Application> {
    @Override
    public int compare(Application a1, Application a2) {
        if (a1.interview() == null && a2.interview() == null) return 0;
        if (a1.interview() == null) return -1;
        if (a2.interview() == null) return 1;

        InterviewResult result1 = a1.interview().interviewResult();
        InterviewResult result2 = a2.interview().interviewResult();

        if (result1 == null && result2 == null) return 0;
        if (result1 == null) return -1;
        if (result2 == null) return 1;

        return result2.compareTo(result1);
    }
}

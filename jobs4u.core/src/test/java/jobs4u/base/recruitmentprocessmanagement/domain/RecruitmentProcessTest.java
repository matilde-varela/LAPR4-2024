package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.time.util.Calendars;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecruitmentProcessTest {

    @Test
    void ensureItCantCreateWithNullParameter1() {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        List<Phase> listA = new ArrayList<>();
        try {
            start = Calendars.fromDate(df.parse("16-03-2004"));
            finish = Calendars.fromDate(df.parse("18-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new RecruitmentProcess(null, finalFinish, listA,new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED)));
    }

    @Test
    void ensureItCantCreateWithNullParameter2() {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        List<Phase> listA = new ArrayList<>();
        try {
            start = Calendars.fromDate(df.parse("16-03-2004"));
            finish = Calendars.fromDate(df.parse("18-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new RecruitmentProcess(finalStart, null, listA,new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED)));
    }

    @Test
    void ensureItCantCreateWithNullParameter3() {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        List<Phase> listA = new ArrayList<>();
        try {
            start = Calendars.fromDate(df.parse("16-03-2004"));
            finish = Calendars.fromDate(df.parse("18-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new RecruitmentProcess(finalStart, finalFinish, null,new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED)));
    }

    @Test
    void ensureItCantCreatePhaseWithSameEndAndStartDate() {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        List<Phase> phases = new ArrayList<>();
        try {
            start = Calendars.fromDate(df.parse("16-03-2024"));
            finish = Calendars.fromDate(df.parse("16-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new RecruitmentProcess(finalStart, finalFinish, phases,new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED)));

    }

    @Test
    void ensureItCantCreatePhaseWithStartDateAfterEndDate() {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        List<Phase> phases = new ArrayList<>();
        try {
            start = Calendars.fromDate(df.parse("18-03-2024"));
            finish = Calendars.fromDate(df.parse("16-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new RecruitmentProcess(finalStart, finalFinish, phases,new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED)));

    }

    @Test
    void ensureItCantCreateARecruitmentProcessWithOverlappingOrNonSequentialPhases(){

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start1 = null;
        Calendar finish1 = null;
        Calendar start2 = null;
        Calendar finish2 = null;
        Calendar start3 = null;
        Calendar finish3 = null;
        Calendar start4 = null;
        Calendar finish4 = null;
        Calendar start5 = null;
        Calendar finish5 = null;
        List<Phase> phases = new ArrayList<>();
        try {
            start1 = Calendars.fromDate(df.parse("16-03-2024"));
            finish1 = Calendars.fromDate(df.parse("18-03-2024"));
            start2 = Calendars.fromDate(df.parse("11-03-2024"));
            finish2 = Calendars.fromDate(df.parse("13-03-2024"));
            start3 = Calendars.fromDate(df.parse("20-02-2024"));
            finish3 = Calendars.fromDate(df.parse("23-02-2024"));
            start4 = Calendars.fromDate(df.parse("20-03-2024"));
            finish4 = Calendars.fromDate(df.parse("22-03-2024"));
            start5 = Calendars.fromDate(df.parse("23-03-2024"));
            finish5 = Calendars.fromDate(df.parse("24-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar finalFinish = finish5;
        Calendar finalStart = start1;

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(finalStart, finalFinish, phases, new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED));

        Phase phase1 = new Phase("Test", "Test", start1, finish1);
        Phase phase2 = new Phase("Test", "Test", start2, finish2);
        Phase phase3 = new Phase("Test", "Test", start3, finish3);
        Phase phase4 = new Phase("Test", "Test", start4, finish4);
        Phase phase5 = new Phase("Test", "Test", start5, finish5);

        phases.add(phase1);
        phases.add(phase2);
        phases.add(phase3);
        phases.add(phase4);
        phases.add(phase5);

        assertThrows(IllegalArgumentException.class,() -> recruitmentProcess.addPhases(phases));

    }

    @Test
    void ensureItCantCreateARecruitmentProcessWithOverlappingOrNonSequentialPhases2(){

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start1 = null;
        Calendar finish1 = null;
        Calendar start2 = null;
        Calendar finish2 = null;
        Calendar start3 = null;
        Calendar finish3 = null;
        Calendar start4 = null;
        Calendar finish4 = null;
        Calendar start5 = null;
        Calendar finish5 = null;
        List<Phase> phases = new ArrayList<>();
        try {
            start1 = Calendars.fromDate(df.parse("16-03-2024"));
            finish1 = Calendars.fromDate(df.parse("18-03-2024"));
            start2 = Calendars.fromDate(df.parse("19-03-2024"));
            finish2 = Calendars.fromDate(df.parse("23-03-2024"));
            start3 = Calendars.fromDate(df.parse("24-03-2024"));
            finish3 = Calendars.fromDate(df.parse("29-03-2024"));
            start4 = Calendars.fromDate(df.parse("01-04-2024"));
            finish4 = Calendars.fromDate(df.parse("05-04-2024"));
            start5 = Calendars.fromDate(df.parse("09-04-2024"));
            finish5 = Calendars.fromDate(df.parse("12-04-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar finalFinish = finish5;
        Calendar finalStart = start1;

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(finalStart, finalFinish, phases, new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED));

        Phase phase1 = new Phase("Test", "Test", start1, finish1);
        Phase phase2 = new Phase("Test", "Test", start2, finish2);
        Phase phase3 = new Phase("Test", "Test", start3, finish3);
        Phase phase4 = new Phase("Test", "Test", start4, finish4);
        Phase phase5 = new Phase("Test", "Test", start5, finish5);

        phases.add(phase1);
        phases.add(phase2);
        phases.add(phase3);
        phases.add(phase4);
        phases.add(phase5);

        assertDoesNotThrow(() -> recruitmentProcess.addPhases(phases));
    }


}
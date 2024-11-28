package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.time.util.Calendars;
import eapli.framework.validations.Preconditions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class PhaseTest {

    @Test
    void ensureItCantCreateWithNullParameter1() {

        String phaseType = null;
        String phaseDescription = "Testing purposes";
        String phaseStatus = "Test test and test";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        try {
            start = Calendars.fromDate(df.parse("16-03-2004"));
            finish = Calendars.fromDate(df.parse("18-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess();

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new Phase(phaseType, phaseDescription, finalStart, finalFinish));

    }

    @Test
    void ensureItCantCreateWithNullParameter2() {

        String phaseType = "TestPhase";
        String phaseDescription = null;
        String phaseStatus = "Test test and test";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        try {
            start = Calendars.fromDate(df.parse("16-03-2004"));
            finish = Calendars.fromDate(df.parse("18-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess();

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new Phase(phaseType, phaseDescription, finalStart, finalFinish));

    }


    @Test
    void ensureItCantCreateWithNullParameter4() {

        String phaseType = "TestPhase";
        String phaseDescription = "Testing purposes";
        String phaseStatus = "Test test and test";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        try {
            start = null;
            finish = Calendars.fromDate(df.parse("18-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess();

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new Phase(phaseType, phaseDescription, finalStart, finalFinish));

    }

    @Test
    void ensureItCantCreateWithNullParameter5() {

        String phaseType = "TestPhase";
        String phaseDescription = "Testing purposes";
        String phaseStatus = "Test test and test";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        try {
            start = Calendars.fromDate(df.parse("16-03-2024"));;
            finish = null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess();

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new Phase(phaseType, phaseDescription, finalStart, finalFinish));

    }

    @Test
    void ensureItCantCreatePhaseWithSameEndAndStartDate() {

        String phaseType = "TestPhase";
        String phaseDescription = "Testing purposes";
        String phaseStatus = "Test test and test";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        try {
            start = Calendars.fromDate(df.parse("16-03-2024"));
            finish = Calendars.fromDate(df.parse("16-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess();

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new Phase(phaseType, phaseDescription, finalStart, finalFinish));

    }

    @Test
    void ensureItCantCreatePhaseWithStartDateAfterEndDate() {

        String phaseType = "TestPhase";
        String phaseDescription = "Testing purposes";
        String phaseStatus = "Test test and test";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar start = null;
        Calendar finish = null;
        try {
            start = Calendars.fromDate(df.parse("18-03-2024"));
            finish = Calendars.fromDate(df.parse("16-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess();

        Calendar finalFinish = finish;
        Calendar finalStart = start;
        assertThrows(IllegalArgumentException.class, () -> new Phase(phaseType, phaseDescription, finalStart, finalFinish));

    }



}
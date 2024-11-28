package jobs4u.base.jobopeningmanagement.domain;

import eapli.framework.time.util.Calendars;
import jobs4u.base.applicationmanagement.domain.*;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.recruitmentprocessmanagement.domain.Phase;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcessStatus;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcessStatusEnum;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;
import org.junit.Test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class JobOpeningTest {

    WorkModeDTO workMode = new WorkModeDTO("remote");
    ContractTypeDTO contractType = new ContractTypeDTO("full-time");
    JobReference jobReference = new JobReference("ISEP", 3);
    String streetName = "StreetName";
    String city = "City";
    String district = "District";
    String streetNumber = "14th";
    String zipcode = "1234-234";
    Address address = new Address(streetName, city, district, streetNumber, zipcode);

    RequirementAnswer requirementAnswer = RequirementAnswer.valueOf("../plugins-config-file/requirement/r-answer-1.txt");
    RequirementResult requirementResult = RequirementResult.valueOf(true);
    ApplicationFile file = new ApplicationFile(new File("example.txt"));
    Set<ApplicationFile> files = new HashSet<>();

    Date date = new Date(2024 - 1900, Calendar.JANUARY, 12);

    InterviewAnswer interviewAnswer = InterviewAnswer.valueOf("../plugins-config-file/interview/i-answer-1.txt");
    Interview interview = new Interview("interview", new Date(2024 - 1900, Calendar.MARCH, 6),
            new InterviewResult(88, "the grade is above 50"), interviewAnswer);


    public RequirementSpecification jobOpeningRequirement() {
        String name = "Senior Developer";
        String description = "Database maintenance";
        String fullClassName = "jobs4u.plugin.core.adapter.RequirementPluginAdapter";
        String dataImporter = "jobs4u.plugin.core.adapter.FileManagementAdapter";
        return new RequirementSpecification(name, description, fullClassName, "plugins-config-file/requirement/r-config-1.txt", dataImporter);
    }

    @Test
    public void ensureMustHaveTitle() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening(null, contractType, workMode,
                address, 15, "description", jobOpeningRequirement(), jobReference)
        );
    }

    @Test
    public void ensureMustHaveContractType() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening("function", null, workMode,
                address, 15, "description", jobOpeningRequirement(), jobReference)
        );
    }

    @Test
    public void ensureMustHaveWorkMode() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening("function", contractType, null,
                address, 15, "description", jobOpeningRequirement(), jobReference)
        );
    }

    @Test
    public void ensureMustHaveAddress() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening("function", contractType, workMode,
                null, 15, "description", jobOpeningRequirement(), jobReference)
        );
    }

    @Test
    public void ensureMustHaveNumberVacancies() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening("function", contractType, workMode,
                address, null, "description", jobOpeningRequirement(), jobReference)
        );
    }

    @Test
    public void ensureMustHaveDescription() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening("function", contractType, workMode,
                address, 15, null, jobOpeningRequirement(), jobReference)
        );
    }

    @Test
    public void ensureMustHaveRequirements() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening("function", contractType, workMode,
                address, 15, "description", null, jobReference)
        );
    }

    @Test
    public void ensureMustHaveLastJobReference() {
        assertThrows(IllegalArgumentException.class, () -> new JobOpening("function", contractType, workMode,
                address, 15, "description", jobOpeningRequirement(), null)
        );
    }

    @Test
    public void ensureHasGivenIdentity() {
        JobOpening opening = new JobOpening("Senior Dev", contractType, workMode, address, 15,
                "description", jobOpeningRequirement(), jobReference);

        JobReference jobReference1 = new JobReference("ISEP", 4);
        assertEquals(opening.identity(), jobReference1);
    }

    @Test
    public void ensureSelectReqSpecificationBeforeRecruitmentProccess() {
        JobOpening opening = new JobOpening("Senior Dev", contractType, workMode, address, 15,
                "description", jobOpeningRequirement(), jobReference);
        opening.updateStatusToStarted();
        assertThrows(NullPointerException.class, () -> opening.changeRequirementSpecification(jobOpeningRequirement()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCantChangeJobReference() {
        JobOpening opening = new JobOpening("Senior Dev", contractType, workMode, address, 15,
                "description", jobOpeningRequirement(), jobReference);

        EditableInformation e = new EditableInformation("Job Reference");

        opening.changeInformation(e, "ISEP-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCanEditPublicInformationOnlyIfStatusIsValid() {
        JobOpening opening = new JobOpening("Senior Dev", contractType, workMode, address, 15,
                "description", jobOpeningRequirement(), jobReference);
        opening.updateStatusToStarted();

        EditableInformation e = EditableInformation.DESCRIPTION;

        opening.changeInformation(e, "New Description");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCanEditReqSpecificationOnlyIfPhaseIsValid() {
        JobOpening opening = new JobOpening("Senior Dev", contractType, workMode, address, 15,
                "description", jobOpeningRequirement(), jobReference);
        opening.updateStatusToStarted();

        List<Phase> listA = new ArrayList<>();

        Calendar start = null;
        Calendar finish = null;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            start = Calendars.fromDate(df.parse("16-03-2004"));
            finish = Calendars.fromDate(df.parse("18-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(start, finish, listA, new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED));
        Phase phase = new Phase("Test", "Screening", start, finish);
        listA.add(phase);
        recruitmentProcess.addPhases(listA);
        opening.addRecruitmentProcess(recruitmentProcess);

        opening.changeRequirementSpecification(new RequirementSpecification("Test.jar", "Test", "test.new.plugin.Classe", "plugins-config-file/requirement/r-config-1.txt", "test.new.plugin.Classe"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCanEditInterviewModelOnlyIfPhaseIsValid() {
        JobOpening opening = new JobOpening("Senior Dev", contractType, workMode, address, 15,
                "description", jobOpeningRequirement(), jobReference);
        opening.updateStatusToStarted();

        List<Phase> listA = new ArrayList<>();

        Calendar start = null;
        Calendar finish = null;
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            start = Calendars.fromDate(df.parse("16-03-2004"));
            finish = Calendars.fromDate(df.parse("18-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcess recruitmentProcess = new RecruitmentProcess(start, finish, listA, new RecruitmentProcessStatus(RecruitmentProcessStatusEnum.PLANNED));
        Phase phase = new Phase("Test", "Interview", start, finish);
        listA.add(phase);
        recruitmentProcess.addPhases(listA);
        opening.addRecruitmentProcess(recruitmentProcess);

        opening.changeInterviewModel(new InterviewModel("Test.jar", "Test", "test.new.plugin.Classe", "plugins-config-file/requirement/r-config-1.txt", "test.new.Classe"));
    }

    @Test
    public void ensureJobOpeningHasInterview() {
        files.add(file);
        Application application = new Application(requirementAnswer, requirementResult, files, date, interview);

        JobOpening opening = new JobOpening("Senior Dev", contractType, workMode, address, 15,
                "description", jobOpeningRequirement(), jobReference);

        Set<Application> applicationsSet = new HashSet<>();
        application.applicationStatus().updateStatusDescriptionAsACCEPTED();
        applicationsSet.add(application);

        opening.setApplications(applicationsSet);

        for (Application a : opening.getApplications()) {
            assertEquals(a.interview(), interview);
        }

    }
}
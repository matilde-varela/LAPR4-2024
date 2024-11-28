package jobs4u.base.app.backoffice.console.presentation.recruitmentprocess;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.time.domain.model.DateInterval;
import eapli.framework.time.domain.model.TimeInterval;
import eapli.framework.time.util.Calendars;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.recruitmentprocessmanagement.application.SetupRecruitmentProcessController;
import jobs4u.base.recruitmentprocessmanagement.domain.PhaseTypeEnum;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.dto.AllPhasesDTO;
import jobs4u.base.recruitmentprocessmanagement.dto.PhaseDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class SetupRecruitmentProcessUI extends AbstractUI {

    private final List<String> allPhases = new ArrayList<>();

    private final SetupRecruitmentProcessController controller = new SetupRecruitmentProcessController();

    @Override
    protected boolean doShow() {

        allPhases.add(String.valueOf(PhaseTypeEnum.APPLICATION));
        allPhases.add(String.valueOf(PhaseTypeEnum.SCREENING));
        allPhases.add(String.valueOf(PhaseTypeEnum.INTERVIEW));
        allPhases.add(String.valueOf(PhaseTypeEnum.ANALYSIS));
        allPhases.add(String.valueOf(PhaseTypeEnum.RESULTS));

        List<JobOpening> listUnfinishedJobOpenings = controller.getAllIncompleteJobOpenings();
        List<String> jobOpeningRefs = new ArrayList<>();

        System.out.println("Choose from one of the following job openings to setup a recruitment process:");
        for (JobOpening jobOpening : listUnfinishedJobOpenings) {
            jobOpeningRefs.add(jobOpening.jobReference().toString());
            System.out.println("Job Opening Reference: " + jobOpening.jobReference().toString() + " | Description: " + jobOpening.descriptionOfJobOpening().description());
        }

        JobOpening jobOpening = chooseJobOpening(listUnfinishedJobOpenings, jobOpeningRefs);

        AllPhasesDTO allPhasesDTO = createAllPhases();

        RecruitmentProcess recruitmentProcess = controller.setupRecruitmentProcess(allPhasesDTO.getListOfPhases().get(0).getInitialDate(), allPhasesDTO.getListOfPhases().get(allPhasesDTO.getListOfPhases().size()-1).getFinalDate(), allPhasesDTO, jobOpening);

        controller.setupJobOpeningWithRecruitmentProcess(recruitmentProcess, jobOpening);

        System.out.println("Worked!");

        return false;
    }

    public JobOpening chooseJobOpening(List<JobOpening> list, List<String> listOfJobRefs) {

        String customerCode = Console.readNonEmptyLine("Customer code: (The code before the '-')", "Its mandatory to fill this area.");
        int sequentialCode = Integer.parseInt(Console.readNonEmptyLine("Sequential code: (The code after the '-')", "Its mandatory to fill this area."));

        JobOpening jobOpening;

        if(!controller.checkByJobReference(customerCode, sequentialCode)){
            System.out.printf("Wrong job reference.\nTry again:\n");
            customerCode = Console.readNonEmptyLine("Customer code: (The code before the '-')", "Its mandatory to fill this area.");
            sequentialCode = Integer.parseInt(Console.readNonEmptyLine("Sequential code: (The code after the '-')", "Its mandatory to fill this area."));

        }
        jobOpening = controller.getJobOpeningByJobReference(customerCode, sequentialCode).get();
        return jobOpening;

    }

    public AllPhasesDTO createAllPhases() {

        List<PhaseDTO> list = new ArrayList<>();

        AllPhasesDTO allPhasesDTO = new AllPhasesDTO(list);

        String option = Console.readNonEmptyLine("Is this recruitment process going to have an interview? (Yes/No)", "It's mandatory for this area to be filled.");

        if (option.equals("Yes") || option.equals("y")) {
            for (int i = 0; i < allPhases.size(); i++) {
                allPhasesDTO.getListOfPhases().add(createPhaseDTO(i));
            }
        } else if (option.equals("No") || option.equals("n")) {

            allPhases.remove("Interviews");

            for (int i = 0; i < allPhases.size(); i++) {
                allPhasesDTO.getListOfPhases().add(createPhaseDTO(i));
            }


        }
        return allPhasesDTO;
    }

    public PhaseDTO createPhaseDTO(int i) {

        System.out.println("----------------------------");
        System.out.println("Phase: " + allPhases.get(i));
        String description = allPhases.get(i);
        String initialDate = Console.readNonEmptyLine("When will this phase start? DD-MM-YYYY", "It's mandatory to fill this area.");
        String finalDate = Console.readNonEmptyLine("When will this phase end? DD-MM-YYYY", "It's mandatory to fill this area.");
        System.out.println("----------------------------");

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");


        Calendar initialInfo = null;
        Calendar endInfo = null;
        try {
            initialInfo = Calendars.fromDate(df.parse(initialDate));
            endInfo = Calendars.fromDate(df.parse(finalDate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        DateInterval dateInterval = new DateInterval(initialInfo, endInfo);

        PhaseDTO phaseDTO = new PhaseDTO(allPhases.get(i), description, dateInterval);

        return phaseDTO;
    }

    @Override
    public String headline() {
        return "Setup a recruitment process";
    }


}

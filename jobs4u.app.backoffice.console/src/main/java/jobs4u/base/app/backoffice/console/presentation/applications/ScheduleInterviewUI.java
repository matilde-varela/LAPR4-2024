package jobs4u.base.app.backoffice.console.presentation.applications;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.authz.EnableDisableUserUI;
import jobs4u.base.app.backoffice.console.presentation.interviewmodel.InterviewModelDtoPrinter;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.JobOpeningDTOPrinter;
import jobs4u.base.applicationmanagement.application.ScheduleInterviewController;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.interviewmodelmanagement.dto.InterviewModelDTO;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class ScheduleInterviewUI extends AbstractUI {
    private final ScheduleInterviewController controller = new ScheduleInterviewController();
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableDisableUserUI.class);

    @Override
    protected boolean doShow() {
        String dateInput;
        String timeInput;
        LocalDate interviewDate;
        LocalTime interviewTime;

        SelectWidget<JobOpeningDTO> selectorJobOpening;
        final JobOpeningDTO jobOpeningDTO;

        SelectWidget<ApplicationDTO> selectorApplication;
        final ApplicationDTO applicationDTO;

        try {

            selectorJobOpening = new SelectWidget<>("Please select a job opening", controller.getJobOpeningsList(), new JobOpeningDTOPrinter());
            selectorJobOpening.show();
            jobOpeningDTO = selectorJobOpening.selectedElement();

            if (jobOpeningDTO == null) {
                System.out.println(" Exiting...");
                return false;
            }

            selectorApplication = new SelectWidget<>("Please select an application", controller.getApplicationList(jobOpeningDTO), new ApplicationDtoPrinter());
            selectorApplication.show();
            applicationDTO = selectorApplication.selectedElement();

            if (applicationDTO == null) {
                System.out.println("Exiting...");
                return false;
            }

            do {
                dateInput = Console.readLine("Insert the date for the interview (YYYY-MM-DD): ");
                timeInput = Console.readLine("Insert the time for the interview (hh:mm): ");

                try {
                    interviewDate = LocalDate.parse(dateInput);
                    interviewTime = LocalTime.parse(timeInput);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date or time format. Please try again.");
                    continue;
                }
                break;
            } while (true);

            return controller.updateApplication(applicationDTO,dateInput,timeInput);
        } catch (NullPointerException ex) {
            System.out.println("Error: Please select a job opening, an application, and insert the date and time for the interview."+ex.getMessage());

        } catch (IntegrityViolationException | ConcurrencyException ex) {LOGGER.error("Error, please try again", ex);
            System.out.println("An unexpected error occurred. Please try again or contact the administrator.");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return false;

    }

    @Override
    public String headline() {
        return "Schedule an interview";
    }
}

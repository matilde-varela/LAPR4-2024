package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.applications.ApplicationDtoPrinter;
import jobs4u.base.app.backoffice.console.presentation.authz.EnableDisableUserUI;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.JobOpeningDTOPrinter;
import jobs4u.base.applicationmanagement.application.ScheduleInterviewController;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.application.SendNotificationEmailController;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public class SendNotificationEmailUI extends AbstractUI {
    private final SendNotificationEmailController controller = new SendNotificationEmailController();
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableDisableUserUI.class);

    @Override
    protected boolean doShow() {

        SelectWidget<JobOpeningDTO> selectorJobOpening;
        final JobOpeningDTO jobOpeningDTO;

        try {
            selectorJobOpening = new SelectWidget<>("Please select a job opening", controller.getJobOpeningsList(), new JobOpeningDTOPrinter());
            selectorJobOpening.show();
            jobOpeningDTO = selectorJobOpening.selectedElement();

            String userPassword = Console.readLine("Please insert your password: ");
            return controller.sendEmail(jobOpeningDTO, userPassword);
        } catch (NullPointerException ex) {
            System.out.println("Error: Please select a job opening."+ex.getMessage());

        } catch (IntegrityViolationException | ConcurrencyException ex) {LOGGER.error("Error, please try again", ex);
            System.out.println("An unexpected error occurred. Please try again or contact the administrator.");
        }
        return false;

    }

    @Override
    public String headline() {
        return "Send a Notification Email";
    }
}

package jobs4u.base.app.backoffice.console.presentation.jobopening.publishing;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.JobOpeningDTOPrinter;
import jobs4u.base.jobopeningmanagement.application.PublishResultJobOpeningController;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public class PublishResultJobOpeningUI extends AbstractUI {

    private final static PublishResultJobOpeningController controller = new PublishResultJobOpeningController();

    @Override
    protected boolean doShow() {
        try {
            final String userPassword = Console.readLine("Please enter your password: ");
            Iterable<JobOpeningDTO> joList = controller.getJobOpeningsList();
            SelectWidget<JobOpeningDTO> sw = new SelectWidget<>("Select a job opening to publish the results of", joList, new JobOpeningDTOPrinter());
            sw.show();
            final JobOpeningDTO jo = sw.selectedElement();
            controller.publishResultForJobOpening(jo, userPassword);
            System.out.println("The results have been successfully published.");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public String headline() {
        return "Publish results of job opening";
    }
}

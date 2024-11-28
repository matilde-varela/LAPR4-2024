package jobs4u.base.app.backoffice.console.presentation.requirementspecification;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.requirementsmanagement.application.VerifyRequirementController;

public class VerifyRequirementUI extends AbstractUI {

    private final VerifyRequirementController controller = new VerifyRequirementController();

    @Override
    protected boolean doShow() {
        final JobOpeningDTO jo;
        Iterable<JobOpeningDTO> jobOpeningDTOS = controller.getJobOpeningList();

        try {
            SelectWidget<JobOpeningDTO> sw = new SelectWidget<>("Select the job opening to verify the requeriments of the applications.", jobOpeningDTOS, new JobOpeningDTOPrinter());
            sw.show();
            jo = sw.selectedElement();
            if (controller.verifyAvailableRequirements(jo)) {
                System.out.println("Requirements verified sucessfully");
            } else {
                System.out.println("An error occorred while verifying requirements. Please try again.");
            }
        } catch (NullPointerException e) {
            System.out.println("None of the currently open job openings are in the screening phase.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public String headline() {
        return "Verification of Requirements Specification";
    }
}

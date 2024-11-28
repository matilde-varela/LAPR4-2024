package jobs4u.base.app.backoffice.console.presentation.requirementspecification;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.authz.EnableDisableUserUI;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

import jobs4u.base.requirementsmanagement.dto.RequirementSpecificationDTO;
import jobs4u.base.requirementsmanagement.application.SelectRequirementSpecificationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectRequirementSpecificationUI extends AbstractUI {
    private final SelectRequirementSpecificationController controller = new SelectRequirementSpecificationController();
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableDisableUserUI.class);

    @Override
    protected boolean doShow() {
        SelectWidget<JobOpeningDTO> selector_jo;
        final JobOpeningDTO jobOpeningDTO;
        SelectWidget<RequirementSpecificationDTO> selector_rs;
        final RequirementSpecificationDTO reqSpeciDTO;

        try {
            selector_jo = new SelectWidget<>("Select a job opening", controller.activeJobOpenings(), new JobOpeningDTOPrinter());
            selector_jo.show();
            jobOpeningDTO = selector_jo.selectedElement();

            selector_rs = new SelectWidget<>("Select a requirement specification", controller.availableRequirementSpecification(), new RequirementSpecificationDTOPrinter());
            selector_rs.show();
            reqSpeciDTO = selector_rs.selectedElement();

            return controller.updateRequirementSpecification(jobOpeningDTO, reqSpeciDTO);
        } catch (NullPointerException ex) {
            System.out.println("Error: Didn't select a job opening or a requirement specification.");

        } catch (IntegrityViolationException | ConcurrencyException ex) {LOGGER.error("Error performing the operation", ex);
            System.out.println("An unexpected error occurred. Please try again or contact the administrator.");
        }

        return false;

    }

    @Override
    public String headline() {
        return "Select a requirement specification";
    }
}

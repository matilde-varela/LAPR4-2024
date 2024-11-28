package jobs4u.base.app.backoffice.console.presentation.interviewmodel;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.authz.EnableDisableUserUI;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.JobOpeningDTOPrinter;
import jobs4u.base.interviewmodelmanagement.application.SelectInterviewModelController;
import jobs4u.base.interviewmodelmanagement.dto.InterviewModelDTO;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectInterviewModelUI extends AbstractUI {
    private final SelectInterviewModelController controller = new SelectInterviewModelController();
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableDisableUserUI.class);

    @Override
    protected boolean doShow() {

        SelectWidget<JobOpeningDTO> selectorJobOpening;
        final JobOpeningDTO jobOpeningDTO;

        SelectWidget<InterviewModelDTO> selectorInterviewModel;
        final InterviewModelDTO interviewModelDTO;

        try {

            selectorJobOpening = new SelectWidget<>("Please select a job opening", controller.activeJobOpenings(), new JobOpeningDTOPrinter());
            selectorJobOpening.show();
            jobOpeningDTO = selectorJobOpening.selectedElement();

            selectorInterviewModel = new SelectWidget<>("Please select an interview model", controller.availableInterviewModels(), new InterviewModelDtoPrinter());
            selectorInterviewModel.show();
            interviewModelDTO = selectorInterviewModel.selectedElement();

            return controller.updateJobOpening(jobOpeningDTO, interviewModelDTO);
        } catch (NullPointerException ex) {
            System.out.println("Error: Please select a job opening and an interview model."+ex.getMessage());

        } catch (IntegrityViolationException | ConcurrencyException ex) {LOGGER.error("Error, please try again", ex);
            System.out.println("An unexpected error occurred. Please try again or contact the administrator.");
        }

        return false;

    }

    @Override
    public String headline() {
        return "Select an interview model";
    }
}

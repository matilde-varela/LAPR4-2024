package jobs4u.base.app.backoffice.console.presentation.interviewmodel;

import eapli.framework.actions.Action;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.SelectRequirementSpecificationUI;

public class SelectInterviewModelAction implements Action {
    @Override
    public boolean execute() {
        return new SelectInterviewModelUI().show();
    }
}

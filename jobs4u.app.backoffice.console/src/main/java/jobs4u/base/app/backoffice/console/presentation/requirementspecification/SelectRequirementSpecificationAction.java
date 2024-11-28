package jobs4u.base.app.backoffice.console.presentation.requirementspecification;

import eapli.framework.actions.Action;

public class SelectRequirementSpecificationAction implements Action {
    @Override
    public boolean execute() {
        return new SelectRequirementSpecificationUI().show();
    }
}

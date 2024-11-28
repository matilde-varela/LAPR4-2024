package jobs4u.base.app.backoffice.console.presentation.requirementspecification;


import eapli.framework.actions.Action;

public class VerifyRequirementAction implements Action {
    @Override
    public boolean execute() {
        return new VerifyRequirementUI().show();
    }
}

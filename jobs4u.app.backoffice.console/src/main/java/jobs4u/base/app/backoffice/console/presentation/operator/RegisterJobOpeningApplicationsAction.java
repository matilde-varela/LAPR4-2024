package jobs4u.base.app.backoffice.console.presentation.operator;


import eapli.framework.actions.Action;


public class RegisterJobOpeningApplicationsAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterJobOpeningApplicationUI().show();
    }
}

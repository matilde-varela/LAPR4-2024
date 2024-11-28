package jobs4u.base.app.backoffice.console.presentation.languageengineer;

import eapli.framework.actions.Action;


public class RegisterPluginAction implements Action {
    @Override
    public boolean execute() {
        return new RegisterPluginUI().show();
    }
}

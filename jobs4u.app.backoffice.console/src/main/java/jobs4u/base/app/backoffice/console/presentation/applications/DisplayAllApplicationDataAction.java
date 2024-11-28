package jobs4u.base.app.backoffice.console.presentation.applications;

import eapli.framework.actions.Action;

public class DisplayAllApplicationDataAction implements Action {

    @Override
    public boolean execute() {
        return new DisplayAllApplicationDataUI().show();
    }

}

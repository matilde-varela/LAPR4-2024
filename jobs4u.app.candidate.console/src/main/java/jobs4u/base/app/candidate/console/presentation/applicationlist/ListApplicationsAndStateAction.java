package jobs4u.base.app.candidate.console.presentation.applicationlist;

import eapli.framework.actions.Action;

public class ListApplicationsAndStateAction implements Action {

    @Override
    public boolean execute(){
        return new ListApplicationsAndStateUI().show();
    }
}

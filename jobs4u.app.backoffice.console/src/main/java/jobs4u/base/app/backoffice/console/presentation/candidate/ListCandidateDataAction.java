package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.actions.Action;

public class ListCandidateDataAction implements Action {

    @Override
    public boolean execute() {
        return new ListCandidateDataUI().show();
    }

}

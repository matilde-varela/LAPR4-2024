package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.actions.Action;

public class ListAllCandidatesAction implements Action {
    @Override
    public boolean execute() {
        return new ListCandidatesUI().show();
    }
}

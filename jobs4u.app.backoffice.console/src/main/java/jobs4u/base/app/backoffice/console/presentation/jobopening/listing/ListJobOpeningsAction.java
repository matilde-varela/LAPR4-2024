package jobs4u.base.app.backoffice.console.presentation.jobopening.listing;

import eapli.framework.actions.Action;

public class ListJobOpeningsAction implements Action {

    @Override
    public boolean execute() {
        return new ListJobOpeningsUI().show();
    }
}

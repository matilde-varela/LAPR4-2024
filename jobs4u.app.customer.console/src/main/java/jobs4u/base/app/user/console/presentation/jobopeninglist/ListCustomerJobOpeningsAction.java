package jobs4u.base.app.user.console.presentation.jobopeninglist;

import eapli.framework.actions.Action;

public class ListCustomerJobOpeningsAction implements Action {
    @Override
    public boolean execute() {
        return new ListCustomerJobOpeningsUI().show();
    }
}

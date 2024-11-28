package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.actions.Action;
import jobs4u.base.app.backoffice.console.presentation.customer.RegisterCustomerUI;

public class RegisterCandidateAction implements Action {
    @Override
    public boolean execute() {
        return new RegisterCandidateUI().show();
    }
}

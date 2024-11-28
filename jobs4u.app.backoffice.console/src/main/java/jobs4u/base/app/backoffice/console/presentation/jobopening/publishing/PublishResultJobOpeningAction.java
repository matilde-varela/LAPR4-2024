package jobs4u.base.app.backoffice.console.presentation.jobopening.publishing;

import eapli.framework.actions.Action;

public class PublishResultJobOpeningAction implements Action {
    @Override
    public boolean execute() {
        return new PublishResultJobOpeningUI().show();
    }
}

package jobs4u.base.app.backoffice.console.presentation.jobopening.editing;

import eapli.framework.actions.Action;

public class EvaluateInterviewsAction implements Action {

    @Override
    public boolean execute() {
        return new EvaluateInterviewsUI().show();
    }
}

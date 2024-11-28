package jobs4u.base.app.backoffice.console.presentation.rank;

import eapli.framework.actions.Action;

public class ApplicationRankingAction   implements Action {
    @Override
    public boolean execute() {
        return new ApplicationRankingUI().show();
    }

}
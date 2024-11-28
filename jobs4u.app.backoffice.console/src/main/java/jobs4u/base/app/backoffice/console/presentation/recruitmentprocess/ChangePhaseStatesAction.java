package jobs4u.base.app.backoffice.console.presentation.recruitmentprocess;

import eapli.framework.actions.Action;

public class ChangePhaseStatesAction implements Action {
    @Override
    public boolean execute() { return new ChangePhaseStatesUI().show(); }
}

package jobs4u.base.app.backoffice.console.presentation.recruitmentprocess;

import eapli.framework.actions.Action;


public class SetupRecruitmentProcessAction implements Action {
    @Override
    public boolean execute() { return new SetupRecruitmentProcessUI().show(); }
}

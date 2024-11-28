package jobs4u.base.app.backoffice.console.presentation.applications;

import eapli.framework.actions.Action;
import jobs4u.base.app.backoffice.console.presentation.interviewmodel.SelectInterviewModelUI;

public class ScheduleInterviewAction implements Action {
    @Override
    public boolean execute() {
        return new ScheduleInterviewUI().show();
    }
}

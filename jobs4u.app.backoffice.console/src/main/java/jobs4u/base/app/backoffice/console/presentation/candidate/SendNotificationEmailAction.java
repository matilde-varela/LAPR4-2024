package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.actions.Action;
import jobs4u.base.app.backoffice.console.presentation.applications.ScheduleInterviewUI;

public class SendNotificationEmailAction implements Action {
    @Override
    public boolean execute() {
        return new SendNotificationEmailUI().show();
    }
}

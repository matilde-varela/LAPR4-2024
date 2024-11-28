package jobs4u.base.app.candidate.console.presentation.notifications;

import eapli.framework.actions.Action;

public class NotificationInboxCandidateAction implements Action {
    @Override
    public boolean execute() {
        return new NotificationInboxCandidateUI().show();
    }
}

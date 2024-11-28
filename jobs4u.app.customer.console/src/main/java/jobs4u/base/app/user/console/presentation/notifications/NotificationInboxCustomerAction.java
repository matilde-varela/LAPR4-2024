package jobs4u.base.app.user.console.presentation.notifications;

import eapli.framework.actions.Action;

public class NotificationInboxCustomerAction implements Action {
    @Override
    public boolean execute() {
        return new NotificationInboxCustomerUI().show();
    }
}

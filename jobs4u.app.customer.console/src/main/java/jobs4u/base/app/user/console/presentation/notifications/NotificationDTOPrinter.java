package jobs4u.base.app.user.console.presentation.notifications;

import eapli.framework.visitor.Visitor;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;

public class NotificationDTOPrinter implements Visitor<NotificationDTO> {
    @Override
    public void visit(NotificationDTO visitee) {
        System.out.printf("%n%s",visitee.toString());
    }
}

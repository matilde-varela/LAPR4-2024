package jobs4u.base.app.user.console.presentation.notifications;

import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.notificationmanagement.application.NotificationInboxCandidateController;
import jobs4u.base.notificationmanagement.application.NotificationInboxCustomerController;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;
import org.apache.commons.lang3.tuple.Pair;

public class NotificationInboxCustomerUI extends AbstractUI{

    NotificationInboxCustomerController controller = new NotificationInboxCustomerController();

    private Username username;

    NotificationDTOPrinter notificationDTOPrinter = new NotificationDTOPrinter();

    @Override
    protected boolean doShow() {

        username = controller.getSessionCredentials();
        String password = Console.readLine("Please provide your password again:");

        //establish connection
        Pair<Boolean, String> pair = controller.establishConnection(username, password);
        boolean connectionEstablished = pair.getKey();

        int option = 0;

        System.out.println(pair.getValue());
        if (connectionEstablished) {

            controller.startCheckForNotificationsThread(username);

            while(option != 3) {
                option = Console.readInteger("\nPlease choose an option:\n1) Notifications To Be Seen\n2) Notifications Already Seen\n3) Exit\n");

                switch (option) {
                    case 1:
                        Iterable<NotificationDTO> unseenNotificationList = controller.getAllUnseenNotifications(username);
                        if (unseenNotificationList.iterator().hasNext()) {
                            for (NotificationDTO notif : unseenNotificationList) {
                                notificationDTOPrinter.visit(notif);
                            }
                        } else {
                            System.out.println("==========================================");
                            System.out.println("     NO NEW NOTIFICATIONS TO BE SEEN      ");
                            System.out.println("==========================================");
                        }
                        break;
                    case 2:
                        String date = Console.readLine("From which date do you want to see the notifications? (DD-MM-YYYY)");
                        Iterable<NotificationDTO> seenNotificationList = controller.getAllSeenNotifications(username, date);
                        if (seenNotificationList.iterator().hasNext()) {
                            for (NotificationDTO notif : seenNotificationList) {
                                notificationDTOPrinter.visit(notif);
                            }
                        } else {
                            System.out.println("==========================================");
                            System.out.println("    NO NOTIFICATIONS IN THAT TIME FRAME   ");
                            System.out.println("==========================================");
                        }
                        break;
                }
            }

            try {
                controller.closeCheckForNotificationsThread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Pair<Boolean, String> response = controller.closeConnection();
            System.out.println(response.getValue());
        }

        return false;
    }

    @Override
    public String headline() {
        return "Notification Inbox For Candidate\n";
    }
}

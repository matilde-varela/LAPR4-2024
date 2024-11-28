package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jakarta.persistence.TypedQuery;
import jobs4u.base.Application;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.notificationmanagement.domain.Notification;
import jobs4u.base.notificationmanagement.dto.NotificationDTO;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class JpaNotificationRepository
        extends JpaAutoTxRepository<Notification, Long, Long>
        implements NotificationRepository {
    public JpaNotificationRepository(final TransactionalContext autoTx) {
        super(autoTx, "id");
    }

    public JpaNotificationRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "id");
    }

    @Override
    public boolean checkForUnseenNotificationsByUsername(String username) {
        String query = "SELECT e \n" +
                "FROM Notification e \n" +
                "WHERE e.notificationRecipient.recipient = :username\n" +
                "AND e.notificationStatus.status = 'UNSEEN'";
        final TypedQuery<Notification> q = createQuery(query, Notification.class);
        q.setParameter("username", username.toString());
        return !(q.getResultList().isEmpty());
    }

    @Override
    public Iterable<Notification> getUnseenNotificationsByUsername(String username) {
        String query = "SELECT e \n" +
                "FROM Notification e \n" +
                "WHERE e.notificationRecipient.recipient = :username\n" +
                "AND e.notificationStatus.status = 'UNSEEN'";
        final TypedQuery<Notification> q = createQuery(query, Notification.class);
        q.setParameter("username", username.toString());
        return q.getResultList();
    }

    @Override
    public Iterable<Notification> getSeenNotificationsByUsernameAndDate(String username, Calendar date) {
        String query = "SELECT e \n" +
                "FROM Notification e \n" +
                "WHERE e.notificationRecipient.recipient = :username\n" +
                "AND e.notificationStatus.status = 'SEEN'\n" +
                "AND e.notificationDate.date > :date\n";
        final TypedQuery<Notification> q = createQuery(query, Notification.class);
        q.setParameter("username", username.toString());
        q.setParameter("date", date);
        return q.getResultList();
    }
}

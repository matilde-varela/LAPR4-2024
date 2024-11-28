package jobs4u.base.applicationmanagement.application.eventhandlers;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;
import jobs4u.base.jobopeningmanagement.domain.events.ApplicationStatusChangedEvent;

public class ApplicationStatusChangedWatchDog implements EventHandler {
    @Override
    public void onEvent(DomainEvent domainevent) {
        assert domainevent instanceof ApplicationStatusChangedEvent;

        final ApplicationStatusChangedEvent event = (ApplicationStatusChangedEvent) domainevent;

        final NotifyCandidateOnApplicationStatusChangedController
                controller = new NotifyCandidateOnApplicationStatusChangedController();
        controller.notifyCandidateOfChangedStatus(event);
    }
}

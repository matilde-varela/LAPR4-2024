package jobs4u.base.jobopeningmanagement.application.eventhandlers;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;
import jobs4u.base.jobopeningmanagement.domain.events.JobOpeningPhaseChangedEvent;
import jobs4u.base.jobopeningmanagement.domain.events.JobOpeningResultsPublishedEvent;

public class JobOpeningPhaseChangedWatchDog implements EventHandler {
    @Override
    public void onEvent(DomainEvent domainevent) {
        assert domainevent instanceof JobOpeningPhaseChangedEvent;

        final JobOpeningPhaseChangedEvent event = (JobOpeningPhaseChangedEvent) domainevent;

        final NotifyCustomerOfChangeInJobOpeningPhasesController
                controller = new NotifyCustomerOfChangeInJobOpeningPhasesController();
        controller.notifyCustomerOfChangedStatus(event);
    }
}

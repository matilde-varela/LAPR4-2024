package jobs4u.base.customermanagement.application.eventhandlers;

import eapli.framework.domain.events.DomainEvent;
import eapli.framework.infrastructure.pubsub.EventHandler;
import jobs4u.base.customermanagement.domain.events.NewCustomerUserRegisteredEvent;

public class NewCustomerUserRegisteredWatchDog implements EventHandler {
    @Override
    public void onEvent(final DomainEvent domainevent) {
        assert domainevent instanceof NewCustomerUserRegisteredEvent;

        final NewCustomerUserRegisteredEvent event = (NewCustomerUserRegisteredEvent) domainevent;

        final AddCustomerOnNewCustomerUserRegisteredController
                controller = new AddCustomerOnNewCustomerUserRegisteredController();
        controller.registerNewCustomer(event);
    }

}

/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jobs4u.base.app.backoffice.console;

import jobs4u.base.app.backoffice.console.presentation.MainMenu;
import jobs4u.base.app.common.console.BaseApplication;
import jobs4u.base.app.common.console.presentation.authz.LoginUI;
import jobs4u.base.applicationmanagement.application.eventhandlers.ApplicationStatusChangedWatchDog;
import jobs4u.base.candidatemanagement.application.eventhandlers.NewCandidateUserRegisteredWatchDog;
import jobs4u.base.candidatemanagement.domain.events.NewCandidateUserRegisteredEvent;
import jobs4u.base.customermanagement.application.eventhandlers.NewCustomerUserRegisteredWatchDog;
import jobs4u.base.customermanagement.domain.events.NewCustomerUserRegisteredEvent;
import jobs4u.base.infrastructure.authz.AuthenticationCredentialHandler;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.eventhandlers.JobOpeningPhaseChangedWatchDog;
import jobs4u.base.jobopeningmanagement.domain.events.*;
import jobs4u.base.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.pubsub.EventDispatcher;
import jobs4u.base.usermanagement.domain.BaseRoles;

/**
 * @author Paulo Gandra Sousa
 */
@SuppressWarnings("squid:S106")
public final class BaseBackoffice extends BaseApplication {

    /**
     * avoid instantiation of this class.
     */
    private BaseBackoffice() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        System.out.println("+= BackOffice Application =======");
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(), new PlainTextEncoder());

        new BaseBackoffice().run(args);
    }

    @Override
    protected void doMain(final String[] args) {
        // login and go to main menu
        if (new LoginUI(new AuthenticationCredentialHandler(), BaseRoles.ADMIN, BaseRoles.CUSTOMER_MANAGER,
                BaseRoles.OPERATOR, BaseRoles.LANGUAGE_ENGINEER).show()) {
            // go to main menu
            final MainMenu menu = new MainMenu();
            menu.mainLoop();
        }
    }

    @Override
    protected String appTitle() {
        return "BackofficeApp";
    }

    @Override
    protected String appGoodbye() {
        return "BackofficeApp";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        dispatcher.subscribe(new NewCustomerUserRegisteredWatchDog(), NewCustomerUserRegisteredEvent.class);
        dispatcher.subscribe(new NewCandidateUserRegisteredWatchDog(), NewCandidateUserRegisteredEvent.class);
        dispatcher.subscribe(new ApplicationStatusChangedWatchDog(), ApplicationStatusChangedEvent.class);
        dispatcher.subscribe(new JobOpeningPhaseChangedWatchDog(), JobOpeningPhaseChangedEvent.class);
    }
}

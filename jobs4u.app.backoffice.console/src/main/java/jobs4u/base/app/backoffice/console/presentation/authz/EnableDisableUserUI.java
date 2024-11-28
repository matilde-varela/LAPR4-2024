/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jobs4u.base.app.backoffice.console.presentation.authz;

import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.usermanagement.application.EnableDisableUserController;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rita Barbosa
 */
@SuppressWarnings("squid:S106")
public class EnableDisableUserUI extends AbstractUI {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnableDisableUserUI.class);

    private final EnableDisableUserController theController = new EnableDisableUserController();

    protected boolean doShow() {
        System.out.println("1. Enable");
        System.out.println("2. Disable");
        System.out.println("0. Exit");

        int option = Console.readOption(1, 2, 0);
        Iterable<SystemUser> iterable = null;
        SelectWidget<SystemUser> selector;
        SystemUser user;

        try {
            switch (option) {
                case 1:
                    iterable = theController.deactivatedUsers();
                    break;
                case 2:
                    iterable = theController.activeUsers();
                    break;
                default:
                    System.out.println("No valid option selected");
                    break;
            }

            if (iterable != null) {
                selector = new SelectWidget<>("Select user", iterable, new SystemUserPrinter());
                selector.show();
                user = selector.selectedElement();
                if (user != null) {
                    if (option == 1) {
                        theController.activateUser(user);
                    } else {
                        theController.deactivateUser(user);

                    }
                }
            }
        } catch (IntegrityViolationException | ConcurrencyException ex) {
            LOGGER.error("Error performing the operation", ex);
            System.out.println("An unexpected error occurred. Please try again or contact the administrator.");
        }
        return true;
    }

    @Override
    public String headline() {
        return "Enable/Disable User";
    }
}

package jobs4u.base.app.backoffice.console.presentation.customer;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.customermanagement.application.RegisterCustomerController;

public class RegisterCustomerUI extends AbstractUI {
    private final RegisterCustomerController controller = new RegisterCustomerController();

    @Override
    protected boolean doShow() {

        final String companyName = Console.readLine("Company Name");
        final String customerCode = Console.readLine("Customer Code");
        final String address = Console.readLine("Address (format: streetName, streetNumber, city, district, zipcode)");
        final String email = Console.readLine("Email");

        try {
            return this.controller.registerNewCustomer(companyName, address, customerCode, email);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            System.out.println(
                    "Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Register Customer";
    }
}

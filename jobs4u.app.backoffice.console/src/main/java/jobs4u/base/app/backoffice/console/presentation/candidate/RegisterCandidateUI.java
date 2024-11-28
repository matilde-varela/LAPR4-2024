package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.candidatemanagement.application.RegisterCandidateController;
import jobs4u.base.customermanagement.application.RegisterCustomerController;

public class RegisterCandidateUI extends AbstractUI {
    private final RegisterCandidateController controller = new RegisterCandidateController();

    @Override
    protected boolean doShow() {

        final String name = Console.readLine("Candidate Name: ");
        final String email = Console.readLine("Candidate Email: ");
        final String extension = Console.readLine("Phone Number Extension (ex: +351): ");
        final String number = Console.readLine("Phone Number (ex: 910000000): ");

        try {
            this.controller.registerCandidate(name, email, extension, number);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            System.out.println(
                    "Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
        }


        return true;
    }

    @Override
    public String headline() {
        return "Register Candidate";
    }
}

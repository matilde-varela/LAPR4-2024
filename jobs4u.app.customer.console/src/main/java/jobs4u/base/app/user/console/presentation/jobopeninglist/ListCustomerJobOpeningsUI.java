package jobs4u.base.app.user.console.presentation.jobopeninglist;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.application.UserSession;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;
import jobs4u.base.jobopeningmanagement.application.ListCustomerJobOpeningsController;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.NoSuchElementException;
import java.util.Optional;


public class ListCustomerJobOpeningsUI extends AbstractListUI<JobOpeningDTO> {

    AuthorizationService authorizationService = AuthzRegistry.authorizationService();

    private ListCustomerJobOpeningsController controller;

    private Username username;

    @Override
    public boolean show() {
        SystemUser user;

        try {
            Optional<UserSession> session = authorizationService.session();
            if (session.isPresent()) {
                user = session.get().authenticatedUser();
            } else {
                throw new NoSuchElementException("No session found");
            }
        } catch (NoSuchElementException e) {
            return false;
        }

        assert user != null;
        username = user.username();
        String password = Console.readLine("Please provide your password again:");

        controller = new ListCustomerJobOpeningsController(username, password);

        System.out.println("Connection successfully established.");
        super.show();
        Pair<Boolean, String> response = controller.closeConnection();
        System.out.println(response.getValue());

        return false;
    }


    @Override
    protected Iterable<JobOpeningDTO> elements() {
        return controller.jobOpeningsDataList(username);
    }

    @Override
    protected Visitor<JobOpeningDTO> elementPrinter() {
        return new JobOpeningCustomerPrinter();
    }

    @Override
    protected String elementName() {
        return "JobOpening";
    }

    @Override
    protected String listHeader() {
        return "The following job openings are associated with the customer:";
    }

    @Override
    protected String emptyMessage() {
        return "No data found.\n";
    }

    @Override
    public String headline() {
        return "Your Job Openings List";
    }
}

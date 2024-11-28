package jobs4u.base.app.candidate.console.presentation.applicationlist;

import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.application.ListApplicationsAndStateController;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public class ListApplicationsAndStateUI extends AbstractUI {

    private final ListApplicationsAndStateController controller = new ListApplicationsAndStateController();

    private Username username;

    @Override
    protected boolean doShow() {

        username = controller.getSessionCredentials();
        String password = Console.readLine("Please provide your password again:");

        Pair<Boolean, String> pair = controller.establishConnection(username, password);
        boolean connectionEstablished = pair.getKey();

        System.out.println();
        if (connectionEstablished) {
            List<Map.Entry<ApplicationDTO, Integer>> applicationDTOList = controller.applicationDTOList(username);
            if(!applicationDTOList.isEmpty()){
                for (Map.Entry<ApplicationDTO, Integer> entry : applicationDTOList) {
                    String sb = "==================================================================\n" +
                            "[Application] " + entry.getKey().getId() + "\n" +
                            "[Files] " + entry.getKey().getApplicationFiles() + "\n" +
                            "[Application Date] " + entry.getKey().getApplicationDate() + "\n" +
                            "[Application Status] " + entry.getKey().getApplicationStatus() + "\n" +
                            "[Candidate Name] " + entry.getKey().getCandidateName() + "\n" +
                            "[Candidate Username] " + entry.getKey().getCandidate() + "\n\n" +
                            "[Applications to the same job opening] " + entry.getValue() + "\n" +
                            "=====================================================================\n";
                    System.out.println(sb);
                }
            } else {
                System.out.println("No applications found!\n");
            }
            controller.closeConnection();
        }

        return false;
    }

    @Override
    public String headline() {
        return "The list of candidate applications and their state";
    }
}

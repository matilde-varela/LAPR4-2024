package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.candidatemanagement.application.ListCandidatesController;
import jobs4u.base.candidatemanagement.application.RegisterCandidateController;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;

import java.util.List;

public class ListCandidatesUI extends AbstractUI {
    private final ListCandidatesController controller = new ListCandidatesController();

    @Override
    protected boolean doShow() {

        try {
            int index =1;
            List<CandidateDTO> candidatesDTOList = this.controller.getCandidatesList();
            for (CandidateDTO candidate: candidatesDTOList){
                System.out.printf("%d. %s | %s | %s%n", index++, candidate.getCandidateName(), candidate.getCandidateEmail(), candidate.getCandidatePhoneNumber());
            }
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            System.out.println(
                    "Unfortunatelly there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
        }


        return true;
    }

    @Override
    public String headline() {
        return "List Candidates";
    }
}

package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.JobOpeningDTOPrinter;
import jobs4u.base.candidatemanagement.application.EnableDisableCandidateController;
import jobs4u.base.candidatemanagement.application.ListCandidatesController;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

import java.util.List;
import java.util.Scanner;

public class EnableDisableCandidateUI extends AbstractUI {
    private final EnableDisableCandidateController controller = new EnableDisableCandidateController();

    @Override
    protected boolean doShow() {

        try {
            SelectWidget<CandidateDTO> selectorCandidate;
            final CandidateDTO candidateDTO;

            Scanner sc = new Scanner(System.in);
            int option;
            System.out.println("1 - Enable Candidate\n2 - Disable Candidate\n0 - Exit");
            option=sc.nextInt();

            if (option == 0) {
                return false;
            }

            switch (option){
                case (1):{
                    Iterable<CandidateDTO> deactivatedCandidates = controller.deactivatedCandidates();
                    if (deactivatedCandidates == null) {
                        System.out.println("There are no candidates to enable.");
                        break;
                    }

                    selectorCandidate = new SelectWidget<>("Please select a Candidate", controller.deactivatedCandidates(), new CandidateDTOPrinter());
                    selectorCandidate.show();
                    candidateDTO = selectorCandidate.selectedElement();

                    if (candidateDTO == null) {
                        System.out.println("No candidate selected.");
                        break;
                    }

                    controller.activateCandidate(candidateDTO);
                    break;
                }
                case (2):{
                    Iterable<CandidateDTO> activeCandidates = controller.activeCandidates();
                    if (activeCandidates == null) {
                        System.out.println("There are no candidates to disable.");
                        break;
                    }

                    selectorCandidate = new SelectWidget<>("Please select a Candidate", controller.activeCandidates(), new CandidateDTOPrinter());
                    selectorCandidate.show();
                    candidateDTO = selectorCandidate.selectedElement();

                    if (candidateDTO == null) {
                        System.out.println("No candidate selected.");
                        break;
                    }

                    controller.deactivateCandidate(candidateDTO);
                    break;
                }
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            System.out.println(
                    "Unfortunately there was an unexpected error in the application. Please try again and if the problem persists, contact your system admnistrator.");
        }


        return true;
    }

    @Override
    public String headline() {
        return "Enable or Disable Candidate";
    }
}

package jobs4u.base.app.backoffice.console.presentation.applications;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.applicationmanagement.application.AnalyseApplicationFilesController;
import jobs4u.base.applicationmanagement.domain.ApplicationFile;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class AnalyseApplicationFilesUI extends AbstractUI {

    AnalyseApplicationFilesController controller = new AnalyseApplicationFilesController();

    @Override
    protected boolean doShow() {
        CandidateDTO candidateDTO = showAndSelectCandidate();

        System.out.println("----------------------- INFO FROM CANDIDATE -----------------------");
        System.out.println("NAME : "+candidateDTO.getCandidateName());
        System.out.println("PHONE NUMBER : "+candidateDTO.getCandidatePhoneNumber());
        System.out.println("EMAIL : "+candidateDTO.getCandidateEmail());
        System.out.println("--------------------------- APPLICATIONS ---------------------------");

        Iterable<ApplicationDTO> list = controller.getAllApplicationsDTOByCandidate(candidateDTO.getCandidatePhoneNumber());
        int i = 0;

        for (ApplicationDTO applicationDTO : list){
            System.out.printf("--------------------------------- %d ---------------------------------\n", i+1);
            System.out.println("ID : "+applicationDTO.getId());
            System.out.println("CANDIDATE : "+applicationDTO.getCandidate());
            System.out.println("STATUS : "+applicationDTO.getApplicationStatus());
            System.out.print("FILES : ");
            for (ApplicationFile applicationFile : applicationDTO.getApplicationFiles()){
                System.out.printf(applicationFile.getApplicationFile()+" | ");
            }
            System.out.println();
            try {
                Map<String, Pair<Integer, List<String>>> top20words = controller.analyzeFilesFromApplication(applicationDTO);
                displayTop20(top20words);
                i++;
            }catch (NoSuchElementException e){
                System.out.println(e.getMessage());
                break;
            }

        }

        return true;
    }

    private void displayTop20(Map<String, Pair<Integer, List<String>>> top20words) {
        int orderNumber = 1;

        System.out.println("--------------------------- TOP 20 WORDS ---------------------------");
        for (Map.Entry<String, Pair<Integer, List<String>>> entry : top20words.entrySet()) {
            System.out.printf("#%d [WORD] '%s'", orderNumber, entry.getKey());
            System.out.printf(" > [WORD COUNT] %d\n  -> [FILES] ", entry.getValue().getKey());
            for (String filename : entry.getValue().getRight()) {
                System.out.printf("%s | ", filename);
            }
            System.out.println("\n--------------------------------------------------------------------");
            ++orderNumber;
        }
    }

    private CandidateDTO showAndSelectCandidate() {
        SelectWidget<CandidateDTO> candidateDTOSelectWidget = new SelectWidget<>("Select a Candidate",
                controller.getAllCandidatesDTO());
        candidateDTOSelectWidget.show();
        return candidateDTOSelectWidget.selectedElement();
    }

    @Override
    public String headline() {
        return "Top 20 Used Words";
    }

}

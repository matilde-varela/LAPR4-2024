package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.application.ListCandidateDataController;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.dto.CandidateDataDTO;

import java.util.List;

public class ListCandidateDataUI extends AbstractUI {

    private final static ListCandidateDataController listCandidateDataController = new ListCandidateDataController();

    @Override
    protected boolean doShow() {

        System.out.println("Select one of the candidates to see information on:");

        List<Candidate> candidateList = listCandidateDataController.listCandidates();

        for (Candidate candidate : candidateList){
            System.out.println("Name: "+candidate.name().toString()+" | Email: "+candidate.email().toString()+" | Phone Number: "+candidate.phoneNumber().extension()+" "+candidate.phoneNumber().number());
        }
        String option = Console.readNonEmptyLine("Candidate's phone number (no extension):", "Mandatory.");

        while(!listCandidateDataController.alreadyExits(option)){
            System.out.println("That phone number does not belong to anyone, please try again.");
            option = Console.readNonEmptyLine("Candidate's phone number (no extension):", "Mandatory.");
        }

        CandidateDataDTO candidateDataDTO = listCandidateDataController.getAllRelevantInfoFromCandidate(option);

        System.out.println("Info from candidate:");
        System.out.println("Candidate's Name : "+candidateDataDTO.getCandidateDTO().getCandidateName());
        System.out.println("Candidate's Phone Number : "+candidateDataDTO.getCandidateDTO().getCandidatePhoneNumber());
        System.out.println("Candidate's Email : "+candidateDataDTO.getCandidateDTO().getCandidateEmail());
        System.out.println();
        System.out.println("Applications Of Candidate:");
        if (candidateDataDTO.getApplicationsDTOList().isEmpty()){
            System.out.println("NO APPLICATIONS FOUND FOR THIS CANDIDATE");
        } else {
            for (ApplicationDTO applicationDTO : candidateDataDTO.getApplicationsDTOList()){
                System.out.println(applicationDTO.toString());
            }
        }

        return false;
    }

    @Override
    public String headline() {
        return "Show data of a candidate";
    }
}

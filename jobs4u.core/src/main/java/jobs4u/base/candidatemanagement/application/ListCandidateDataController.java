package jobs4u.base.candidatemanagement.application;

import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.candidatemanagement.dto.CandidateDataDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListCandidateDataController {

    private final static CandidateManagementService candidateManagementService = new CandidateManagementService();

    private final static ApplicationManagementService applicationManagementService = new ApplicationManagementService();

    public List<Candidate> listCandidates(){
        return candidateManagementService.getCandidatesList();
    }

    public boolean alreadyExits(String phoneNumber){
        return candidateManagementService.alreadyExits(phoneNumber);
    }

    public Optional<Candidate> getCandidateByPhoneNumber(String phoneNumber){
        return candidateManagementService.getCandidateByPhoneNumber(phoneNumber);
    }

    public CandidateDataDTO getAllRelevantInfoFromCandidate(String phoneNumber){

        List<ApplicationDTO> list = new ArrayList<>();

        Candidate candidate = getCandidateByPhoneNumber(phoneNumber).get();

        CandidateDataDTO candidateDataDTO = new CandidateDataDTO(getCandidateByPhoneNumber(phoneNumber).get().toDTO(), applicationManagementService.getAllApplicationsThatHaveCandidate(candidate));

        return candidateDataDTO;
    }

}

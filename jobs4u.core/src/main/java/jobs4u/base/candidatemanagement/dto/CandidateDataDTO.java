package jobs4u.base.candidatemanagement.dto;

import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;

import java.util.List;

public class CandidateDataDTO {

    private final CandidateDTO candidateDTO;
    private final List<ApplicationDTO> applicationsDTOList;

    public CandidateDataDTO(CandidateDTO candidateDTO, List<ApplicationDTO> applicationsDTOList) {
        this.candidateDTO = candidateDTO;
        this.applicationsDTOList = applicationsDTOList;
    }

    public CandidateDTO getCandidateDTO() {
        return candidateDTO;
    }

    public List<ApplicationDTO> getApplicationsDTOList() {
        return applicationsDTOList;
    }
}

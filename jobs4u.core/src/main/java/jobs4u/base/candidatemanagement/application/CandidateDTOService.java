package jobs4u.base.candidatemanagement.application;

import eapli.framework.validations.Preconditions;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

import java.util.ArrayList;
import java.util.List;

public class CandidateDTOService {

    public Iterable<CandidateDTO> convertToDTO(Iterable<Candidate> candidates) {
        Preconditions.noneNull(candidates);

        List<CandidateDTO> dtos = new ArrayList<>();
        for (Candidate j : candidates) {
            dtos.add(j.toDTO());
        }

        return dtos;
    }
}

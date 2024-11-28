package jobs4u.base.jobopeningmanagement.application;

import eapli.framework.validations.Preconditions;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

import java.util.ArrayList;
import java.util.List;

public class JobOpeningDTOService {

    public Iterable<JobOpeningDTO> convertToDTO(Iterable<JobOpening> jobOpenings) {
        Preconditions.noneNull(jobOpenings);

        List<JobOpeningDTO> dtos = new ArrayList<>();
        for (JobOpening j : jobOpenings) {
            dtos.add(j.toDTO());
        }

        return dtos;
    }
}

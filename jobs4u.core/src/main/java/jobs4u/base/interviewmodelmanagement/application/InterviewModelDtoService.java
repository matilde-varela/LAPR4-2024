package jobs4u.base.interviewmodelmanagement.application;

import eapli.framework.validations.Preconditions;
import jobs4u.base.interviewmodelmanagement.dto.InterviewModelDTO;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;

import java.util.ArrayList;
import java.util.List;

public class InterviewModelDtoService {
    public Iterable<InterviewModelDTO> toDto(Iterable<InterviewModel> interviewModels) {
        Preconditions.noneNull(interviewModels);

        List<InterviewModelDTO> interviewModelDTOS = new ArrayList<>();

        for (InterviewModel im : interviewModels) {

            interviewModelDTOS.add(im.toDto());
        }

        return interviewModelDTOS;
    }
}

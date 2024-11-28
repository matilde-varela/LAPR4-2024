package jobs4u.base.jobopeningmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.validations.Preconditions;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.ArrayList;
import java.util.List;

public class JobOpeningListDTOService {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public List<JobOpeningDTO> convertToDTO(List<JobOpening> listToDisplay){
        Preconditions.noneNull(listToDisplay);
        Preconditions.nonEmpty(listToDisplay);

        List<JobOpeningDTO> dtoList = new ArrayList<>();
        for (JobOpening jobOpening : listToDisplay){
            dtoList.add(jobOpening.toDTO());
        }
        return dtoList;
    }
}

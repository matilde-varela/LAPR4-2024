package jobs4u.base.workmodemanagement.application;

import eapli.framework.validations.Preconditions;
import jobs4u.base.workmodemanagement.domain.WorkMode;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;

import java.util.ArrayList;
import java.util.List;

public class WorkModeDtoService {
    public Iterable<WorkModeDTO> convertToDTO(Iterable<WorkMode> workModes) {
        Preconditions.noneNull(workModes);

        List<WorkModeDTO> dtos = new ArrayList<>();
        for (WorkMode j : workModes) {
            dtos.add(j.toDTO());
        }

        return dtos;
    }
}

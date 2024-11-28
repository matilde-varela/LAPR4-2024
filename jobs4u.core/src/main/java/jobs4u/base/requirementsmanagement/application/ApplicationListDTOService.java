package jobs4u.base.requirementsmanagement.application;

import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;

import java.util.ArrayList;
import java.util.List;

public class ApplicationListDTOService {

    public List<ApplicationDTO> convertApplicationsToDTO(Iterable<Application> applicationsList){
        List<ApplicationDTO> applicationDTOList = new ArrayList<>();
        for(Application application : applicationsList){
            applicationDTOList.add(application.toDTO());
        }
        return applicationDTOList;
    }

}

package jobs4u.base.notificationmanagement.dto;

import eapli.framework.validations.Preconditions;
import jobs4u.base.contracttypemanagement.domain.ContractType;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;
import jobs4u.base.notificationmanagement.domain.Notification;


import java.util.ArrayList;
import java.util.List;

public class NotificationDTOService {

    public Iterable<NotificationDTO> convertToDTO(Iterable<Notification> notifications) {
        Preconditions.noneNull(notifications);

        List<NotificationDTO> dtos = new ArrayList<>();
        for (Notification j : notifications) {
            dtos.add(j.toDTO());
        }

        return dtos;
    }

}

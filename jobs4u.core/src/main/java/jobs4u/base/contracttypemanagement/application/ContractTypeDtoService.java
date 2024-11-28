package jobs4u.base.contracttypemanagement.application;

import eapli.framework.validations.Preconditions;
import jobs4u.base.contracttypemanagement.domain.ContractType;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;


import java.util.ArrayList;
import java.util.List;

public class ContractTypeDtoService {

    public Iterable<ContractTypeDTO> convertToDTO(Iterable<ContractType> contractTypes) {
        Preconditions.noneNull(contractTypes);

        List<ContractTypeDTO> dtos = new ArrayList<>();
        for (ContractType j : contractTypes) {
            dtos.add(j.toDTO());
        }

        return dtos;
    }
}

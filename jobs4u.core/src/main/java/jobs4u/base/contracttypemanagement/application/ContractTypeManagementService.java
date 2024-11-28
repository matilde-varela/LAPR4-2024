package jobs4u.base.contracttypemanagement.application;

import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;

public class ContractTypeManagementService {

    private final ContractTypeRepository repo = PersistenceContext.repositories().contractTypes();
    private final ContractTypeDtoService svc = new ContractTypeDtoService();

    public Iterable<ContractTypeDTO> allContractTypes() {
        return svc.convertToDTO(repo.findAll());
    }

}

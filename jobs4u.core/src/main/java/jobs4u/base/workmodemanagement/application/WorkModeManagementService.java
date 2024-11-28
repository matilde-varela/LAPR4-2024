package jobs4u.base.workmodemanagement.application;

import jobs4u.base.contracttypemanagement.application.ContractTypeDtoService;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;
import jobs4u.base.workmodemanagement.repository.WorkModeRepository;

public class WorkModeManagementService {
    private final WorkModeRepository repo = PersistenceContext.repositories().workModes();
    private final WorkModeDtoService svc = new WorkModeDtoService();

    public Iterable<WorkModeDTO> allWorkModes() {
        return svc.convertToDTO(repo.findAll());
    }
}

package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.contracttypemanagement.domain.ContractType;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryContractTypeRepository
        extends InMemoryDomainRepository<ContractType, String>
        implements ContractTypeRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public List<ContractType> contractTypes() {
        List<ContractType> contractTypeList = new ArrayList<>();
        Iterable<ContractType> contractTypes = findAll();
        for (ContractType contractType : contractTypes) {
            contractTypeList.add(contractType);
        }
        return contractTypeList;
    }
}

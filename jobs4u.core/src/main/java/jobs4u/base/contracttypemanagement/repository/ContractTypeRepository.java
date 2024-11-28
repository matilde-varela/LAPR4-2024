package jobs4u.base.contracttypemanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.contracttypemanagement.domain.ContractType;

import java.util.List;

public interface ContractTypeRepository
        extends DomainRepository<String, ContractType> {

    List<ContractType> contractTypes();
}

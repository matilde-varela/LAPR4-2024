package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jobs4u.base.Application;
import jobs4u.base.contracttypemanagement.domain.ContractType;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;

import java.util.ArrayList;
import java.util.List;

public class JpaContractTypeRepository
        extends JpaAutoTxRepository<ContractType, String, String>
        implements ContractTypeRepository {


    public JpaContractTypeRepository(final TransactionalContext autoTx) {
        super(autoTx, "denomination");
    }

    public JpaContractTypeRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "denomination");
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

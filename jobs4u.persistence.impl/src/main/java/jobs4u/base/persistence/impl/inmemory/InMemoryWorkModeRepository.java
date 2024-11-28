package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.workmodemanagement.domain.WorkMode;
import jobs4u.base.workmodemanagement.repository.WorkModeRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryWorkModeRepository extends InMemoryDomainRepository<WorkMode, String>
        implements WorkModeRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public List<WorkMode> workModes() {
        List<WorkMode> workModeList = new ArrayList<>();
        Iterable<WorkMode> workModes = findAll();
        for (WorkMode workMode : workModes) {
            workModeList.add(workMode);
        }
        return workModeList;
    }
}

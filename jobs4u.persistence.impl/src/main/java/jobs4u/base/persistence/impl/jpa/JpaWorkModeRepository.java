package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jobs4u.base.Application;
import jobs4u.base.workmodemanagement.domain.WorkMode;
import jobs4u.base.workmodemanagement.repository.WorkModeRepository;

import java.util.ArrayList;
import java.util.List;

public class JpaWorkModeRepository
        extends JpaAutoTxRepository<WorkMode, String, String>
        implements WorkModeRepository {


    public JpaWorkModeRepository(final TransactionalContext autoTx) {
        super(autoTx, "denomination");
    }

    public JpaWorkModeRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "denomination");
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

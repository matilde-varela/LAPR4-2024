package jobs4u.base.workmodemanagement.repository;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.workmodemanagement.domain.WorkMode;

import java.util.List;

public interface WorkModeRepository
        extends DomainRepository<String, WorkMode> {

    List<WorkMode> workModes();
}

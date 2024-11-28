package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.requirementsmanagement.domain.RequirementName;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;
import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRequirementSpecificationRepository
        extends InMemoryDomainRepository<RequirementSpecification, RequirementName>
        implements RequirementSpecificationRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public List<RequirementSpecification> requirementsSpecifications() {
        List<RequirementSpecification> requirementsList = new ArrayList<>();
        Iterable<RequirementSpecification> requirementsSpecifications = findAll();
        for (RequirementSpecification req : requirementsSpecifications) {
            requirementsList.add(req);
        }
        return requirementsList;
    }

    @Override
    public Optional<RequirementSpecification> requirementSpecificationByRequirementName(String requirement) {
        return matchOne(e -> e.identity().name().equals(requirement));
    }
}

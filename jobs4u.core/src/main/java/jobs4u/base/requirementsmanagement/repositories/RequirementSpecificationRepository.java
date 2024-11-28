package jobs4u.base.requirementsmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.requirementsmanagement.domain.RequirementName;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;

import java.util.Optional;

public interface RequirementSpecificationRepository
        extends DomainRepository<RequirementName, RequirementSpecification> {

    Iterable<RequirementSpecification> requirementsSpecifications();

    Optional<RequirementSpecification> requirementSpecificationByRequirementName(String requirement);
}

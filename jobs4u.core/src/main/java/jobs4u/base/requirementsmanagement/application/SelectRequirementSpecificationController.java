package jobs4u.base.requirementsmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.requirementsmanagement.dto.RequirementSpecificationDTO;
import jobs4u.base.requirementsmanagement.domain.RequirementName;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;

import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.Optional;

public class SelectRequirementSpecificationController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final JobOpeningManagementService jobOpeningSvc = new JobOpeningManagementService();
    private final RequirementSpecificationManagementService reqSpeSvc = new RequirementSpecificationManagementService();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final RequirementSpecificationRepository requirementSpecificationRepository = PersistenceContext.repositories().requirementSpecifications();


    public Iterable<JobOpeningDTO> activeJobOpenings() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);

        return jobOpeningSvc.activeJobOpenings();
    }

    public Iterable<RequirementSpecificationDTO> availableRequirementSpecification() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);

        return reqSpeSvc.allRequirementSpecifications();
    }

    public boolean updateRequirementSpecification(JobOpeningDTO jobOpeningDTO, RequirementSpecificationDTO requirementSpecificationDTO) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);
        Optional<JobOpening> j = jobOpeningRepository.ofIdentity(new JobReference(jobOpeningDTO.getJobReference()));
        if (j.isPresent()) {
            Optional<RequirementSpecification> r = requirementSpecificationRepository.ofIdentity(new RequirementName(requirementSpecificationDTO.filename()));
            if (r.isPresent()) {
                j.get().changeRequirementSpecification(r.get());
                jobOpeningRepository.save(j.get());
                return true;
            }
        }
        return false;
    }
}

package jobs4u.base.jobopeningmanagement.application;

import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.customermanagement.application.CustomerManagementService;
import jobs4u.base.customermanagement.dto.CustomerDTO;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.contracttypemanagement.domain.ContractType;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.workmodemanagement.domain.WorkMode;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;
import jobs4u.base.workmodemanagement.repository.WorkModeRepository;
import jobs4u.base.requirementsmanagement.dto.RequirementSpecificationDTO;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;

import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.*;

@UseCaseController
public class RegisterJobOpeningController {

    private final AuthorizationService authz;
    private final CustomerManagementService customerManagementService;
    private final JobOpeningManagementService jobOpeningManagementService;
    private final ContractTypeRepository contractTypeRepository;
    private final WorkModeRepository workModeRepository;
    private final RequirementSpecificationRepository requirementSpecificationRepository;

    public RegisterJobOpeningController() {
        this.authz = AuthzRegistry.authorizationService();
        this.customerManagementService = new CustomerManagementService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.contractTypeRepository = PersistenceContext.repositories().contractTypes();
        this.workModeRepository = PersistenceContext.repositories().workModes();
        this.requirementSpecificationRepository = PersistenceContext.repositories().requirementSpecifications();
    }

    public List<CustomerDTO> getCustomersList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        Optional<SystemUser> user = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);
        if (user.isPresent()) {
            return customerManagementService.getAssignedCustomerCodesList(user.get().identity());
        }
        throw new NoSuchElementException("It was not possible to retrieve the user's data.");
    }

    public List<ContractTypeDTO> getContractTypesList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        List<ContractTypeDTO> contractTypes = new ArrayList<>();
        for (ContractType type : contractTypeRepository.contractTypes()) {
            contractTypes.add(type.toDTO());
        }
        return contractTypes;
    }

    public List<WorkModeDTO> getWorkModesList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        List<WorkModeDTO> workModes = new ArrayList<>();
        for (WorkMode mode : workModeRepository.workModes()) {
            workModes.add(mode.toDTO());
        }
        return workModes;
    }

    public List<RequirementSpecificationDTO> getRequirementsSpecificationsList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        List<RequirementSpecificationDTO> requirementSpecifications = new ArrayList<>();
        for (RequirementSpecification requirement : requirementSpecificationRepository.requirementsSpecifications()) {
            requirementSpecifications.add(requirement.toDTO());
        }
        return requirementSpecifications;
    }


    public Optional<JobOpening> registerJobOpening(String function, ContractTypeDTO contractTypeDenomination,
                                                   WorkModeDTO workModeDenomination, String streetName, String city,
                                                   String district, String state, String zipcode, int numVacancies,
                                                   String description, RequirementSpecificationDTO requirementsFileName,
                                                   CustomerDTO companyInfo) {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        Optional<RequirementSpecification> requirementSpecification = requirementSpecificationRepository.requirementSpecificationByRequirementName(requirementsFileName.filename());

        if (requirementSpecification.isEmpty()) {
            throw new NoSuchElementException("No requirement specifications where found.");
        }

        return Optional.of(jobOpeningManagementService.registerJobOpening(function, contractTypeDenomination, workModeDenomination,
                streetName, city, district, state, zipcode, numVacancies, description, requirementSpecification.get(), companyInfo));
    }

}

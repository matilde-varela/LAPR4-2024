package jobs4u.base.requirementsmanagement.application;

import com.ibm.icu.impl.Pair;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.pluginmanagement.domain.ConfigFileName;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;
import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plugins.FileManagement;
import plugins.RequirementsSpecificationPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class VerifyRequirementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);

    private final JobOpeningManagementService svc = new JobOpeningManagementService();
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ApplicationRepository repo = PersistenceContext.repositories().applications();
    private final RequirementSpecificationRepository repoRS = PersistenceContext.repositories().requirementSpecifications();

    public Iterable<JobOpeningDTO> getJobOpeningList() {
        Optional<SystemUser> customerManager = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);
        return customerManager.map(systemUser -> svc.jobOpeningsInScreeingListOfCustomerManager(systemUser.username())).orElse(null);
    }

    public Boolean verifyAvailableRequirements(JobOpeningDTO jobOpeningDTO) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        return verifyRequirementsOfApplications(jobOpeningDTO.getJobReference(), jobOpeningDTO.getRequirementName());
    }

    private Boolean verifyRequirementsOfApplications(String jobReference, String requirement) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();

        Iterable<Application> applications = repo.applicationsForJobOpeningWithRequirements(jobReference);
        if (!applications.iterator().hasNext()) {
            throw new IllegalArgumentException("No applications have associated requirement specification answers.");
        }
        Optional<RequirementSpecification> rs = repoRS.requirementSpecificationByRequirementName(requirement);

        try {
            if (rs.isPresent()) {
                RequirementSpecification requirementSpec = rs.get();
                FileManagement dataImporterInstance = (FileManagement) loader.loadClass(requirementSpec.dataImporter()).getDeclaredConstructor().newInstance();
                RequirementsSpecificationPlugin reqSpecEvaluator = (RequirementsSpecificationPlugin) loader.loadClass(requirementSpec.className()).getDeclaredConstructor().newInstance();
                dataImporterInstance.importData(requirementSpec.configurationFile().toString());
                for (Application a : applications) {
                    try {
                        Pair<Boolean, String> result = reqSpecEvaluator.evaluateRequirementSpecificationFile(a.requirementAnswerFilePath());
                        a.updateRequirementResult(result);
                        repo.save(a);
                    } catch (Exception e) {
                        LOGGER.error("Couldn't evaluate application.");
                        return false;
                    }
                }
            } else {
                LOGGER.error("Requirement specification not found for: {}", requirement);
                return false;
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            LOGGER.error("Unable to access plugin.");
            return false;
        }
        return true;
    }


}

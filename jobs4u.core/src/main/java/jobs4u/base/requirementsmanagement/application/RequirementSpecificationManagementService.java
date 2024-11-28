package jobs4u.base.requirementsmanagement.application;

import jobs4u.base.infrastructure.persistence.PersistenceContext;

import jobs4u.base.requirementsmanagement.domain.RequirementName;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;
import jobs4u.base.requirementsmanagement.dto.RequirementSpecificationDTO;
import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plugins.FileManagement;
import plugins.RequirementsSpecificationPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;


public class RequirementSpecificationManagementService {

    private static final RequirementSpecificationRepository requirementSpecificationRepository = PersistenceContext
            .repositories().requirementSpecifications();

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);

    private final RequirementSpecificationDTOService dtoSvc = new RequirementSpecificationDTOService();

    public Iterable<RequirementSpecificationDTO> allRequirementSpecifications() {
        return dtoSvc.convertToDTO(requirementSpecificationRepository.findAll());
    }
    public static void registerRequirementPlugin(String nameOfRequirementPlugin, String descriptionOfRequirementPlugin, String fullClassName,String configFile, String dataImporter){
        RequirementSpecification requirementSpecification = new RequirementSpecification(nameOfRequirementPlugin, descriptionOfRequirementPlugin, fullClassName, configFile,dataImporter);
        requirementSpecificationRepository.save(requirementSpecification);
    }

    public boolean checkIfRequirementSpecificationAlreadyExists(String identifier){
        return requirementSpecificationRepository.containsOfIdentity(new RequirementName(identifier));
    }

    public boolean checkAnswersFileIsValid(String requirementName, String filepath) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        try {
                Optional<RequirementSpecification> rs = requirementSpecificationRepository.requirementSpecificationByRequirementName(requirementName);
                if (rs.isPresent()) {
                    RequirementSpecification requirementSpec = rs.get();
                    FileManagement dataImporterInstance = (FileManagement) loader.loadClass(requirementSpec.dataImporter()).getDeclaredConstructor().newInstance();
                    dataImporterInstance.importData(requirementSpec.configurationFile().toString());
                    return dataImporterInstance.checkFileFormat(filepath);
                }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            LOGGER.error("Unable to access plugin.");
            return false;
        }
        return false;
    }
}

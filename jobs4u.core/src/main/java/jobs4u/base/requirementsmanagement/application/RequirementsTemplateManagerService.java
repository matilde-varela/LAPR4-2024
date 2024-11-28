package jobs4u.base.requirementsmanagement.application;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;
import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;
import plugins.FileManagement;
import plugins.InterviewModelPlugin;
import plugins.RequirementsSpecificationPlugin;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RequirementsTemplateManagerService {

    private final RequirementSpecificationRepository requirementSpecificationRepository = PersistenceContext
            .repositories().requirementSpecifications();

    public RequirementSpecification getRequirementFromJobOpeningDTO(JobOpeningDTO jobOpeningDTO) {
        Optional<RequirementSpecification> optional = requirementSpecificationRepository.requirementSpecificationByRequirementName(jobOpeningDTO.getRequirementName());
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NoSuchElementException("There are no requirement specifications with the name provided.");
    }

    public boolean generateNewTemplate(RequirementSpecification requirementSpecification, String outputDirectory) {
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            FileManagement dataImporterInstance = (FileManagement) loader.loadClass(requirementSpecification.dataImporter()).getDeclaredConstructor().newInstance();
            RequirementsSpecificationPlugin interviewModelEvaluator = (RequirementsSpecificationPlugin) loader.loadClass(requirementSpecification.className()).getDeclaredConstructor().newInstance();
            dataImporterInstance.importData(requirementSpecification.configurationFile().toString());
            interviewModelEvaluator.generateTextFile((outputDirectory + "\\" + requirementSpecification.requirementName().name() + ".txt"));
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            return false;
        }
    }
}


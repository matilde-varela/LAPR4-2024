package jobs4u.base.interviewmodelmanagement.application;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import plugins.FileManagement;
import plugins.InterviewModelPlugin;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class InterviewTemplateManagerService {

    private final InterviewModelRepository interviewModelRepository = PersistenceContext.repositories().interviewModels();

    public InterviewModel getInterviewFromJobOpening(JobOpeningDTO jobOpeningDTO) {
        Optional<InterviewModel> optional = interviewModelRepository.getFileByName(jobOpeningDTO.getInterviewModelName());
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NoSuchElementException("There are no interview models with the name provided.");
    }

    public boolean generateNewTemplate(InterviewModel interviewModel, String outputDirectory) {
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            FileManagement dataImporterInstance = (FileManagement) loader.loadClass(interviewModel.dataImporter()).getDeclaredConstructor().newInstance();
            InterviewModelPlugin interviewModelEvaluator = (InterviewModelPlugin) loader.loadClass(interviewModel.className()).getDeclaredConstructor().newInstance();
            dataImporterInstance.importData(interviewModel.configurationFile().toString());
            interviewModelEvaluator.generateTextFile((outputDirectory + "\\" + interviewModel.nameString() + ".txt"));
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            return false;
        }
    }
}

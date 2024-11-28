package jobs4u.base.interviewmodelmanagement.application;

import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModelName;
import jobs4u.base.interviewmodelmanagement.dto.InterviewModelDTO;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plugins.FileManagement;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class InterviewModelManagementService {

    private static final InterviewModelRepository repository = PersistenceContext.repositories().interviewModels();

    private final InterviewModelDtoService dtoService = new InterviewModelDtoService();

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);

    public Iterable<InterviewModelDTO> allInterviewModels() {
         return dtoService.toDto(repository.findAll());
    }

    public static void registerInterviewPlugin(String nameOfInterviewPlugin, String descriptionOfInterviewPlugin, String fullClassName, String configFile,String dataImporter){
        InterviewModel interviewModel = new InterviewModel(nameOfInterviewPlugin, descriptionOfInterviewPlugin, fullClassName,configFile,dataImporter);
        repository.save(interviewModel);
    }

    public boolean checkIfInterviewModelAlreadyExists(String identifier){
        return repository.containsOfIdentity(new InterviewModelName(identifier));
    }

    public boolean checkAnswersFileIsValid(String interviewName, String filepath) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        try {
            Optional<InterviewModel> im = repository.getFileByName(interviewName);
            if (im.isPresent()) {
                InterviewModel interviewSpec = im.get();
                FileManagement dataImporterInstance = (FileManagement) loader.loadClass(interviewSpec.dataImporter()).getDeclaredConstructor().newInstance();
                dataImporterInstance.importData(interviewSpec.configurationFile().toString());
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

package jobs4u.base.jobopeningmanagement.application;

import com.ibm.icu.impl.Pair;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plugins.FileManagement;
import plugins.InterviewModelPlugin;
import plugins.RequirementsSpecificationPlugin;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class EvaluateInterviewsController {
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContext.class);

    private final JobOpeningManagementService jobOpeningManagementService= new JobOpeningManagementService();
    private final ApplicationRepository applicationRepository = PersistenceContext.repositories().applications();
    private final InterviewModelRepository interviewModelRepository =PersistenceContext.repositories().interviewModels();

    public Iterable<JobOpeningDTO> getJobOpeningsList() {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);
        return jobOpeningManagementService.activeJobOpenings();
    }

    public boolean interviewsEvaluation(JobOpeningDTO jobOpening) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);

        ClassLoader loader = ClassLoader.getSystemClassLoader();

        Iterable<Application> applications = applicationRepository.applicationsForJobOpeningWithInterviewAnswers(jobOpening.getJobReference());
        long size = StreamSupport.stream(applications.spliterator(),false).count();
        if (!applications.iterator().hasNext()) {
            throw new IllegalArgumentException("No applications have associated interview answers.");
        }

        Optional<InterviewModel> im = interviewModelRepository.interviewModelByInterviewName(jobOpening.getInterviewModelName());

        try {
            if (im.isPresent()) {
                InterviewModel interviewModel = im.get();

                FileManagement dataImporterInstance = (FileManagement) loader.loadClass(interviewModel.dataImporter()).getDeclaredConstructor().newInstance();

                InterviewModelPlugin interviewModelEvaluator = (InterviewModelPlugin) loader.loadClass(interviewModel.className()).getDeclaredConstructor().newInstance();

                dataImporterInstance.importData(interviewModel.configurationFile().toString());

                for (Application application : applications) {
                    try {

                        Pair<Integer, String> result = interviewModelEvaluator.evaluateInterviewModelFile(application.interviewAnswerFilePath());
                        application.updateInterviewGrade(result);
                        applicationRepository.save(application);

                    } catch (Exception e) {
                        LOGGER.error("Couldn't evaluate application.");
                        return false;
                    }
                }
            } else {
                LOGGER.error("Requirement specification not found for: {}", jobOpening.getInterviewModelName());
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

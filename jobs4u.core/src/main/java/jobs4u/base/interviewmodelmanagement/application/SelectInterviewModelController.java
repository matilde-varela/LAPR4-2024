package jobs4u.base.interviewmodelmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.interviewmodelmanagement.dto.InterviewModelDTO;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModelName;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;

import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.Optional;

public class SelectInterviewModelController {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final JobOpeningManagementService jobOpeningManagementService = new JobOpeningManagementService();
    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();

    private final InterviewModelManagementService interviewModelManagementService = new InterviewModelManagementService();
    private final InterviewModelRepository interviewModelRepository = PersistenceContext.repositories().interviewModels();


    public Iterable<JobOpeningDTO> activeJobOpenings() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);

        return jobOpeningManagementService.activeJobOpenings();
    }

    public Iterable<InterviewModelDTO> availableInterviewModels() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);

        return interviewModelManagementService.allInterviewModels();
    }

    public boolean updateJobOpening(JobOpeningDTO jobOpeningDTO, InterviewModelDTO interviewModelDTO) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);
        Optional<JobOpening> jo = jobOpeningRepository.ofIdentity(new JobReference(jobOpeningDTO.getJobReference()));

        if (jo.isPresent()) {

            Optional<InterviewModel> im = interviewModelRepository.ofIdentity(new InterviewModelName(interviewModelDTO.name()));

            if (im.isPresent()) {
                jo.get().changeInterviewModel(im.get());
                jobOpeningRepository.save(jo.get());
                return true;
            }
        }
        return false;
    }
}

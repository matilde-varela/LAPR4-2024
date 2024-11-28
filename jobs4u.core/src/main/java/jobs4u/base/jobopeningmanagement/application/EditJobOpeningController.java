package jobs4u.base.jobopeningmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.contracttypemanagement.application.ContractTypeManagementService;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.interviewmodelmanagement.application.InterviewModelManagementService;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModelName;
import jobs4u.base.interviewmodelmanagement.dto.InterviewModelDTO;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;
import jobs4u.base.jobopeningmanagement.domain.EditableInformation;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.requirementsmanagement.domain.RequirementName;
import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;
import jobs4u.base.workmodemanagement.application.WorkModeManagementService;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;
import jobs4u.base.requirementsmanagement.application.RequirementSpecificationManagementService;
import jobs4u.base.requirementsmanagement.dto.RequirementSpecificationDTO;
import jobs4u.base.workmodemanagement.repository.WorkModeRepository;

import java.util.*;
import java.util.function.Supplier;

import static jobs4u.base.jobopeningmanagement.domain.EditableInformation.*;

public class EditJobOpeningController {

    private final AuthorizationService auth = AuthzRegistry.authorizationService();
    private final RequirementSpecificationManagementService reqSvc = new RequirementSpecificationManagementService();
    private final InterviewModelManagementService imSvc = new InterviewModelManagementService();
    private final ContractTypeManagementService ctSvc = new ContractTypeManagementService();
    private final WorkModeManagementService wmSvc = new WorkModeManagementService();
    private final JobOpeningManagementService joSvc = new JobOpeningManagementService();
    private final JobOpeningRepository jobOpeningRepo = PersistenceContext.repositories().jobOpenings();
    private final ContractTypeRepository contractTypeRepository = PersistenceContext.repositories().contractTypes();
    private final WorkModeRepository workModeRepository = PersistenceContext.repositories().workModes();
    private final RequirementSpecificationRepository requirementSpecificationRepository = PersistenceContext.repositories().requirementSpecifications();
    private final InterviewModelRepository interviewModelRepository = PersistenceContext.repositories().interviewModels();

    public Iterable<JobOpeningDTO> getJobOpeningList() {
        return auth.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER)
                .map(user -> joSvc.jobOpeningsOfCustomerManager(user.identity()))
                .orElseThrow(() -> new IllegalArgumentException("User doesn't have the necessary permissions to view job openings"));
    }

    public Iterable<EditableInformation> editableJobOpeningInformation(JobOpeningDTO jobOpeningDTO) {
        auth.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);

        return jobOpeningRepo.ofIdentity(new JobReference(jobOpeningDTO.getJobReference()))
                .map(JobOpening::editableJobOpeningInformation)
                .orElseThrow(() -> new IllegalArgumentException("Job opening not found for reference: "
                        + jobOpeningDTO.getJobReference()));
    }

    public Iterable<RequirementSpecificationDTO> requirementSpecifications() {
        return reqSvc.allRequirementSpecifications();
    }

    public Iterable<InterviewModelDTO> interviewModels() {
        return imSvc.allInterviewModels();
    }

    public Iterable<ContractTypeDTO> contractTypes() {
        return ctSvc.allContractTypes();
    }

    public Iterable<WorkModeDTO> workModes() {
        return wmSvc.allWorkModes();
    }


    public boolean changeJobOpeningInformation(JobOpeningDTO jobOpeningDTO, List<EditableInformation> selectedInformation, List<String> newInformation) {
        auth.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);

        Optional<JobOpening> jobOpeningOptional = jobOpeningRepo.ofIdentity(new JobReference(jobOpeningDTO.getJobReference()));

        if (jobOpeningOptional.isPresent()) {
            JobOpening jobOpening = jobOpeningOptional.get();
            jobOpeningRepo.save(editInformation(jobOpening, selectedInformation, newInformation));
            return true;
        }
        return false;
    }

    private JobOpening editInformation(JobOpening jobOpening, List<EditableInformation> selectedInformation, List<String> newInformation) {
        for (int i = 0; i < selectedInformation.size(); i++) {
            EditableInformation info = selectedInformation.get(i);
            String newInfo = newInformation.get(i);

            if (info.equals(CONTRACT_TYPE)) {
                contractTypeRepository.ofIdentity(newInfo)
                        .ifPresentOrElse(jobOpening::changeContractType, () -> {
                            throw new IllegalArgumentException("Specified contract type is not supported");
                        });
            } else if (info.equals(WORK_MODE)) {
                workModeRepository.ofIdentity(newInfo)
                        .ifPresentOrElse(jobOpening::changeWorkMode, () -> {
                            throw new IllegalArgumentException("Specified work mode is not supported");
                        });
            } else if (info.equals(REQ_SPECI)) {
                requirementSpecificationRepository.ofIdentity(new RequirementName(newInfo))
                        .ifPresentOrElse(jobOpening::changeRequirementSpecification, () -> {
                            throw new IllegalArgumentException("Specified requirement is not supported");
                        });
            } else if (info.equals(INT_MODEL)) {
                interviewModelRepository.ofIdentity(new InterviewModelName(newInfo))
                        .ifPresentOrElse(jobOpening::changeInterviewModel, () -> {
                            throw new IllegalArgumentException("Specified interview model is not supported");
                        });
            } else {
                jobOpening.changeInformation(info, newInfo);
            }
        }
        return jobOpening;
    }

    public Iterable<?> necessaryInformation(EditableInformation ed) {
        Map<EditableInformation, Supplier<Iterable<?>>> infoMap = Map.of(
                REQ_SPECI, this::requirementSpecifications,
                INT_MODEL, this::interviewModels,
                WORK_MODE, this::workModes,
                CONTRACT_TYPE, this::contractTypes
        );

        return infoMap.containsKey(ed) ? infoMap.get(ed).get() : null;
    }
}

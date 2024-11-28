package jobs4u.base.recruitmentprocessmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.pubsub.EventPublisher;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;
import jobs4u.base.customermanagement.application.CustomerManagementService;
import jobs4u.base.jobopeningmanagement.application.JobOpeningListDTOService;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.domain.events.JobOpeningPhaseChangedEvent;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.recruitmentprocessmanagement.dto.RecruitmentProcessDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;
import org.hibernate.mapping.Collection;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ChangePhaseStatesController {

    private final AuthorizationService authz;
    private final CustomerManagementService customerManagementService;
    private final EventPublisher dispatcher;
    private final JobOpeningManagementService jobOpeningManagementService;
    private final RecruitmentProcessManagementService recruitmentProcessManagementService;
    private final JobOpeningListDTOService jobOpeningListDTOService;

    public ChangePhaseStatesController() {
        this.dispatcher = InProcessPubSub.publisher();
        this.authz = AuthzRegistry.authorizationService();
        this.customerManagementService = new CustomerManagementService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.jobOpeningListDTOService = new JobOpeningListDTOService();
        this.recruitmentProcessManagementService = new RecruitmentProcessManagementService();
    }
    public Iterable<JobOpeningDTO> getJobOpeningList() throws ClassNotFoundException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        Optional<SystemUser> user = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);
        if (user.isPresent()) {
            return jobOpeningManagementService.plannedJobOpeningsOfCustomerManager(user.get().username());
        }else {
            return Collections.emptyList();
        }

    }

    public RecruitmentProcessDTO getRecruitmentProcessDTOWithJobReference(String jobReference) throws ClassNotFoundException {
        try {
            return recruitmentProcessManagementService.getRecruitmentProcessWithJobReference(new JobReference(jobReference));
        }catch(Exception e){
            throw new ClassNotFoundException("It was not possible to retrieve the job opening's data.");
        }
    }

    public boolean goBackAPhase(String jobReference) {
        String codes[] = jobReference.split("-");

        try {
            recruitmentProcessManagementService.goBackAPhase(jobReference);
            dispatcher.publish(new JobOpeningPhaseChangedEvent(customerManagementService.getCustomerByCustomerCode(codes[0]).get().customerUser().email(), new JobReference(jobReference), recruitmentProcessManagementService.getRecruitmentProcessWithJobReference(new JobReference(jobReference)).getRecruitmentProcessStatus()));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean goNextPhase(String jobReference) {
        String codes[] = jobReference.split("-");

        try {
            recruitmentProcessManagementService.goNextPhase(jobReference);
            dispatcher.publish(new JobOpeningPhaseChangedEvent(customerManagementService.getCustomerByCustomerCode(codes[0]).get().customerUser().email(), new JobReference(jobReference), recruitmentProcessManagementService.getRecruitmentProcessWithJobReference(new JobReference(jobReference)).getRecruitmentProcessStatus()));
        }catch (Exception e){
            return false;
        }
        return true;
    }
}

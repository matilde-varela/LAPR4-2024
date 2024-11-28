package jobs4u.base.applicationmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.util.List;
import java.util.Optional;

public class DisplayAllApplicationDataController {

    private final AuthorizationService authz;

    private final JobOpeningManagementService jobOpeningManagementService;


    public DisplayAllApplicationDataController(){
        this.authz = AuthzRegistry.authorizationService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
    }

    public List <ApplicationDTO> getApplicationsList(){
        Iterable<JobOpeningDTO> jobOpeningDTOList = getJobOpeningList();

        return jobOpeningManagementService.getApplicationsList(jobOpeningDTOList);
    }


    public Iterable<JobOpeningDTO> getJobOpeningList() {
        Optional<SystemUser> customerManager = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);
        return customerManager.map(systemUser -> jobOpeningManagementService.jobOpeningsListOfCustomerManager(systemUser.username())).orElse(null);

    }
}

package jobs4u.base.jobopeningmanagement.application;


import eapli.framework.application.UseCaseController;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.time.domain.model.DateInterval;
import jobs4u.base.criteriamanagement.domain.Criteria;
import jobs4u.base.criteriamanagement.dto.CriteriaDTO;
import jobs4u.base.criteriamanagement.repository.CriteriaRepository;
import jobs4u.base.customermanagement.application.CustomerManagementService;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.dto.CustomerDTO;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;


import java.util.*;


@UseCaseController
public class ListJobOpeningsController {

    private final AuthorizationService authz;

    private final CustomerManagementService customerManagementService;
    private final JobOpeningManagementService jobOpeningManagementService;

    private final JobOpeningListDTOService jobOpeningListDTOService;

    private final CriteriaRepository criteriaRepository;

    public ListJobOpeningsController() {
        this.authz = AuthzRegistry.authorizationService();
        this.customerManagementService = new CustomerManagementService();
        this.jobOpeningManagementService = new JobOpeningManagementService();
        this.jobOpeningListDTOService = new JobOpeningListDTOService();
        this.criteriaRepository = PersistenceContext.repositories().criteria();
    }

    public List<JobOpeningDTO> filterJobOpeningList(Object object) {
        List<JobOpening> jobOpeningList = new ArrayList<>();
        switch (object.getClass().getSimpleName()) {
            case "CustomerDTO":
                Optional<Customer> customer = customerManagementService.getCustomerByDTO((CustomerDTO) object);
                if (customer.isPresent()){
                    jobOpeningList = filterJobOpeningListByCompanyName((CustomerDTO) object);
                }
                break;
            case "DateInterval":
                    jobOpeningList = filterJobOpeningListByDateInterval((DateInterval) object, virgemJobOpeningList());
                break;
            case "String":
                if (object.equals("STARTED")) {
                    jobOpeningList = filterJobOpeningListBySTARTEDStatus(virgemJobOpeningList());
                } else {
                    jobOpeningList = filterJobOpeningListByCustomerCode((String) object);
                }
                break;
            default:
                break;
        }
        return jobOpeningListDTOService.convertToDTO(jobOpeningList);
    }

    private List<JobOpening> filterJobOpeningListByDateInterval(DateInterval interval, List<JobOpening> jobOpenings) {
        return jobOpeningManagementService.filterJobOpeningListByDateInterval(interval, jobOpenings);
    }

    private List<JobOpening> filterJobOpeningListBySTARTEDStatus(List<JobOpening> virgemJobOpeningList) {
        return jobOpeningManagementService.filterJobOpeningListBySTARTEDStatus("STARTED", virgemJobOpeningList);
    }

    private List<JobOpening> filterJobOpeningListByCustomerCode(String customerCode) {
        return jobOpeningManagementService.filterJobOpeningListByCustomerCode(customerCode);
    }


    private List<JobOpening> filterJobOpeningListByCompanyName(CustomerDTO object) {
        return jobOpeningManagementService.filterJobOpeningListByCompanyName(object);
    }


    public Iterable<JobOpeningDTO> backofficeJobOpenings() {
        return jobOpeningListDTOService.convertToDTO(virgemJobOpeningList());
    }

    private List<JobOpening> virgemJobOpeningList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        Optional<SystemUser> user = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);
        if (user.isPresent()) {
            List<CustomerDTO> customerDTOList = customerManagementService.getAssignedCustomerCodesList(user.get().identity());
            return jobOpeningManagementService.getJobOpeningsFromCustomerCodes(customerDTOList);
        } else {
            throw new NoSuchElementException("It was not possible to retrieve the job openings.");
        }
    }

    public List<CriteriaDTO> getCriteria() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        List<CriteriaDTO> criteriaDTOS = new ArrayList<>();
        for (Criteria criteria : criteriaRepository.jobOpeningCriteria()) {
            criteriaDTOS.add(criteria.toDTO());
        }
        return criteriaDTOS;
    }

    public List<CustomerDTO> getCustomersList() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER);
        Optional<SystemUser> user = authz.loggedinUserWithPermissions(BaseRoles.CUSTOMER_MANAGER);
        if (user.isPresent()) {
            return customerManagementService.getAssignedCustomerCodesList(user.get().identity());
        }
        throw new NoSuchElementException("It was not possible to retrieve the user's data.");
    }
}

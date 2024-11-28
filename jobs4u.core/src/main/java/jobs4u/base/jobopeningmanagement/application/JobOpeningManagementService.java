package jobs4u.base.jobopeningmanagement.application;

import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.time.domain.model.DateInterval;
import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.customermanagement.application.CustomerManagementService;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.dto.CustomerDTO;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatusEnum;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.recruitmentprocessmanagement.application.RecruitmentProcessManagementService;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcessStatusEnum;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;

import java.util.*;

public class JobOpeningManagementService {

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext
            .repositories().jobOpenings();

    private final JobOpeningDTOService dtoSvc = new JobOpeningDTOService();

    private final RecruitmentProcessManagementService recruitmentProcessManagementService = new RecruitmentProcessManagementService();

    private final CustomerManagementService customerManagementService = new CustomerManagementService();

    private final ApplicationManagementService applicationManagementService = new ApplicationManagementService();

    public Iterable<JobOpening> getUNFINISHEDJobOpenings() {
        return jobOpeningRepository.getUNFINISHEDJobOpeningList();
    }

    public JobOpening registerJobOpening(String function, ContractTypeDTO contractTypeDenomination,
                                         WorkModeDTO workModeDenomination, String streetName, String city,
                                         String district, String state, String zipcode, int numVacancies,
                                         String description, RequirementSpecification requirementsFile,
                                         CustomerDTO companyInfo) {

        JobReference lastReference = jobOpeningRepository.lastJobReference(companyInfo.customerCode());

        JobOpening jobOpening = new JobOpening(function, contractTypeDenomination, workModeDenomination, streetName, city,
                district, state, zipcode, numVacancies, description, requirementsFile, lastReference);

        jobOpeningRepository.save(jobOpening);
        return jobOpening;
    }

    public Iterable<JobOpeningDTO> activeJobOpenings() {
        return dtoSvc.convertToDTO(jobOpeningRepository.findAllJobOpeningsNotStarted());
    }

    public boolean checkJobOpeningByJobRef(String customerCode, int sequentialCode) {
        return jobOpeningRepository.containsOfIdentity(new JobReference(customerCode, sequentialCode));
    }

    public Optional<JobOpening> getJobOpeningByJobRef(String customerCode, int sequentialCode) {
        return jobOpeningRepository.ofIdentity(new JobReference(customerCode, sequentialCode));
    }

    public Optional<JobOpening> getJobOpeningByJobRef(String jobReference) {
        return jobOpeningRepository.ofIdentity(new JobReference(jobReference));
    }

    public List<JobOpening> getJobOpeningsFromCustomerCodes(List<CustomerDTO> customerDTOList) {
        Set<CustomerCode> customerCodes = new HashSet<>();
        for (CustomerDTO customerDTO : customerDTOList) {
            customerCodes.add(new CustomerCode(customerDTO.customerCode()));
        }
        return jobOpeningRepository.getJobOpeningListMatchingCustomerCodesList(customerCodes);
    }

    public List<JobOpening> filterJobOpeningListByCompanyName(CustomerDTO dto) {
        Optional<Customer> customer = customerManagementService.getCustomerByDTO(dto);
        if (customer.isPresent()) {
            return jobOpeningRepository.getJobOpeningListMatchingCustomer(customer.get());
        }
        throw new NoSuchElementException("Failure - filterJobOpeningListByCompanyName");
    }

    public List<JobOpening> filterJobOpeningListByCustomerCode(String customerCode) {
        Optional<Customer> customer = customerManagementService.getCustomerByCustomerCode(customerCode);
        if (customer.isPresent()) {
            return jobOpeningRepository.getJobOpeningListMatchingCustomer(customer.get());
        }
        throw new NoSuchElementException("Unable to retrieve the customer with customer code " + customerCode);
    }

    public List<JobOpening> filterJobOpeningListBySTARTEDStatus(String started, List<JobOpening> virgemJobOpeningList) {
        List<JobOpening> filtered = jobOpeningRepository.getJobOpeningListMatchingStatus(started);
        virgemJobOpeningList.removeIf(jobOpening -> !filtered.contains(jobOpening));
        return virgemJobOpeningList;
    }

    public List<JobOpening> filterJobOpeningListByDateInterval(DateInterval interval, List<JobOpening> jobOpenings) {
        List<JobOpening> filtered = jobOpeningRepository.getJobOpeningListWithinDateInterval(interval);
        jobOpenings.removeIf(jobOpening -> !filtered.contains(jobOpening));
        if (jobOpenings.isEmpty()) {
            throw new NoSuchElementException("There are no job openings in the defined interval.");
        }
        return jobOpenings;
    }

    public Iterable<JobOpening> getAllUnfinishedJobOpenings() {
        return jobOpeningRepository.findAllJobOpeningsWithoutRecruitmentProcess();
    }

    public boolean setupJobOpeningWithRecruitmentProcess(RecruitmentProcess recruitmentProcess, JobOpening jobOpening) {
        JobOpening jobOpening1 = jobOpeningRepository.ofIdentity(jobOpening.jobReference()).get();
        jobOpening1.updateStatusToNotStarted();
        jobOpening1.addRecruitmentProcess(recruitmentProcess);

        jobOpening1 = jobOpeningRepository.save(jobOpening1);

        recruitmentProcess.referToJobOpening(jobOpening1);

        recruitmentProcessManagementService.saveToRepository(recruitmentProcess);

        return true;
    }

    public List<JobOpeningDTO> getJobOpeningsList() {
        List<JobOpeningDTO> jobOpenings = new ArrayList<>();
        for (JobOpening jobOpening : jobOpeningRepository.findAll()) {
            jobOpenings.add(jobOpening.toDTO());
        }
        return jobOpenings;
    }

    public boolean saveToRepository(JobOpening jobOpening){
        try {
            jobOpeningRepository.save(jobOpening);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public JobOpening getJobOpening(JobOpeningDTO jobOpeningDTO) {
        String jobReference = jobOpeningDTO.getJobReference();
        JobOpening jobOpening = null;

        for (JobOpening job : jobOpeningRepository.findAll()) {
            if (job.jobReference().toString().equals(jobReference)) {
                jobOpening = job;
            }
        }
        return jobOpening;
    }

    public Iterable<JobOpeningDTO> jobOpeningsOfCustomerManager(Username customerManagerUsername) {
        Iterable<JobOpening> list = jobOpeningRepository.getJobOpeningListMatchingCustomerManager(customerManagerUsername);
        return dtoSvc.convertToDTO(list);
    }

    public Iterable<JobOpeningDTO> getJobOpeningFromUsername(Username username) {
        Iterable<JobOpening> list = jobOpeningRepository.getJobOpeningFromUsername(username);
        return dtoSvc.convertToDTO(list);
    }

    public Iterable<JobOpeningDTO> plannedJobOpeningsOfCustomerManager(Username customerManagerUsername) {
        Iterable<JobOpening> list = jobOpeningRepository.getPlannedJobOpeningListMatchingCustomerManager(customerManagerUsername);
        return dtoSvc.convertToDTO(list);
    }

    public Iterable<JobOpeningDTO> jobOpeningsInScreeingListOfCustomerManager(Username customerManagerUsername) {
        return dtoSvc.convertToDTO(jobOpeningRepository.jobOpeningsInScreeingListOfCustomerManager(customerManagerUsername));
    }

    public Iterable<JobOpeningDTO> jobOpeningsListOfCustomerManager(Username customerManagerUsername) {
        return dtoSvc.convertToDTO(jobOpeningRepository.jobOpeningsListOfCustomerManager(customerManagerUsername));
    }

    public Iterable<JobOpeningDTO> getJobOpeningListInAnalysisPhase(Username customerManagerUsername){
        Iterable<JobOpening> jobs = jobOpeningRepository.getJobOpeningListInAnalysisPhase(customerManagerUsername);
        return dtoSvc.convertToDTO(jobs);
    }

    public List<JobOpeningDTO> getOnGoingJobOpenings() {
        List<JobOpening> jobOpenings = jobOpeningRepository.getJobOpeningListMatchingStatus(String.valueOf(JobOpeningStatusEnum.STARTED));
        List<JobOpeningDTO> jobOpeningDTOs = new ArrayList<>();
        for (JobOpening jobOpening : jobOpenings) {
            jobOpeningDTOs.add(jobOpening.toDTO());
        }
        return jobOpeningDTOs;
    }

    public List<JobOpeningDTO> getJobOpeningsInInterviewAndAnalysisPhase(Username customerManagerUsername){
        Iterable<JobOpening> jobOpenings = jobOpeningRepository.getJobOpeningListMatchingStatusFromCustomerManager(String.valueOf(JobOpeningStatusEnum.STARTED), customerManagerUsername);
        List<JobOpeningDTO> jobOpeningInInterviewAndAnalysisPhase = new ArrayList<>();
        for (JobOpening jobOpening : jobOpenings) {
            jobOpeningInInterviewAndAnalysisPhase.add(jobOpening.toDTO());
        }
        return jobOpeningInInterviewAndAnalysisPhase;
    }

    public Iterable<JobOpeningDTO> jobOpeningsInResultListOfCustomerManager(Username customerManagerUsername) {
        return dtoSvc.convertToDTO(jobOpeningRepository.jobOpeningsInResultListOfCustomerManager(customerManagerUsername));
    }

    public List<ApplicationDTO> getApplicationsList(Iterable<JobOpeningDTO> jobOpeningDTOList) {
        List<ApplicationDTO> applicationDTOList = new ArrayList<>();
        for (JobOpeningDTO dto :jobOpeningDTOList) {
            JobOpening jobOpening = getJobOpening(dto);
            List<ApplicationDTO> applicationDTOFromJobOpening = applicationManagementService.getApplicationsList(jobOpening);
            applicationDTOList.addAll(applicationDTOFromJobOpening);
        }
        return applicationDTOList;
    }

    public Iterable<JobOpeningDTO> getOnGoingJobOpeningsInScreeningPhase() {
        List<JobOpening> jobOpeningList = new ArrayList<>();
        List<JobOpening> jobOpenings = jobOpeningRepository.getJobOpeningListMatchingStatus(String.valueOf(JobOpeningStatusEnum.STARTED));
        for (JobOpening jobOpening : jobOpenings) {
            if (recruitmentProcessManagementService.checkIfRecruitmentProcessIsInScreeningPhase(jobOpening.jobReference().toString())){
                jobOpeningList.add(jobOpening);
            }
        }
        return dtoSvc.convertToDTO(jobOpeningList);
    }
}

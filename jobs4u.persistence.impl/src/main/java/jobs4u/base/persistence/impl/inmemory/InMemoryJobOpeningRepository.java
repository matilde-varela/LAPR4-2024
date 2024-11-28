package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import eapli.framework.time.domain.model.DateInterval;
import jakarta.persistence.TypedQuery;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.customermanagement.repository.CustomerRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatus;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatusEnum;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;

import java.util.*;

public class InMemoryJobOpeningRepository
        extends InMemoryDomainRepository<JobOpening, JobReference>
        implements JobOpeningRepository {

    static {
        InMemoryInitializer.init();
    }

    private final CustomerRepository custRepo = PersistenceContext.repositories().customers();

    @Override
    public JobReference lastJobReference(String customerCode) {
        int repoSize = (int) size();
        JobReference lastJobReference = null;
        if (repoSize == 0) {
                       return new JobReference(customerCode, 0);
        }
        Iterable<JobOpening> jobOpenings = findAll();
        for (JobOpening element : jobOpenings) {
            lastJobReference = element.identity();
        }
        if (lastJobReference == null) {
            throw new NoSuchElementException("It was not possible to retrieve the last registered job opening.");
        }
        return lastJobReference;
    }

    @Override
    public Iterable<JobOpening> findAllJobOpeningsWithCustomerCode(String c) {
        return match(e -> Objects.equals(e.jobReference().getcustomerCode(), c));
    }

    @Override
    public List<JobOpening> getJobOpeningListMatchingCustomerCodesList(Set<CustomerCode> customerCodes) {
        List<JobOpening> jobOpeningArrayList = new ArrayList<>();
        for (CustomerCode code : customerCodes) {
            Iterable<JobOpening> jobOpenings = match(e -> e.jobReference().getcustomerCode().equals(code.toString()));
            for (JobOpening element : jobOpenings) {
                jobOpeningArrayList.add(element);
            }
        }
        return jobOpeningArrayList;
    }

    @Override
    public List<JobOpening> getJobOpeningListMatchingCustomer(Customer customer) {
        List<JobOpening> jobOpeningArrayList = new ArrayList<>();

        Iterable<JobOpening> jobOpenings = match(e -> {
            e.jobReference().getcustomerCode();
            customer.customerCode();
            return false;
        });
        for (JobOpening element : jobOpenings) {
            jobOpeningArrayList.add(element);
        }
        return jobOpeningArrayList;
    }

    @Override
    public List<JobOpening> getJobOpeningListMatchingStatus(String started) {
        List<JobOpening> jobOpeningArrayList = new ArrayList<>();
        Iterable<JobOpening> jobOpenings = match(e -> e.currentStatus().toString().equals(started));
        for (JobOpening element : jobOpenings) {
            jobOpeningArrayList.add(element);
        }
        return jobOpeningArrayList;
    }

    @Override
    public List<JobOpening> getJobOpeningListWithinDateInterval(DateInterval interval) {
        List<JobOpening> jobOpeningArrayList = new ArrayList<>();
        Iterable<JobOpening> jobOpenings = match(e -> e.getRecruitmentProcess().getRecruitmentPeriod().getRecruitmentInterval().start().compareTo(interval.start()) >= 0
                || e.getRecruitmentProcess().getRecruitmentPeriod().getRecruitmentInterval().end().compareTo(interval.end()) <= 0);
        for (JobOpening element : jobOpenings) {
            jobOpeningArrayList.add(element);
        }
        return jobOpeningArrayList;
    }

    @Override
    public Iterable<JobOpening> getUNFINISHEDJobOpeningList() {
        String status = new JobOpeningStatus(JobOpeningStatusEnum.UNFINISHED).getStatusDescription();
        List<JobOpening> jobOpeningArrayList = new ArrayList<>();
        Iterable<JobOpening> jobOpenings = match(e -> e.currentStatus().toString().equals(status));
        for (JobOpening element : jobOpenings) {
            jobOpeningArrayList.add(element);
        }
        return jobOpeningArrayList;
    }


    @Override
    public Iterable<JobOpening> findAllJobOpeningsNotStarted() {
        return match(e -> e.jobOpeningStatus().getStatusDescription().equals(String.valueOf(JobOpeningStatusEnum.UNFINISHED))
                || e.jobOpeningStatus().getStatusDescription().equals(String.valueOf(JobOpeningStatusEnum.NOT_STARTED)));
    }

    @Override
    public Iterable<JobOpening> findAllJobOpeningsWithoutRecruitmentProcess() {
        return match(e -> e.jobOpeningStatus().getStatusDescription().equals(String.valueOf(JobOpeningStatusEnum.UNFINISHED)));
    }

    @Override
    public Optional<JobOpening> getJobOpeningByJobReference(JobReference id) {
        return matchOne(e -> e.identity().equals(id));
    }

    @Override
    public Iterable<JobOpening> getJobOpeningListMatchingCustomerManager(Username customerManagerUsername) {
        List<Customer> customers = custRepo.getCustomersByUsername(customerManagerUsername);

        for (Customer customer : customers) {
            return match(e -> customer.customerCode().toString().contains(e.jobReference().getcustomerCode()));
        }
        return Collections.emptyList();
    }

    @Override
    public Iterable<JobOpening> getJobOpeningFromUsername(Username username) {
        List<Customer> customers = custRepo.getCustomersByUsername(username);
        for (Customer customer : customers) {
            return match(e -> customer.customerCode().costumerCode().equals(e.jobReference().getcustomerCode()));
        }
        return Collections.emptyList();
    }

    @Override
    public Iterable<JobOpening> jobOpeningsInScreeingListOfCustomerManager(Username customerManagerUsername) {
        CustomerRepository custRepo = PersistenceContext.repositories().customers();
        List<Customer> customers = custRepo.getCustomersByUsername(customerManagerUsername);

        return match(e -> {
            if (e.getRecruitmentProcess().currentActivePhase().equalsIgnoreCase("screening")) {
                return customers.stream().anyMatch(customer -> customer.customerCode().toString().equals(e.jobReference().getcustomerCode()));
            }
            return false;
        });
    }

    @Override
    public Iterable<JobOpening> jobOpeningsListOfCustomerManager(Username customerManagerUsername) {
        CustomerRepository custRepo = PersistenceContext.repositories().customers();
        List<Customer> customers = custRepo.getCustomersByUsername(customerManagerUsername);

        return match (e -> customers.stream().anyMatch(customer -> customer.customerCode().toString().equals(e.jobReference().getcustomerCode())));
    }



    @Override
    public Iterable<JobOpening> getPlannedJobOpeningListMatchingCustomerManager(Username customerManagerUsername) {
        CustomerRepository custRepo = PersistenceContext.repositories().customers();
        List<Customer> customers = custRepo.getCustomersByUsername(customerManagerUsername);

        for (Customer customer : customers) {
            return match(e -> customer.customerCode().toString().contains(e.jobReference().getcustomerCode()) && (e.jobOpeningStatus().equals(JobOpeningStatusEnum.NOT_STARTED) || e.jobOpeningStatus().equals(JobOpeningStatusEnum.STARTED)));
        }
        return Collections.emptyList();
    }

    @Override
    public Iterable<JobOpening> getJobOpeningListInAnalysisPhase(Username customerManagerUsername) {
        CustomerRepository custRepo = PersistenceContext.repositories().customers();
        List<Customer> customers = custRepo.getCustomersByUsername(customerManagerUsername);

        return match(e -> {
            if (e.getRecruitmentProcess().currentActivePhase().equalsIgnoreCase("interview") || e.getRecruitmentProcess().currentActivePhase().equalsIgnoreCase("analysis")) {
                return customers.stream().anyMatch(customer -> customer.customerCode().toString().equals(e.jobReference().getcustomerCode()));
            }
            return false;
        });
    }

    @Override
    public Iterable<JobOpening> jobOpeningsInResultListOfCustomerManager(Username customerManagerUsername) {
        CustomerRepository custRepo = PersistenceContext.repositories().customers();
        List<Customer> customers = custRepo.getCustomersByUsername(customerManagerUsername);

        return match(e -> {
            if (e.getRecruitmentProcess().currentActivePhase().equalsIgnoreCase("results")) {
                return customers.stream().anyMatch(customer -> customer.customerCode().toString().equals(e.jobReference().getcustomerCode()));
            }
            return false;
        });
    }

    @Override
    public Iterable<JobOpening> getJobOpeningListMatchingStatusFromCustomerManager(String started, Username customerManagerUsername) {
    CustomerRepository custRepo = PersistenceContext.repositories().customers();
    List<Customer> customers = custRepo.getCustomersByUsername(customerManagerUsername);

        return match(e -> {
        if (e.currentStatus().toString().equals(started) && (e.getRecruitmentProcess().currentActivePhase().equalsIgnoreCase("analysis") || e.getRecruitmentProcess().currentActivePhase().equalsIgnoreCase("interview"))) {
            return customers.stream().anyMatch(customer -> customer.customerCode().toString().equals(e.jobReference().getcustomerCode()));
        }
        return false;
    });
}

}

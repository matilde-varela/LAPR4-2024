package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import eapli.framework.time.domain.model.DateInterval;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import jobs4u.base.Application;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.customermanagement.domain.CustomerCode;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatus;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatusEnum;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import org.hibernate.HibernateException;


import java.util.*;

public class JpaJobOpeningRepository
        extends JpaAutoTxRepository<JobOpening, JobReference, JobReference>
        implements JobOpeningRepository {


    public JpaJobOpeningRepository(final TransactionalContext autoTx) {
        super(autoTx, "jobReference");
    }

    public JpaJobOpeningRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "jobReference");
    }

    @Override
    public Iterable<JobOpening> findAllJobOpeningsNotStarted() {
        return match("e.status.statusDescription = 'UNFINISHED' OR e.status.statusDescription ='NOT_STARTED'");
    }

    @Override
    public Iterable<JobOpening> findAllJobOpeningsWithCustomerCode(String c) {
        return match("e.jobReference.companyCode = :code", "code", c);
    }

    @Override
    public Iterable<JobOpening> findAllJobOpeningsWithoutRecruitmentProcess() {
        return match("e.status.statusDescription = 'UNFINISHED'");
    }

    @Override
    public synchronized JobReference lastJobReference(String customerCode) {
        Iterable<JobOpening> jobOpenings = findAllJobOpeningsWithCustomerCode(customerCode);
        List<JobOpening> c = (List<JobOpening>) jobOpenings;
        int size = c.size();
        JobReference lastJobReference = null;
        if (size == 0) {
            return new JobReference(customerCode, 0);
        }
        for (JobOpening element : jobOpenings) {
            lastJobReference = element.identity();
        }
        if (lastJobReference == null) {
            throw new NoSuchElementException("It was not possible to retrieve the last registered job opening.");
        }
        return lastJobReference;
    }

    @Override
    public List<JobOpening> getJobOpeningListMatchingCustomerCodesList(Set<CustomerCode> customerCodes) {
        List<JobOpening> jobOpenings = new ArrayList<>();
        for (CustomerCode code : customerCodes) {
            try {
                jobOpenings.addAll(match("e.jobReference.companyCode = :code", "code", code.costumerCode()));
            } catch (HibernateException ex) {
                jobOpenings.addAll(match("e=(SELECT c FROM JobOpening c WHERE c.jobReference.companyCode=:code)",
                        "code", code.costumerCode()));
            }
        }
        return jobOpenings;
    }

    @Override
    public List<JobOpening> getJobOpeningListMatchingCustomer(Customer customer) {
        try {
            return match("e.jobReference.companyCode = :code", "code", customer.customerCode().toString());
        } catch (HibernateException ex) {
            return match("e=(SELECT c FROM JobOpening c WHERE c.jobReference.companyCode=:code)",
                    "code", customer.customerCode().costumerCode());
        }
    }

    @Override
    public List<JobOpening> getJobOpeningListMatchingStatus(String status) {
        final TypedQuery<JobOpening>
                q = createQuery("SELECT c" +
                        " FROM JobOpening c" +
                        " WHERE c.status.statusDescription = :status",
                JobOpening.class);
        q.setParameter("status", status);
        return q.getResultList();
    }

    @Override
    public List<JobOpening> getJobOpeningListWithinDateInterval(DateInterval interval) {
        final TypedQuery<JobOpening> q = createQuery("SELECT e FROM JobOpening e WHERE e.recruitmentProcess.recruitmentPeriod.recruitmentInterval.dateStart BETWEEN :start AND :end",
                JobOpening.class);
        q.setParameter("start", interval.start(), TemporalType.DATE);
        q.setParameter("end", interval.end(), TemporalType.DATE);
        List<JobOpening> startMatch = q.getResultList();

        final TypedQuery<JobOpening> s = createQuery("SELECT e FROM JobOpening e WHERE e.recruitmentProcess.recruitmentPeriod.recruitmentInterval.dateEnd BETWEEN :start AND :end",
                JobOpening.class);
        s.setParameter("start", interval.start(), TemporalType.DATE);
        s.setParameter("end", interval.end(), TemporalType.DATE);
        List<JobOpening> endMatch = s.getResultList();

        List<JobOpening> resultList = new ArrayList<>(startMatch);
        resultList.retainAll(endMatch);

        return resultList;
//        final Map<String, Object> params = new HashMap<>();
//        params.put("start", interval.start());
//        params.put("end", interval.start());
//        return match("e.recruitmentProcess.recruitmentPeriod.recruitmentInt   erval.dateStart >= :start" +
//                "AND e.recruitmentProcess.recruitmentPeriod.recruitmentInterval.dateEnd <= :end", params);
    }

    @Override
    public Iterable<JobOpening> getUNFINISHEDJobOpeningList() {
        String status = new JobOpeningStatus(JobOpeningStatusEnum.UNFINISHED).getStatusDescription();
        try {
            return match("e.status.statusDescription = :status", "status", status);
        } catch (HibernateException ex) {
            return match("e=(SELECT c FROM JobOpening c WHERE c.status.statusDescription = :status)",
                    "status", status);
        }
    }

    @Override
    public Optional<JobOpening> getJobOpeningByJobReference(JobReference id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return matchOne("e.jobReference=:id", params);
    }

    @Override
    public Iterable<JobOpening> getJobOpeningListMatchingCustomerManager(Username customerManagerUsername) {
        final TypedQuery<JobOpening>
                q = createQuery("SELECT e \n" +
                        "FROM JobOpening e \n" +
                        "WHERE e.jobReference.companyCode IN (\n" +
                        "    SELECT code.customerCode \n" +
                        "    FROM Customer c \n" +
                        "    WHERE c.customerManager.username = :manager\n" +
                        ")",
                JobOpening.class);
        q.setParameter("manager", customerManagerUsername);
        return q.getResultList();
    }

    @Override
    public Iterable<JobOpening> jobOpeningsInScreeingListOfCustomerManager(Username customerManagerUsername) {
        final TypedQuery<JobOpening>
                q = createQuery("SELECT e \n" +
                        "FROM JobOpening e \n" +
                        "JOIN RecruitmentProcess rp ON e.jobReference = rp.jobOpening.jobReference\n" +
                        "WHERE rp.recruitmentProcessStatus.statusDescription = 'SCREENING'\n" +
                        "AND e.jobReference.companyCode IN (\n" +
                        "    SELECT code.customerCode \n" +
                        "    FROM Customer c \n" +
                        "    WHERE c.customerManager.username.value = :manager\n" +
                        ")",
                JobOpening.class);
        q.setParameter("manager", customerManagerUsername.toString());
        return q.getResultList();
    }

    @Override
    public Iterable<JobOpening> getPlannedJobOpeningListMatchingCustomerManager(Username customerManagerUsername) {
        final TypedQuery<JobOpening>
                q = createQuery("SELECT e \n" +
                        "FROM JobOpening e \n" +
                        "WHERE e.jobReference.companyCode IN (\n" +
                        "    SELECT code.customerCode \n" +
                        "    FROM Customer c \n" +
                        "    WHERE c.customerManager.username.value = :manager\n" +
                        ")\n" +
                        "AND (e.status.statusDescription = 'STARTED' OR e.status.statusDescription = 'NOT_STARTED')",
                JobOpening.class);
        q.setParameter("manager", customerManagerUsername.toString());
        return q.getResultList();
    }

    @Override
    public Iterable<JobOpening> jobOpeningsListOfCustomerManager(Username customerManagerUsername) {
        final TypedQuery<JobOpening>
                q = createQuery("SELECT e \n" +
                        "FROM JobOpening e \n" +
                        "WHERE e.jobReference.companyCode IN (\n" +
                        "    SELECT code.customerCode \n" +
                        "    FROM Customer c \n" +
                        "    WHERE c.customerManager.username.value = :manager\n" +
                        ")\n",
                JobOpening.class);
        q.setParameter("manager", customerManagerUsername.toString());
        return q.getResultList();
    }

    @Override
    public Iterable<JobOpening> getJobOpeningFromUsername(Username username) {
        List<String> statuses = Arrays.asList("NOT_STARTED", "STARTED", "ENDED");

        final TypedQuery<JobOpening> q = createQuery(
                "SELECT e " +
                        "FROM JobOpening e " +
                        "WHERE e.jobReference.companyCode IN (" +
                        "    SELECT c.code.customerCode " +
                        "    FROM Customer c " +
                        "    WHERE c.customerUser.username = :username)" +
                        "AND e.status.statusDescription IN (:statuses)",
                JobOpening.class);

        q.setParameter("username", username);
        q.setParameter("statuses", statuses);
        return q.getResultList();
    }

    @Override
    public Iterable<JobOpening> getJobOpeningListInAnalysisPhase(Username customerManagerUsername) {
        final TypedQuery<JobOpening>
                q = createQuery("SELECT e \n" +
                        "FROM JobOpening e \n" +
                        "JOIN RecruitmentProcess rp ON e.jobReference = rp.jobOpening.jobReference\n" +
                        "WHERE rp.recruitmentProcessStatus.statusDescription = 'ANALYSIS' \n" +
                        "AND e.jobReference.companyCode IN (\n" +
                        "    SELECT code.customerCode \n" +
                        "    FROM Customer c \n" +
                        "    WHERE c.customerManager.username.value = :manager\n" +
                        ")",
                JobOpening.class);
        q.setParameter("manager", customerManagerUsername.toString());
        return q.getResultList();
    }

    @Override
    public Iterable<JobOpening> jobOpeningsInResultListOfCustomerManager(Username customerManagerUsername) {
        final TypedQuery<JobOpening>
                q = createQuery("SELECT e \n" +
                        "FROM JobOpening e \n" +
                        "JOIN RecruitmentProcess rp ON e.jobReference = rp.jobOpening.jobReference\n" +
                        "WHERE rp.recruitmentProcessStatus.statusDescription = 'RESULTS'\n" +
                        "AND e.jobReference.companyCode IN (\n" +
                        "    SELECT code.customerCode \n" +
                        "    FROM Customer c \n" +
                        "    WHERE c.customerManager.username.value = :manager\n" +
                        ")",
                JobOpening.class);
        q.setParameter("manager", customerManagerUsername.toString());
        return q.getResultList();
    }

    @Override
    public Iterable<JobOpening> getJobOpeningListMatchingStatusFromCustomerManager(String status, Username customerManagerUsername) {
        final TypedQuery<JobOpening>
                q = createQuery("SELECT e" +
                        " FROM JobOpening e" +
                        " JOIN RecruitmentProcess rp ON e.jobReference = rp.jobOpening.jobReference" +
                        " WHERE (rp.recruitmentProcessStatus.statusDescription = 'ANALYSIS' OR  rp.recruitmentProcessStatus.statusDescription = 'INTERVIEW')" +
                        " AND e.status.statusDescription = :status" +
                        " AND e.jobReference.companyCode IN (" +
                        "    SELECT code.customerCode " +
                        "    FROM Customer c " +
                        "    WHERE c.customerManager.username.value = :manager" +
                        ")",
                JobOpening.class);
        q.setParameter("status", status);
        q.setParameter("manager", customerManagerUsername.toString());
        return q.getResultList();
    }

}

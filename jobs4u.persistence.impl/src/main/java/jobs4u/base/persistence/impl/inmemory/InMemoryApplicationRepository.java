package jobs4u.base.persistence.impl.inmemory;


import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatus;
import jobs4u.base.jobopeningmanagement.domain.JobOpeningStatusEnum;
import jobs4u.base.jobopeningmanagement.domain.JobReference;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;

import java.util.*;

public class InMemoryApplicationRepository
        extends InMemoryDomainRepository<Application, Long>
        implements ApplicationRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<Application> applications() {
        List<Application> applicationList = new ArrayList<>();
        Iterable<Application> applications = findAll();
        for (Application app : applications) {
            applicationList.add(app);
        }
        return applicationList;
    }

    @Override
    public Iterable<Application> applicationsForJobOpeningWithRequirements(String jobReference) {
        JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
        Optional<JobOpening> jos = jobOpeningRepository.getJobOpeningByJobReference(new JobReference(jobReference));
        jos.ifPresent(jobOpening -> match(e -> e.requirementAnswerFilePath() != null && jobOpening.getApplications().contains(e)));
        return Collections.emptyList();
    }

    @Override
    public Iterable<Application> applicationsForJobOpeningWithoutInterviews(String jobReference) {
        JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
        Optional<JobOpening> jos = jobOpeningRepository.getJobOpeningByJobReference(new JobReference(jobReference));
        jos.ifPresent(jobOpening -> match(e -> e.interview() != null && jobOpening.getApplications().contains(e)));
        return Collections.emptyList();
    }


    @Override
    public Iterable<Application> applicationsFromCandidate(String phoneNumber) {
        return match(e -> e.candidate().phoneNumber().number().equals(phoneNumber));
    }

    @Override
    public Application getApplicationFromDTO(ApplicationDTO applicationDTO) {
        Optional<Application> optionalApplication = matchOne(e -> e.identity().equals(applicationDTO.getId()));
        if (optionalApplication.isPresent()) {
            return optionalApplication.get();
        }
        throw new NoSuchElementException("No application with id " + applicationDTO.getId() + " found");
    }
    @Override
    public Iterable<Application> applicationsForJobOpeningWithInterviewAnswers(String jobReference) {
        JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
        Optional<JobOpening> jobOpenings = jobOpeningRepository.getJobOpeningByJobReference(new JobReference(jobReference));
        jobOpenings.ifPresent(jobOpening -> match(e -> e.interview() != null && jobOpening.getApplications().contains(e)));
        return Collections.emptyList();
    }

    @Override
    public Iterable<Application> getApplicationFromCandidateUserName(Username username) {
        return match(e -> e.candidate().user().username().equals(username)); //change this later to be correct and related to the jpaApplicationRepository
    }

    @Override
    public Iterable<Application> getApplicationsWithInterviewGrade(String jobReference) {
        JobOpeningRepository jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
        Optional<JobOpening> jobOpenings = jobOpeningRepository.getJobOpeningByJobReference(new JobReference(jobReference));
        jobOpenings.ifPresent(jobOpening -> match(e -> e.interview().interviewResult() != null && jobOpening.getApplications().contains(e)));
        return Collections.emptyList();
    }
}

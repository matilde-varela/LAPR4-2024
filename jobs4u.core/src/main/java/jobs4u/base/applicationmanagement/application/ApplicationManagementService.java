package jobs4u.base.applicationmanagement.application;

import eapli.framework.infrastructure.authz.domain.model.Username;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.domain.ApplicationFile;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.requirementsmanagement.application.ApplicationListDTOService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.*;

public class ApplicationManagementService {

    private final ApplicationRepository applicationRepository = PersistenceContext.
            repositories().applications();

    private final JobOpeningRepository jobOpeningRepository = PersistenceContext.
            repositories().jobOpenings();

    private final ApplicationDTOService applicationDTOService = new ApplicationDTOService();

    private final ApplicationListDTOService applicationListDTOService = new ApplicationListDTOService();


    public Application registerApplication(Set<ApplicationFile> files,
                                           Date applicationDate, Candidate candidate){
        return new Application(files, applicationDate, candidate);
    }

    public Application registerApplicationWithoutCandidate(Set<ApplicationFile> files,
                                           Date applicationDate){

        return new Application(files, applicationDate);
    }

    public List<ApplicationDTO> getAllApplicationsThatHaveCandidate(Candidate candidate){
        List<ApplicationDTO> list = new ArrayList<>();
        for(Application application : applicationRepository.applications()){
            if(application.candidate().sameAs(candidate)){
                list.add(application.toDTO());
            }
        }
        return list;
    }


    public List<ApplicationDTO> getApplicationsList(JobOpening jobOpening){
        Set<Application> applicationList = jobOpening.getApplications();

        return (List<ApplicationDTO>) applicationDTOService.convertToDTO(applicationList);
    }

    public List<ApplicationDTO> getApplicationsWithInterviewGrade(JobOpening jobOpening){
        Iterable<Application> applicationList = applicationRepository.getApplicationsWithInterviewGrade(jobOpening.jobReference().toString());

        return (List<ApplicationDTO>) applicationDTOService.convertToDTO(applicationList);
    }

    public Set<Application> getApplications(JobOpening jobOpening){
        return jobOpening.getApplications();
    }


    public Map<ApplicationDTO, Integer> getApplicationsAndNumber(Username username) {
        Iterable<Application> list = applicationRepository.getApplicationFromCandidateUserName(username);
        Map<ApplicationDTO, Integer> applicationDTOMap = new LinkedHashMap<>();
        for (Application application : list) {
                for (JobOpening jobOpening : jobOpeningRepository.findAll()) {
                    for (Application app : jobOpening.getApplications()) {
                        if (application.equals(app)){
                            applicationDTOMap.put(application.toDTO(), jobOpening.getApplications().size());
                        }
                    }
                }
        }

        return applicationDTOMap;
    }

    public List<ApplicationDTO> getApplicationsFromJobReference(String jobReference) {
        Iterable<Application> applications = applicationRepository.applicationsForJobOpeningWithRequirements(jobReference);
        return applicationListDTOService.convertApplicationsToDTO(applications);
    }

    public void uploadAnswersFile(ApplicationDTO applicationDTO, String filepath) {
        Application application = applicationRepository.getApplicationFromDTO(applicationDTO);
        application.updateRequirementAnswer(filepath);
        applicationRepository.save(application);
    }

    public void uploadInterviewAnswerFile(ApplicationDTO applicationDTO, String filepath){
        Application application = applicationRepository.getApplicationFromDTO(applicationDTO);
        application.interview().updateInterviewAnswer(filepath);
        applicationRepository.save(application);
    }

    public Optional<Application> getApplicationWithId(Long id){
        return applicationRepository.ofIdentity(id);
    }

    public Iterable<ApplicationDTO> getAllApplicationsThatHaveCandidate(String phoneNumber){
        return applicationDTOService.convertToDTO(applicationRepository.applicationsFromCandidate(phoneNumber));
    }

    public Application getApplication(ApplicationDTO applicationDto) {
        Long applicationId = applicationDto.getId();
        Application application = null;

        for (Application application1  : applicationRepository.applications()) {
            if (application1.identity().toString().equals(applicationId.toString())) {
                application =application1;
            }
        }
        return application;
    }

    public List<ApplicationDTO> getApplicationsOrderedByInterviewResult(List<ApplicationDTO> applicationListDTO) {
        List<ApplicationDTO> orderList = applicationListDTO;

        Collections.sort(orderList, new Comparator<ApplicationDTO>() {
            @Override
            public int compare(ApplicationDTO app1, ApplicationDTO app2) {
                Integer grade1 = app1.getInterview().interviewResult().interviewGrade();
                Integer grade2 = app2.getInterview().interviewResult().interviewGrade();


                return grade2.compareTo(grade1);
            }
        });

        return orderList;
    }

    public String getApplicationFileContent(ApplicationDTO applicationDTO, String filename) {
        Application application = getApplication(applicationDTO);
        ApplicationFile file = null;
        for (ApplicationFile applicationFile : application.allFiles()) {
            if (applicationFile.getApplicationFile().getName().equals(filename)){
                file = applicationFile;
            }
        }
        assert file != null;
        try {
            return file.getContent();
        }catch (FileNotFoundException e){
            return "!";
        }
    }
}

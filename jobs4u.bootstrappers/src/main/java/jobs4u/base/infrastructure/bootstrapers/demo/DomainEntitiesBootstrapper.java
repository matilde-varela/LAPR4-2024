package jobs4u.base.infrastructure.bootstrapers.demo;

import eapli.framework.actions.Action;
import eapli.framework.infrastructure.authz.domain.model.Role;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.time.domain.model.DateInterval;
import eapli.framework.time.util.Calendars;
import jobs4u.base.applicationmanagement.domain.*;
import jobs4u.base.candidatemanagement.application.CandidateManagementService;
import jobs4u.base.candidatemanagement.domain.Candidate;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.candidatemanagement.repository.CandidateRepository;
import jobs4u.base.contracttypemanagement.domain.ContractType;
import jobs4u.base.criteriamanagement.domain.Criteria;
import jobs4u.base.criteriamanagement.repository.CriteriaRepository;
import jobs4u.base.customermanagement.application.CustomerManagementService;
import jobs4u.base.infrastructure.bootstrapers.UsersBootstrapperBase;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.domain.*;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;
import jobs4u.base.notificationmanagement.domain.*;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.rankmanagement.domain.Rank;
import jobs4u.base.rankmanagement.domain.RankOrder;
import jobs4u.base.rankmanagement.persistence.RankRepository;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;
import jobs4u.base.contracttypemanagement.repository.ContractTypeRepository;
import jobs4u.base.jobopeningmanagement.repositories.JobOpeningRepository;
import jobs4u.base.workmodemanagement.repository.WorkModeRepository;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;
import jobs4u.base.requirementsmanagement.domain.RequirementSpecification;
import jobs4u.base.requirementsmanagement.repositories.RequirementSpecificationRepository;
import jobs4u.base.recruitmentprocessmanagement.domain.*;
import jobs4u.base.recruitmentprocessmanagement.repository.RecruitmentProcessRepository;
import jobs4u.base.usermanagement.domain.BaseRoles;
import jobs4u.base.workmodemanagement.domain.WorkMode;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DomainEntitiesBootstrapper extends UsersBootstrapperBase implements Action {

    CustomerManagementService customerManagementService = new CustomerManagementService();

    CandidateManagementService candidateManagementService = new CandidateManagementService();

    private ContractTypeRepository contractTypeRepository;
    private WorkModeRepository workModeRepository;
    private RequirementSpecificationRepository requirementSpecificationRepository;
    private InterviewModelRepository interviewModelRepository;
    private JobOpeningRepository jobOpeningRepository;
    List<InterviewModel> interviewModelsList = new ArrayList<>();
    List<JobOpening> jobOpeningList = new ArrayList<>();
    private CriteriaRepository criteriaRepository;
    private RecruitmentProcessRepository recruitmentProcessRepository;
    private NotificationRepository notificationRepository;
    private RankRepository rankRepository;

    private CandidateRepository candidateRepository;

    List<RequirementSpecification> requirementSpecificationsList = new ArrayList<>();

    List<RecruitmentProcess> recruitmentProcessList = new ArrayList<>();

    @Override
    public boolean execute() {
        instantiateRepositories();
        persistCriteria();
        persistCustomers();
        persistCandidates();
        persistContractTypes();
        persistWorkModes();
        persistRequirementSpecifications();
        persistInterviewModels();
        persistRecruitmentProcesses();
        persistJobOpenings();
        persistApplications();
        persistNotifications();
        persistRank();
        return true;
    }

    private void persistRank() {
        Optional<JobOpening> jo = jobOpeningRepository.getJobOpeningByJobReference(new JobReference("AVIP", 1));

        if (jo.isPresent()) {
            Iterable<Application> applicationsIterable = jo.get().getApplications();
            List<Application> applications = new ArrayList<>();
            applicationsIterable.forEach(applications::add);

            if (applications.size() >= 2) {
                List<RankOrder> rankOrders = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    rankOrders.add(new RankOrder(applications.get(i), i + 1));
                }

                Rank rank = new Rank(new JobReference("AVIP", 1), rankOrders);
                rankRepository.save(rank);
            }
        }
    }

    public void instantiateRepositories() {
        this.notificationRepository = PersistenceContext.repositories().notifications();
        this.contractTypeRepository = PersistenceContext.repositories().contractTypes();
        this.workModeRepository = PersistenceContext.repositories().workModes();
        this.requirementSpecificationRepository = PersistenceContext.repositories().requirementSpecifications();
        this.jobOpeningRepository = PersistenceContext.repositories().jobOpenings();
        this.interviewModelRepository = PersistenceContext.repositories().interviewModels();
        this.criteriaRepository = PersistenceContext.repositories().criteria();
        this.recruitmentProcessRepository = PersistenceContext.repositories().recruitmentProcesses();
        this.candidateRepository = PersistenceContext.repositories().candidates();
        this.rankRepository = PersistenceContext.repositories().ranks();
    }

    private void persistCriteria() {
        criteriaRepository.save(new Criteria("Status [STARTED]", JobOpening.class.getSimpleName()));
        criteriaRepository.save(new Criteria("Company Name", JobOpening.class.getSimpleName()));
        criteriaRepository.save(new Criteria("Customer Code", JobOpening.class.getSimpleName()));
        criteriaRepository.save(new Criteria("Time Interval", JobOpening.class.getSimpleName()));
    }

    private void persistNotifications() {
        Calendar calendar1 = null, calendar2 = null, calendar3 = null,
                calendar4 = null, calendar5 = null, calendar6 = null;

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        try {
            calendar1 = Calendars.fromDate(df.parse("16-03-2024"));
            calendar2 = Calendars.fromDate(df.parse("17-03-2024"));
            calendar3 = Calendars.fromDate(df.parse("18-03-2024"));
            calendar4 = Calendars.fromDate(df.parse("19-03-2024"));
            calendar5 = Calendars.fromDate(df.parse("20-03-2024"));
            calendar6 = Calendars.fromDate(df.parse("21-03-2024"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CANDIDATE)), new NotificationRecipient("candidate@email.com"), new NotificationBody("YOUR APPLICATION FOR THE JOB OF Night Guard AT ISEP WAS RECEIVED."), new NotificationDate(calendar1), new NotificationStatus(String.valueOf(NotificationStatusEnum.SEEN))));
        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CANDIDATE)), new NotificationRecipient("candidate@email.com"), new NotificationBody("YOUR APPLICATION FOR THE JOB OF Night Guard AT ISEP WAS ACCEPTED."), new NotificationDate(calendar2), new NotificationStatus(String.valueOf(NotificationStatusEnum.SEEN))));
        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CANDIDATE)), new NotificationRecipient("candidate@email.com"), new NotificationBody("YOU WERE CHOSEN FOR THE JOB OF Night Guard AT ISEP."), new NotificationDate(calendar3), new NotificationStatus(String.valueOf(NotificationStatusEnum.UNSEEN))));

        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CUSTOMER)), new NotificationRecipient("c@email.com"), new NotificationBody("YOUR JOB OPENING ISEP-4 IS ON IT'S APPLICATION PHASE."), new NotificationDate(calendar1), new NotificationStatus(String.valueOf(NotificationStatusEnum.SEEN))));
        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CUSTOMER)), new NotificationRecipient("c@email.com"), new NotificationBody("YOUR JOB OPENING ISEP-4 IS ON IT'S SCREENING PHASE."), new NotificationDate(calendar2), new NotificationStatus(String.valueOf(NotificationStatusEnum.SEEN))));
        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CUSTOMER)), new NotificationRecipient("c@email.com"), new NotificationBody("YOUR JOB OPENING ISEP-4 IS ON IT'S INTERVIEW PHASE."), new NotificationDate(calendar3), new NotificationStatus(String.valueOf(NotificationStatusEnum.SEEN))));
        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CUSTOMER)), new NotificationRecipient("c@email.com"), new NotificationBody("YOUR JOB OPENING ISEP-4 IS ON IT'S ANALYSIS PHASE."), new NotificationDate(calendar4), new NotificationStatus(String.valueOf(NotificationStatusEnum.UNSEEN))));
        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CUSTOMER)), new NotificationRecipient("c@email.com"), new NotificationBody("YOUR JOB OPENING ISEP-4 IS ON IT'S RESULTS PHASE."), new NotificationDate(calendar5), new NotificationStatus(String.valueOf(NotificationStatusEnum.UNSEEN))));
        notificationRepository.save(new Notification(new NotificationType(String.valueOf(NotificationTypeEnum.CUSTOMER)), new NotificationRecipient("c@email.com"), new NotificationBody("YOUR JOB OPENING ISEP-4 HAS CONCLUDED."), new NotificationDate(calendar6), new NotificationStatus(String.valueOf(NotificationStatusEnum.UNSEEN))));
    }

    private void persistRecruitmentProcesses() {
        DateInterval dateInterval1 = null, dateInterval2 = null,
                dateInterval3 = null, dateInterval4 = null,
                dateInterval5 = null, dateInterval6 = null;

        Calendar start1 = null, end1 = null, start2 = null, end2 = null,
                start3 = null, end3 = null, start4 = null, end4 = null,
                start5 = null, end5 = null, start6 = null, end6 = null,
                start7 = null, end7 = null, start8 = null, end8 = null,
                start9 = null, end9 = null, start10 = null, end10 = null,
                start11 = null, end11 = null, start12 = null, end12 = null,
                start13 = null, end13 = null, start14 = null, end14 = null,
                start15 = null, end15 = null, start16 = null, end16 = null,
                start17 = null, end17 = null, start18 = null, end18 = null,
                start19 = null, end19 = null, start20 = null, end20 = null,
                start21 = null, end21 = null, start22 = null, end22 = null,
                start23 = null, end23 = null, start24 = null, end24 = null,
                start25 = null, end25 = null, start26 = null, end26 = null,
                start27 = null, end27 = null, start28 = null, end28 = null,
                start29 = null, end29 = null, start30 = null, end30 = null;


        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            start1 = Calendars.fromDate(df.parse("25-04-2024"));
            end1 = Calendars.fromDate(df.parse("29-04-2024"));
            start2 = Calendars.fromDate(df.parse("01-05-2024"));
            end2 = Calendars.fromDate(df.parse("07-05-2024"));
            start3 = Calendars.fromDate(df.parse("08-05-2024"));
            end3 = Calendars.fromDate(df.parse("10-05-2024"));
            start4 = Calendars.fromDate(df.parse("11-05-2024"));
            end4 = Calendars.fromDate(df.parse("17-05-2024"));
            start5 = Calendars.fromDate(df.parse("25-05-2024"));
            end5 = Calendars.fromDate(df.parse("17-06-2024"));
            dateInterval1 = new DateInterval(start1, end5);

            start6 = Calendars.fromDate(df.parse("10-01-2024"));
            end6 = Calendars.fromDate(df.parse("13-01-2024"));
            start7 = Calendars.fromDate(df.parse("14-01-2024"));
            end7 = Calendars.fromDate(df.parse("17-01-2024"));
            start8 = Calendars.fromDate(df.parse("20-01-2024"));
            end8 = Calendars.fromDate(df.parse("21-01-2024"));
            start9 = Calendars.fromDate(df.parse("22-01-2024"));
            end9 = Calendars.fromDate(df.parse("24-01-2024"));
            start10 = Calendars.fromDate(df.parse("25-01-2024"));
            end10 = Calendars.fromDate(df.parse("30-01-2024"));
            dateInterval2 = new DateInterval(start6, end10);

            start11 = Calendars.fromDate(df.parse("10-02-2024"));
            end11 = Calendars.fromDate(df.parse("13-02-2024"));
            start12 = Calendars.fromDate(df.parse("14-02-2024"));
            end12 = Calendars.fromDate(df.parse("17-02-2024"));
            start13 = Calendars.fromDate(df.parse("20-02-2024"));
            end13 = Calendars.fromDate(df.parse("21-02-2024"));
            start14 = Calendars.fromDate(df.parse("22-02-2024"));
            end14 = Calendars.fromDate(df.parse("24-02-2024"));
            start15 = Calendars.fromDate(df.parse("25-02-2024"));
            end15 = Calendars.fromDate(df.parse("30-02-2024"));
            dateInterval3 = new DateInterval(start11, end15);

            start16 = Calendars.fromDate(df.parse("11-03-2024"));
            end16 = Calendars.fromDate(df.parse("14-03-2024"));
            start17 = Calendars.fromDate(df.parse("15-03-2024"));
            end17 = Calendars.fromDate(df.parse("18-03-2024"));
            start18 = Calendars.fromDate(df.parse("20-03-2024"));
            end18 = Calendars.fromDate(df.parse("22-03-2024"));
            start19 = Calendars.fromDate(df.parse("23-03-2024"));
            end19 = Calendars.fromDate(df.parse("26-03-2024"));
            start20 = Calendars.fromDate(df.parse("27-03-2024"));
            end20 = Calendars.fromDate(df.parse("28-03-2024"));
            dateInterval4 = new DateInterval(start16, end20);

            start21 = Calendars.fromDate(df.parse("12-03-2024"));
            end21 = Calendars.fromDate(df.parse("13-03-2024"));
            start22 = Calendars.fromDate(df.parse("16-03-2024"));
            end22 = Calendars.fromDate(df.parse("18-03-2024"));
            start23 = Calendars.fromDate(df.parse("21-03-2024"));
            end23 = Calendars.fromDate(df.parse("22-03-2024"));
            start24 = Calendars.fromDate(df.parse("25-03-2024"));
            end24 = Calendars.fromDate(df.parse("26-03-2024"));
            start25 = Calendars.fromDate(df.parse("27-03-2024"));
            end25 = Calendars.fromDate(df.parse("28-03-2024"));
            dateInterval5 = new DateInterval(start21, end25);

            start26 = Calendars.fromDate(df.parse("10-03-2024"));
            end26 = Calendars.fromDate(df.parse("11-03-2024"));
            start27 = Calendars.fromDate(df.parse("12-03-2024"));
            end27 = Calendars.fromDate(df.parse("18-03-2024"));
            start28 = Calendars.fromDate(df.parse("21-03-2024"));
            end28 = Calendars.fromDate(df.parse("23-03-2024"));
            start29 = Calendars.fromDate(df.parse("24-03-2024"));
            end29 = Calendars.fromDate(df.parse("25-03-2024"));
            start30 = Calendars.fromDate(df.parse("26-03-2024"));
            end30 = Calendars.fromDate(df.parse("27-03-2024"));
            dateInterval6 = new DateInterval(start26, end30);
        } catch (ParseException ignored) {

        }

        List<Phase> phases1 = new ArrayList<>();
        List<Phase> phases2 = new ArrayList<>();
        List<Phase> phases3 = new ArrayList<>();
        List<Phase> phases4 = new ArrayList<>();
        List<Phase> phases5 = new ArrayList<>();
        List<Phase> phases6 = new ArrayList<>();

        Phase application1 = new Phase(String.valueOf(PhaseTypeEnum.APPLICATION), "Candidates send applications.", start1, end1);
        Phase screening1 = new Phase(String.valueOf(PhaseTypeEnum.SCREENING), "Candidates screening.", start2, end2);
        Phase analysis1 = new Phase(String.valueOf(PhaseTypeEnum.ANALYSIS), "Candidates analysis.", start3, end3);
        Phase interview1 = new Phase(String.valueOf(PhaseTypeEnum.INTERVIEW), "Candidates get interviewed.", start4, end4);
        Phase result1 = new Phase(String.valueOf(PhaseTypeEnum.RESULTS), "Candidates get results.", start5, end5);

        Phase application2 = new Phase(String.valueOf(PhaseTypeEnum.APPLICATION), "Candidates send applications.", start6, end6);
        Phase screening2 = new Phase(String.valueOf(PhaseTypeEnum.SCREENING), "Candidates screening.", start7, end7);
        Phase analysis2 = new Phase(String.valueOf(PhaseTypeEnum.ANALYSIS), "Candidates analysis.", start8, end8);
        Phase interview2 = new Phase(String.valueOf(PhaseTypeEnum.INTERVIEW), "Candidates get interviewed.", start9, end9);
        Phase result2 = new Phase(String.valueOf(PhaseTypeEnum.RESULTS), "Candidates get results.", start10, end10);


        Phase application3 = new Phase(String.valueOf(PhaseTypeEnum.APPLICATION), "Candidates send applications.", start11, end11);
        Phase screening3 = new Phase(String.valueOf(PhaseTypeEnum.SCREENING), "Candidates screening.", start12, end12);
        Phase analysis3 = new Phase(String.valueOf(PhaseTypeEnum.ANALYSIS), "Candidates analysis.", start13, end13);
        Phase interview3 = new Phase(String.valueOf(PhaseTypeEnum.INTERVIEW), "Candidates get interviewed.", start14, end14);
        Phase result3 = new Phase(String.valueOf(PhaseTypeEnum.RESULTS), "Candidates get results.", start15, end15);

        Phase application4 = new Phase(String.valueOf(PhaseTypeEnum.APPLICATION), "Candidates send applications.", start16, end16);
        Phase screening4 = new Phase(String.valueOf(PhaseTypeEnum.SCREENING), "Candidates screening.", start17, end17);
        Phase analysis4 = new Phase(String.valueOf(PhaseTypeEnum.ANALYSIS), "Candidates analysis.", start18, end18);
        Phase interview4 = new Phase(String.valueOf(PhaseTypeEnum.INTERVIEW), "Candidates get interviewed.", start19, end19);
        Phase result4 = new Phase(String.valueOf(PhaseTypeEnum.RESULTS), "Candidates get results.", start20, end20);

        Phase application5 = new Phase(String.valueOf(PhaseTypeEnum.APPLICATION), "Candidates send applications.", start21, end21);
        Phase screening5 = new Phase(String.valueOf(PhaseTypeEnum.SCREENING), "Candidates screening.", start22, end22);
        Phase analysis5 = new Phase(String.valueOf(PhaseTypeEnum.ANALYSIS), "Candidates analysis.", start23, end23);
        Phase interview5 = new Phase(String.valueOf(PhaseTypeEnum.INTERVIEW), "Candidates get interviewed.", start24, end24);
        Phase result5 = new Phase(String.valueOf(PhaseTypeEnum.RESULTS), "Candidates get results.", start25, end25);

        Phase application6 = new Phase(String.valueOf(PhaseTypeEnum.APPLICATION), "Candidates send applications.", start26, end26);
        Phase screening6 = new Phase(String.valueOf(PhaseTypeEnum.SCREENING), "Candidates screening.", start27, end27);
        Phase analysis6 = new Phase(String.valueOf(PhaseTypeEnum.ANALYSIS), "Candidates analysis.", start28, end28);
        Phase interview6 = new Phase(String.valueOf(PhaseTypeEnum.INTERVIEW), "Candidates get interviewed.", start29, end29);
        Phase result6 = new Phase(String.valueOf(PhaseTypeEnum.RESULTS), "Candidates get results.", start30, end30);


        phases1.add(application1);
        phases1.add(screening1);
        phases1.add(interview1);
        phases1.add(analysis1);
        phases1.add(result1);

        phases2.add(application2);
        phases2.add(screening2);
        phases2.add(interview2);
        phases2.add(analysis2);
        phases2.add(result2);

        phases3.add(application3);
        phases3.add(screening3);
        phases3.add(interview3);
        phases3.add(analysis3);
        phases3.add(result3);

        phases4.add(application4);
        phases4.add(screening4);
        phases4.add(interview4);
        phases4.add(analysis4);
        phases4.add(result4);

        phases5.add(application5);
        phases5.add(screening5);
        phases5.add(interview5);
        phases5.add(analysis5);
        phases5.add(result5);

        phases6.add(application6);
        phases6.add(screening6);
        phases6.add(interview6);
        phases6.add(analysis6);
        phases6.add(result6);

        RecruitmentProcess recruitmentProcess1 = new RecruitmentProcess(dateInterval1.start(), dateInterval1.end(), phases1, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.ANALYSIS)));

        RecruitmentProcess recruitmentProcess2 = new RecruitmentProcess(dateInterval2.start(), dateInterval2.end(), phases2, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.SCREENING)));

        RecruitmentProcess recruitmentProcess3 = new RecruitmentProcess(dateInterval3.start(), dateInterval3.end(), phases3, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.PLANNED)));

        RecruitmentProcess recruitmentProcess4 = new RecruitmentProcess(dateInterval4.start(), dateInterval4.end(), phases4, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.INTERVIEW)));

        RecruitmentProcess recruitmentProcess5 = new RecruitmentProcess(dateInterval5.start(), dateInterval5.end(), phases5, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.RESULTS)));

        RecruitmentProcess recruitmentProcess6 = new RecruitmentProcess(dateInterval6.start(), dateInterval6.end(), phases6, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.RESULTS)));

        RecruitmentProcess recruitmentProcess7 = new RecruitmentProcess(dateInterval2.start(), dateInterval2.end(), phases2, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.ANALYSIS)));

        RecruitmentProcess recruitmentProcess8 = new RecruitmentProcess(dateInterval1.start(), dateInterval1.end(), phases1, new RecruitmentProcessStatus(String.valueOf(RecruitmentProcessStatusEnum.INTERVIEW)));

        recruitmentProcess1 = recruitmentProcessRepository.save(recruitmentProcess1);
        recruitmentProcess2 = recruitmentProcessRepository.save(recruitmentProcess2);
        recruitmentProcess3 = recruitmentProcessRepository.save(recruitmentProcess3);
        recruitmentProcess4 = recruitmentProcessRepository.save(recruitmentProcess4);
        recruitmentProcess5 = recruitmentProcessRepository.save(recruitmentProcess5);
        recruitmentProcess6 = recruitmentProcessRepository.save(recruitmentProcess6);
        recruitmentProcess7 = recruitmentProcessRepository.save(recruitmentProcess7);
        recruitmentProcess8 = recruitmentProcessRepository.save(recruitmentProcess8);

        recruitmentProcessList.add(recruitmentProcess1);
        recruitmentProcessList.add(recruitmentProcess2);
        recruitmentProcessList.add(recruitmentProcess3);
        recruitmentProcessList.add(recruitmentProcess4);
        recruitmentProcessList.add(recruitmentProcess5);
        recruitmentProcessList.add(recruitmentProcess6);
        recruitmentProcessList.add(recruitmentProcess7);
        recruitmentProcessList.add(recruitmentProcess8);
    }


    private void persistJobOpenings() {
        ContractTypeDTO contract = new ContractTypeDTO("full-time");
        WorkModeDTO mode = new WorkModeDTO("remote");
        String description = "Night Guard.";

        JobReference jobReference = new JobReference("ISEP", 0);
        JobReference jobReference1 = new JobReference("ISEP", 1);
        JobReference jobReference2 = new JobReference("ISEP", 2);
        JobReference jobReference3 = new JobReference("ISEP", 3);
        JobReference jobReference4 = new JobReference("ISEP", 4);
        JobReference jobReference5 = new JobReference("ISEP", 5);
        JobReference jobReference6 = new JobReference("AVIP", 0);
        JobReference jobReference7 = new JobReference("ISEP", 6);
        JobReference jobReference8 = new JobReference("ISEP", 7);

        JobOpening jobOpening1 = new JobOpening("Front End Junior Developer", contract, mode, "123 Main Street",
                "Flagtown", "Star District", "USA", "4500-900", 2, description,
                requirementSpecificationsList.get(0), interviewModelsList.get(0), jobReference);

        JobOpening jobOpening2 = new JobOpening("Back End Senior Developer", contract, mode, "456 Elm Street",
                "Maple Town", "Moonlight District", "Canada", "4500-900", 15, description,
                requirementSpecificationsList.get(1), interviewModelsList.get(1), jobReference1);

        JobOpening jobOpening3 = new JobOpening("Back End Senior Developer", contract, mode, "MM Street",
                "MM Town", "MM District", "MMM", "4500-900", 8, description,
                requirementSpecificationsList.get(0), interviewModelsList.get(0), jobReference2);

        JobOpening jobOpening4 = new JobOpening("Night Guard", contract, mode, "Freddy fazbear Street",
                "Chica Town", "Foxy District", "MMM", "4500-900", 7, description,
                requirementSpecificationsList.get(0), interviewModelsList.get(0), jobReference3);

        JobOpening jobOpening5 = new JobOpening("Front End Senior Developer", contract, mode, "Test Street",
                "Test Town", "Test District", "Portugal", "4500-900", 4, description,
                requirementSpecificationsList.get(1), interviewModelsList.get(1), jobReference4);

        JobOpening jobOpening6 = new JobOpening("Front End Junior Developer", contract, mode, "Second Street",
                "Second Town", "Second District", "Second", "4500-910", 9, description,
                requirementSpecificationsList.get(1), interviewModelsList.get(1), jobReference5);

        JobOpening jobOpening7 = new JobOpening("Back End Senior Developer", contract, mode, "Third Street",
                "Third Town", "Third District", "Third", "4510-910", 1, description,
                requirementSpecificationsList.get(0), interviewModelsList.get(0), jobReference6);

        JobOpening jobOpening8 = new JobOpening("Front End Senior Developer", contract, mode, "Third Street",
                "New Town", "New District", "New Dream", "4520-920", 3, description,
                requirementSpecificationsList.get(1), interviewModelsList.get(1), jobReference7);

        JobOpening jobOpening9 = new JobOpening("Back End Junior Developer", contract, mode, "Flower Street",
                "Sun Town", "Sea District", "Fever Sun", "4560-910", 5, description,
                requirementSpecificationsList.get(0), interviewModelsList.get(0), jobReference8);

        jobOpening1.updateStatusToNotStarted();
        jobOpening2.updateStatusToNotStarted();
        jobOpening3.updateStatusToNotStarted();
        jobOpening5.updateStatusToNotStarted();
        jobOpening6.updateStatusToNotStarted();
        jobOpening7.updateStatusToNotStarted();
        jobOpening8.updateStatusToNotStarted();
        jobOpening9.updateStatusToNotStarted();


        jobOpening1 = jobOpeningRepository.save(jobOpening1);
        jobOpening2 = jobOpeningRepository.save(jobOpening2);
        jobOpening3 = jobOpeningRepository.save(jobOpening3);
        jobOpeningRepository.save(jobOpening4);
        jobOpening5 = jobOpeningRepository.save(jobOpening5);
        jobOpening6 = jobOpeningRepository.save(jobOpening6);
        jobOpening7 = jobOpeningRepository.save(jobOpening7);
        jobOpening8 = jobOpeningRepository.save(jobOpening8);
        jobOpening9 = jobOpeningRepository.save(jobOpening9);


        List<JobOpening> jobOpenings = new ArrayList<>();

        jobOpenings.add(jobOpening1);
        jobOpenings.add(jobOpening2);
        jobOpenings.add(jobOpening3);
        jobOpenings.add(jobOpening5);
        jobOpenings.add(jobOpening6);
        jobOpenings.add(jobOpening7);
        jobOpenings.add(jobOpening8);
        jobOpenings.add(jobOpening9);

        for (int i = 0; i < 3; i++) {
            jobOpenings.get(i).addRecruitmentProcess(recruitmentProcessList.get(i));
            jobOpenings.get(i).updateStatusToNotStarted();
            recruitmentProcessList.get(i).referToJobOpening(jobOpenings.get(i));
            jobOpeningRepository.save(jobOpenings.get(i));
            recruitmentProcessRepository.save(recruitmentProcessList.get(i));
        }

        JobOpening jobOpening = jobOpenings.get(0);
        jobOpening.updateStatusToStarted();
        jobOpeningRepository.save(jobOpening);

        jobOpening = jobOpenings.get(1);
        jobOpening.updateStatusToStarted();
        jobOpeningRepository.save(jobOpening);

        for (int j = 3; j < 8; j++) {
            jobOpenings.get(j).addRecruitmentProcess(recruitmentProcessList.get(j));
            jobOpenings.get(j).updateStatusToNotStarted();
            recruitmentProcessList.get(j).referToJobOpening(jobOpenings.get(j));
            jobOpeningRepository.save(jobOpenings.get(j));
            recruitmentProcessRepository.save(recruitmentProcessList.get(j));
            jobOpenings.get(j).updateStatusToStarted();
            jobOpeningRepository.save(jobOpenings.get(j));
        }

        jobOpeningList.add(jobOpening1);
        jobOpeningList.add(jobOpening2);
        jobOpeningList.add(jobOpening3);
        jobOpeningList.add(jobOpening4);
        jobOpeningList.add(jobOpening5);
        jobOpeningList.add(jobOpening6);
        jobOpeningList.add(jobOpening7);
        jobOpeningList.add(jobOpening8);
        jobOpeningList.add(jobOpening9);
    }

    private void persistRequirementSpecifications() {
        String description1 = "Back-End Developer With Experience in Java";
        String name1 = "Requirements_Back_End_Dev";
        String fullClassName1 = "jobs4u.plugin.core.adapter.RequirementPluginAdapter";
        String dataImporter = "jobs4u.plugin.core.adapter.FileManagementAdapter";
        RequirementSpecification requirementSpecification1 = new RequirementSpecification(name1, description1, fullClassName1, "plugins-config-file/requirement/r-config-1.txt", dataImporter);

        String description2 = "Front-End Developer With Experience in Java";
        String name2 = "Requirements_Front_End_Dev";
        String fullClassName2 = "jobs4u.plugin.core.adapter.RequirementPluginAdapter";
        RequirementSpecification requirementSpecification2 = new RequirementSpecification(name2, description2, fullClassName2, "plugins-config-file/requirement/r-config-2.txt", dataImporter);

        requirementSpecificationsList.add(requirementSpecification1);
        requirementSpecificationsList.add(requirementSpecification2);
        requirementSpecificationRepository.save(requirementSpecification1);
        requirementSpecificationRepository.save(requirementSpecification2);
    }

    private void persistInterviewModels() {
        String description1 = "Back-End Developer With Experience in Java";
        String name1 = "Interview_Back_End_Dev";
        String fullClassName1 = "jobs4u.plugin.core.adapter.InterviewPluginAdapter";
        String dataImporter = "jobs4u.plugin.core.adapter.FileManagementAdapter";
        InterviewModel interviewModel1 = new InterviewModel(name1, description1, fullClassName1, "plugins-config-file/interview/i-config-1.txt", dataImporter);


        String description2 = "Front-End Developer With Experience in Java";
        String name2 = "Interview_Front_End_Dev";
        String fullClassName2 = "jobs4u.plugin.core.adapter.InterviewPluginAdapter";
        InterviewModel interviewModel2 = new InterviewModel(name2, description2, fullClassName2, "plugins-config-file/interview/i-config-2.txt", dataImporter);

        interviewModelsList.add(interviewModel1);
        interviewModelsList.add(interviewModel2);

        interviewModelRepository.save(interviewModel1);
        interviewModelRepository.save(interviewModel2);
    }

    private void persistCustomers() {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.CUSTOMER_MANAGER);
        Address address = new Address("StreetLane", "City Garden", "District 9", "14th", "4590-890");

        SystemUser customerManager = registerUser("customerM2@email.com", "Password-1", "Joana", "Cash", roles);
        customerManagementService.registerNewCustomer("Instituto Superior de Engenharia do Porto", address.toString(),
                "ISEP", customerManager, "isep@email.com");

        SystemUser customerManager2 = registerUser("1220841@isep.ipp.pt", "Password-1", "Rita", "Barbosa", roles);
        customerManagementService.registerNewCustomer("Ana VIP", address.toString(),
                "AVIP", customerManager2, "1221933@isep.ipp.pt");
    }

    private void persistCandidates() {
        final Set<Role> roles = new HashSet<>();
        roles.add(BaseRoles.CANDIDATE_USER);
        PhoneNumber phoneNumber = new PhoneNumber("+351", "910000034");
        candidateManagementService.registerCandidate("Joana", "candidate@email.com", phoneNumber);

        PhoneNumber phoneNumber2 = new PhoneNumber("+351", "910000000");
        candidateManagementService.registerCandidate("Matilde", "1220683@isep.ipp.pt", phoneNumber2);

        PhoneNumber phoneNumber3 = new PhoneNumber("+351", "910000001");
        candidateManagementService.registerCandidate("José", "1220738@isep.ipp.pt", phoneNumber3);
        PhoneNumber phoneNumber4 = new PhoneNumber("+351", "910095800");

        candidateManagementService.registerCandidate("Vitor", "1211273@isep.ipp.pt", phoneNumber4);
    }

    private void persistWorkModes() {
        WorkMode workMode = WorkMode.valueOf("remote");
        WorkMode workMode1 = WorkMode.valueOf("hybrid");
        WorkMode workMode2 = WorkMode.valueOf("onsite");

        workModeRepository.save(workMode);
        workModeRepository.save(workMode1);
        workModeRepository.save(workMode2);
    }

    private void persistContractTypes() {
        ContractType contractType = ContractType.valueOf("full-time");
        ContractType contractType1 = ContractType.valueOf("part-time");

        contractTypeRepository.save(contractType);
        contractTypeRepository.save(contractType1);
    }

    private void persistApplications() {
        RequirementResult requirementResult = RequirementResult.valueOf(true);


        ApplicationFile file1 = new ApplicationFile(new File("output/candidate1/1-big-file-1.txt"));
        ApplicationFile file2 = new ApplicationFile(new File("output/candidate1/1-candidate-data.txt"));
        ApplicationFile file3 = new ApplicationFile(new File("output/candidate1/1-cv.txt"));
        ApplicationFile file4 = new ApplicationFile(new File("output/candidate1/1-email.txt"));
        ApplicationFile file5 = new ApplicationFile(new File("output/candidate1/1-report-1.txt"));
        Set<ApplicationFile> files1 = new HashSet<>();
        files1.add(file1);
        files1.add(file2);
        files1.add(file3);
        files1.add(file4);
        files1.add(file5);

        ApplicationFile file6 = new ApplicationFile(new File("output/candidate2/2-letter.txt"));
        ApplicationFile file7 = new ApplicationFile(new File("output/candidate2/2-candidate-data.txt"));
        ApplicationFile file8 = new ApplicationFile(new File("output/candidate2/2-cv.txt"));
        ApplicationFile file9 = new ApplicationFile(new File("output/candidate2/2-email.txt"));
        Set<ApplicationFile> files2 = new HashSet<>();
        files2.add(file6);
        files2.add(file7);
        files2.add(file8);
        files2.add(file9);

        Date date = new Date(2024 - 1900, Calendar.JANUARY, 5);

        RequirementAnswer requirementAnswer1 = RequirementAnswer.valueOf("plugins-config-file/requirement/r-answer-1.txt");
        RequirementResult requirementResult1 = RequirementResult.valueOf(false);
        Date date1 = new Date(2024 - 1900, Calendar.JANUARY, 6);

        InterviewAnswer interviewAnswer1 = InterviewAnswer.valueOf("plugins-config-file/interview/i-answer-1.txt");
        Interview interview1 = new Interview("interview1", new Date(2024 - 1900, Calendar.MARCH, 3),
                new InterviewResult(60, "errors in questions 5 and 7."), interviewAnswer1);

        RequirementAnswer requirementAnswer2 = RequirementAnswer.valueOf("plugins-config-file/requirement/r-answer-2.txt");
        RequirementResult requirementResult2 = RequirementResult.valueOf(false);
        Date date2 = new Date(2024 - 1900, Calendar.JANUARY, 8);
        InterviewAnswer interviewAnswer2 = InterviewAnswer.valueOf("plugins-config-file/interview/i-answer-2.txt");
        Interview interview2 = new Interview("interview2", new Date(2024 - 1900, Calendar.MARCH, 4),
                new InterviewResult(80, "error in question 3."), interviewAnswer2);

        Interview interview3 = new Interview("interview3", new Date(2024 - 1900, Calendar.MARCH, 4),
                new InterviewResult(22, "errors in questions 1, 2, 4 and 6."), interviewAnswer2);

        Interview interview4 = new Interview("interview4", new Date(2024 - 1900, Calendar.MARCH, 5));

        ApplicationFile file10 = new ApplicationFile(new File("output/candidate3/example3.txt"));
        Set<ApplicationFile> files3 = new HashSet<>();
        files3.add(file10);

        Date date3 = new Date(2024 - 1900, Calendar.JANUARY, 10);


        ApplicationFile file11 = new ApplicationFile(new File("output/candidate4/example4.txt"));
        Set<ApplicationFile> files4 = new HashSet<>();
        files4.add(file11);

        Date date4 = new Date(2024 - 1900, Calendar.JANUARY, 12);
        ApplicationFile file12 = new ApplicationFile(new File("output/candidate5/example5.txt"));
        Set<ApplicationFile> files5 = new HashSet<>();
        files5.add(file12);

        ApplicationFile file13 = new ApplicationFile(new File("output/candidate6/example6.txt"));
        Set<ApplicationFile> files6 = new HashSet<>();
        files6.add(file13);

        Date date5 = new Date(2024 - 1900, Calendar.JANUARY, 12);


        Application application, application1, application2, application3, application4, application5, application6, application7, application8,
                application9, application10, application11, application12, application13;
        PhoneNumber phone = new PhoneNumber("+351", "910000000");
        Optional<Candidate> candidate1 = candidateRepository.findByPhoneNumber(phone);
        PhoneNumber phone1 = new PhoneNumber("+351", "910000001");
        Optional<Candidate> candidate2 = candidateRepository.findByPhoneNumber(phone1);
        Optional<Candidate> c4 = candidateRepository.findByPhoneNumber(new PhoneNumber("+351", "910095800"));


        if (candidate1.isPresent() && candidate2.isPresent() && c4.isPresent()) {
            Candidate candidate = candidate1.get();
            Candidate candidate3 = candidate2.get();
            Candidate candidate4 = c4.get();
            application = new Application(requirementAnswer1, requirementResult, files1, date, candidate3, interview1);
            application1 = new Application(requirementAnswer2, requirementResult, files2, date1, candidate, interview2);
            application2 = new Application(requirementAnswer1, requirementResult, files3, date2, candidate4, interview3);
            application3 = new Application(requirementAnswer1, requirementResult, files4, date3, candidate, interview1);
            application4 = new Application(requirementAnswer1, requirementResult, files5, date4, candidate3, interview4);
            application5 = new Application(requirementAnswer1, requirementResult1, files6, date5, candidate3, interview1);
            application6 = new Application(requirementAnswer1, requirementResult1, files2, date1, candidate, interview1);
            application7 = new Application(requirementAnswer1, requirementResult, files3, date2, candidate3, interview1);
            application8 = new Application(requirementAnswer2, requirementResult, files4, date3, candidate, interview3);
            application9 = new Application(requirementAnswer2, requirementResult, files1, date, candidate4, interview2);
            application10 = new Application(requirementAnswer2, requirementResult2, files5, date4, candidate, interview2);
            application11 = new Application(requirementAnswer2, requirementResult, files4, date3, candidate, interview3);
            application12 = new Application(requirementAnswer1, requirementResult, files2, date1, candidate4, interview1);
            application13 = new Application(requirementAnswer1, requirementResult, files2, date1, candidate4, interview4);

        } else {
            application = new Application(requirementAnswer1, requirementResult, files1, date, interview1);
            application1 = new Application(requirementAnswer2, requirementResult, files2, date1, interview2);
            application2 = new Application(requirementAnswer1, requirementResult, files3, date2, interview3);
            application3 = new Application(requirementAnswer1, requirementResult, files4, date3, interview1);
            application4 = new Application(requirementAnswer1, requirementResult, files5, date4, interview4);
            application5 = new Application(requirementAnswer1, requirementResult1, files6, date5, interview1);
            application6 = new Application(requirementAnswer1, requirementResult1, files2, date1, interview1);
            application7 = new Application(requirementAnswer1, requirementResult, files3, date2, interview1);
            application8 = new Application(requirementAnswer2, requirementResult, files4, date3, interview3);
            application9 = new Application(requirementAnswer2, requirementResult, files1, date, interview2);
            application10 = new Application(requirementAnswer2, requirementResult2, files5, date4, interview2);
            application11 = new Application(requirementAnswer2, requirementResult, files4, date3, interview3);
            application12 = new Application(requirementAnswer1, requirementResult, files2, date1, interview1);
            application13 = new Application(requirementAnswer1, requirementResult, files2, date1, interview4);

        }

        Set<Application> applicationsSet = new HashSet<>();
        applicationsSet.add(application);
        applicationsSet.add(application1);
        applicationsSet.add(application2);

        Set<Application> applicationsSet1 = new HashSet<>();
        applicationsSet1.add(application3);

        Set<Application> applicationsSet2 = new HashSet<>();
        applicationsSet2.add(application4);

        Set<Application> applicationsSet3 = new HashSet<>();
        applicationsSet3.add(application5);
        applicationsSet3.add(application6);

        Set<Application> applicationsSet4 = new HashSet<>();
        application8.applicationStatus().updateStatusDescriptionAsREJECTED();
        application9.applicationStatus().updateStatusDescriptionAsACCEPTED();
        application7.applicationStatus().updateStatusDescriptionAsACCEPTED();
        applicationsSet4.add(application8);
        applicationsSet4.add(application9);
        applicationsSet4.add(application7);

        Set<Application> applicationsSet5 = new HashSet<>();
        applicationsSet5.add(application10);

        Set<Application> applicationsSet6 = new HashSet<>();
        applicationsSet6.add(application11);
        applicationsSet6.add(application12);

        Set<Application> applicationSet7 = new HashSet<>();
        applicationSet7.add(application13);

        Set<Application> applicationsSet8 = new HashSet<>();
        applicationsSet8.add(application1);


        List<JobOpening> jobs = new ArrayList<>();
        for (JobOpening job : jobOpeningRepository.findAll()) {
            jobs.add(job);
        }
        jobs.get(0).setApplications(applicationsSet);
        jobOpeningRepository.save(jobs.get(0));
        jobs.get(1).setApplications(applicationsSet5);
        jobOpeningRepository.save(jobs.get(1));
        jobs.get(2).setApplications(applicationsSet1);
        jobOpeningRepository.save(jobs.get(2));
        jobs.get(3).setApplications(applicationsSet8);
        jobOpeningRepository.save(jobs.get(3));
        jobs.get(4).setApplications(applicationsSet2);
        jobOpeningRepository.save(jobs.get(4));
        jobs.get(5).setApplications(applicationsSet4);
        jobOpeningRepository.save(jobs.get(5));
        jobs.get(6).setApplications(applicationsSet3);
        jobOpeningRepository.save(jobs.get(6));
        jobs.get(7).setApplications(applicationsSet6);
        jobOpeningRepository.save(jobs.get(7));
        jobs.get(8).setApplications(applicationSet7);
        jobOpeningRepository.save(jobs.get(8));
    }
}


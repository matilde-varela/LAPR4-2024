package jobs4u.base.applicationmanagement.application;

import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.applicationmanagement.repositories.ApplicationRepository;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.application.JobOpeningManagementService;
import jobs4u.base.jobopeningmanagement.domain.JobOpening;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.usermanagement.domain.BaseRoles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleInterviewController {
    private final AuthorizationService authorizationService = AuthzRegistry.authorizationService();
    private final JobOpeningManagementService jobOpeningManagementService= new JobOpeningManagementService();
    private final ApplicationManagementService applicationManagementService= new ApplicationManagementService();
    private final ApplicationRepository applicationRepository = PersistenceContext.repositories().applications();

    public Iterable<JobOpeningDTO> getJobOpeningsList() {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);

        return jobOpeningManagementService.activeJobOpenings();
    }

    public Iterable<ApplicationDTO> getApplicationList(JobOpeningDTO jobOpeningDto) {
        authorizationService.ensureAuthenticatedUserHasAnyOf(BaseRoles.CUSTOMER_MANAGER, BaseRoles.ADMIN);
        JobOpening jobOpening= jobOpeningManagementService.getJobOpening(jobOpeningDto);
        return applicationManagementService.getApplicationsList(jobOpening);
    }

    public boolean updateApplication(ApplicationDTO applicationDto, String date, String time) throws ParseException {
        Application application = applicationManagementService.getApplication(applicationDto);
        Date newSchedule = parseData(date,time);
        if (application!=null){
            application.updateApplicationSchedule(newSchedule);
            applicationRepository.save(application);
            return true;
        }
        return false;
    }

    public static Date parseData(String date, String hour) throws ParseException {
        // Split date in day, month and year
        String[] partesData = date.split("[/-]");
        int year = Integer.parseInt(partesData[0]);
        int month = Integer.parseInt(partesData[1]);
        int day = Integer.parseInt(partesData[2]);

        // Split hour
        String[] partesHora = hour.split(":");
        int hour1 = Integer.parseInt(partesHora[0]);
        int minute = Integer.parseInt(partesHora[1]);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.parse(year + "-" + month + "-" + day + " " + hour1 + ":" + minute + ":00");
    }
}

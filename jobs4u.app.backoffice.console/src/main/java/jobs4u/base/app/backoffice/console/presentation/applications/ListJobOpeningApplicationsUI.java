package jobs4u.base.app.backoffice.console.presentation.applications;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.applicationmanagement.application.ListJobOpeningApplicationsController;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

import java.util.List;
import java.util.NoSuchElementException;


public class ListJobOpeningApplicationsUI extends AbstractUI{

    private static final ListJobOpeningApplicationsController controller = new ListJobOpeningApplicationsController();

    @Override
    protected boolean doShow() {

        try{
        int index = 0;
            List<JobOpeningDTO>  jobOpeningListDTO = controller.getJobOpeningsList();

        for (JobOpeningDTO jobOpening: jobOpeningListDTO) {
            System.out.printf("INDEX: %d", index);
            String sb = "==================================================================\n" +
                    "[Job Reference] " + jobOpening.getJobReference() + "\n" +
                    "[Status] " + jobOpening.getStatus() + "\n" +
                    "[Function] " + jobOpening.getFunction() + "\n" +
                    "[Description] " + jobOpening.getDescription() + "\n" +
                    "[Contract Type] " + jobOpening.getContractType() + "\n" +
                    "[Work Mode] " + jobOpening.getWorkMode() + "\n" +
                    "[Address] " + jobOpening.getAddress() + "\n" +
                    "[Customer Code] " + jobOpening.getCustomerCode() + "\n" +
                    "[Number of Vacancies] " + jobOpening.getNumVacancies() + "\n" +
                    "[Requirement Specification] " + jobOpening.getRequirementName() + "\n" +
                    "=====================================================================";
            System.out.println(sb);
            index++;
        }

            int option = selectsIndexNoCancel(jobOpeningListDTO);

            JobOpeningDTO job = jobOpeningListDTO.get(option);

            List<ApplicationDTO> applicationListDTO = controller.getApplicationsList(job);

            if (applicationListDTO.isEmpty()){
                System.out.println("Don't exist applications for the job opening chosen!");
            }

            for (ApplicationDTO app : applicationListDTO) {
                System.out.println(app.toString());
            }

        } catch (NoSuchElementException | IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static int selectsIndexNoCancel(List<?> list) {
        String input;
        int value;
        do {
            input = Console.readNonEmptyLine("Select the job opening index:", "Providing a job opening option is obligatory.");

            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
        } while (value < 0 || value > list.size() - 1);
        return value;
    }

    @Override
    public String headline() {
        return "List Job Opening Applications";
    }

}

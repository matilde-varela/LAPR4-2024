package jobs4u.base.app.backoffice.console.presentation.operator;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.applicationmanagement.application.RegisterJobOpeningApplicationController;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.domain.ApplicationFile;
import jobs4u.base.candidatemanagement.domain.PhoneNumber;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RegisterJobOpeningApplicationUI extends AbstractUI {

    private static final RegisterJobOpeningApplicationController controller = new RegisterJobOpeningApplicationController();
    @Override
    protected boolean doShow() {
        final String sharedFolderPath = Console.readNonEmptyLine("Shared Folder", "Please enter the shared folder");

        File folder = new File(sharedFolderPath);

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


        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.getName().endsWith("-candidate-data.txt")) {
                        try (Scanner scanner = new Scanner(file)) {
                            String jobReference = scanner.nextLine();
                            String email = scanner.nextLine();
                            String name = scanner.nextLine();
                            String phoneNumber = scanner.nextLine();

                            PhoneNumber number = new PhoneNumber("+351", phoneNumber);

                            boolean candidateExists = controller.getCandidate(number);
                            if (!candidateExists){
                                boolean validate = controller.registerCandidate(name, email, "+351", phoneNumber);
                            }

                            Set<ApplicationFile> applicationFiles = new HashSet<>();

                            for (File otherFile : files) {
                                applicationFiles.add(new ApplicationFile(otherFile));
                            }

                            try {
                                Optional<Application> application = controller.registerApplication(applicationFiles, new Date(), number, job);
                                if (application.isEmpty()) {
                                    throw new IntegrityViolationException();
                                } else {
                                    System.out.println("The application was successfully registered!\n");
                                }
                            } catch (final IntegrityViolationException | ConcurrencyException e) {
                                System.out.println("An error occurred while registering the job opening.\n" + e.getMessage());
                            }

                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }
        } else{
            System.out.println("Invalid directory: " + sharedFolderPath);
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
        return "Register a Job Application";
    }

}

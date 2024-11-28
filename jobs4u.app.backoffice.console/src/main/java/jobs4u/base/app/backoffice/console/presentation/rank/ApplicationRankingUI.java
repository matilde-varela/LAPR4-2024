package jobs4u.base.app.backoffice.console.presentation.rank;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.rankmanagement.application.ApplicationRankingController;
import jobs4u.base.applicationmanagement.domain.ApplicationFile;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.rankmanagement.dto.RankOrderDTO;


import java.util.ArrayList;
import java.util.List;

public class ApplicationRankingUI extends AbstractUI {

    private final ApplicationRankingController controller = new ApplicationRankingController();

    @Override
    protected boolean doShow() {
        boolean canLeaveFunctionality = false;
        String chosenOption;
        SelectWidget<ApplicationDTO> applicationSelect;

        try {
            JobOpeningDTO jobOpeningDTO = showAndSelectJobOpening();

            applicationSelect = new SelectWidget<>("Select an Application", controller.getApplications(jobOpeningDTO));

            do {
                chosenOption = showFunctionalityOptions();

                if (!chosenOption.equals("Exit functionality")) {
                    executeFunctionalityOption(chosenOption, jobOpeningDTO, applicationSelect);
                } else {
                    canLeaveFunctionality = controller.checkNeededApplicationsAreRanked(jobOpeningDTO);
                    if (!canLeaveFunctionality) {
                        System.out.println("You haven't ranked enough applications.\n");
                    }
                }
            } while (!canLeaveFunctionality);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    private void executeFunctionalityOption(String chosenOption, JobOpeningDTO jobOpeningDTO, SelectWidget<ApplicationDTO> applicationSelect) {
        switch (chosenOption) {
            case "Update Ranking":
                applicationSelect.show();
                ApplicationDTO applicationDTO = applicationSelect.selectedElement();
                String option;
                do {
                    option = showApplicationOptions();
                    executeApplicationOption(option, jobOpeningDTO, applicationDTO);
                } while (!option.equals("Exit application"));

                break;

            case "See Rank":
                System.out.println("===========================================================");
                System.out.printf("                        RANK (%s)                         \n", jobOpeningDTO.getJobReference());
                System.out.println("===========================================================");
                System.out.printf("--> Number of Rank Orders to be saved : %d\n", calculateNumberRankOrders(jobOpeningDTO));
                System.out.println("[FORMULA] vacancies + (vacancies * system_configuration)");
                System.out.println("===========================================================");
                Iterable<RankOrderDTO> rankOrderDTOS = controller.getRankOrderList(jobOpeningDTO);
                if (!rankOrderDTOS.iterator().hasNext()) {
                    System.out.println("This job opening does not have a rank yet.\n");
                } else {
                    showRank(rankOrderDTOS);
                }
                break;
        }
    }

    private int calculateNumberRankOrders(JobOpeningDTO jobOpeningDTO) {
        int numVacancies = jobOpeningDTO.getNumVacancies();
        double system_config = controller.getRankSystemConfig();
        return (int) (numVacancies + (numVacancies * system_config));
    }

    private void executeApplicationOption(String chosenOption, JobOpeningDTO jobOpeningDTO, ApplicationDTO applicationDTO) {
        switch (chosenOption) {
            case "See Application Files":
                if (applicationDTO != null) {
                    SelectWidget<String> files = new SelectWidget<>("Select the file you want to see:", listOfApplicationFilesNames(applicationDTO));
                    files.show();
                    String filename = files.selectedElement();
                    if (filename != null) {
                        seeApplicationFileInWindow(applicationDTO, filename);
                    }
                } else {
                    System.out.println("You need to choose an application first.\n");
                }
                break;

            case "Update Application's Rank":
                if (applicationDTO != null) {
                    int order;
                    do{
                       order  = Console.readInteger("Provide the rank order you want to put this application on:");
                    }while (order <= 0);
                    controller.updateRanking(jobOpeningDTO, applicationDTO, order);
                } else {
                    System.out.println("You need to choose an application first.\n");
                }
                break;
        }
    }

    private void showRank(Iterable<RankOrderDTO> rankOrderDTOS) {
        for (RankOrderDTO rankOrderDTO : rankOrderDTOS) {
            System.out.println(rankOrderDTO);
        }
    }

    private String showApplicationOptions() {
        List<String> optionsList = new ArrayList<>();
        optionsList.add("See Application Files");
        optionsList.add("Update Application's Rank");
        SelectWidget<String> options = new SelectWidget<>("Application Options:", optionsList);
        options.show();
        String selectedOption = options.selectedElement();
        if (selectedOption == null) {
            return "Exit application";
        }
        return selectedOption;
    }

    private String showFunctionalityOptions() {
        List<String> optionsList = new ArrayList<>();
        optionsList.add("See Rank");
        optionsList.add("Update Ranking");
        SelectWidget<String> options = new SelectWidget<>("Functionality Options:", optionsList);
        options.show();
        String selectedOption = options.selectedElement();
        if (selectedOption == null) {
            return "Exit functionality";
        }
        return selectedOption;
    }

    private JobOpeningDTO showAndSelectJobOpening() {
        SelectWidget<JobOpeningDTO> jobOpeningSelect = new SelectWidget<>("Job Opening", controller.getJobOpeningList());
        jobOpeningSelect.show();
        return jobOpeningSelect.selectedElement();
    }

    private void seeApplicationFileInWindow(ApplicationDTO applicationDTO, String filename) {
        if (applicationDTO != null && filename != null) {
            String fileContent = "--> Candidate: " + applicationDTO.getCandidate() + "\n\n" + controller.getApplicationFile(applicationDTO, filename);
            ApplicationFilePopUpWindow popUpWindow = new ApplicationFilePopUpWindow(fileContent);
            popUpWindow.run();
        }
    }

    private Iterable<String> listOfApplicationFilesNames(ApplicationDTO applicationDTO) {
        List<String> filenames = new ArrayList<>();
        for (ApplicationFile applicationFile : applicationDTO.getApplicationFiles()) {
            filenames.add(applicationFile.getApplicationFile().getName());
        }
        return filenames;
    }

    @Override
    public String headline() {
        return "Application Ranking";
    }
}

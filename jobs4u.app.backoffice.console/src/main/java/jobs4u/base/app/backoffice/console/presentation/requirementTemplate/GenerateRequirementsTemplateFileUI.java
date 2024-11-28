package jobs4u.base.app.backoffice.console.presentation.requirementTemplate;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.jobopening.listing.JobOpeningPrinter;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.requirementsmanagement.application.GenerateRequirementsTemplateFileController;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GenerateRequirementsTemplateFileUI extends AbstractUI {

    private GenerateRequirementsTemplateFileController controller = new GenerateRequirementsTemplateFileController();

    @Override
    protected boolean doShow() {
        Path tempDirectory;
        String directory;
        JobOpeningDTO jobOpeningDTO = null;
        SelectWidget<JobOpeningDTO> jobOpeningDTOSelectWidget;
        List<String> answers = new ArrayList<>();
        answers.add("Yes");
        answers.add("No");
        SelectWidget<String> choice = new SelectWidget<String>("Confirm selection?", answers);
        String answer = answers.get(1);

        while (answer.equals(answers.get(1))){
            jobOpeningDTOSelectWidget = new SelectWidget<>("Select a job opening to generate its requirements template file",
                    controller.getJobOpeningList(), new JobOpeningPrinter());
            jobOpeningDTOSelectWidget.show();
            jobOpeningDTO = jobOpeningDTOSelectWidget.selectedElement();

            System.out.println("[Requirement Specification] " + jobOpeningDTO.getRequirementName() + "\n");

            choice.show();
            answer = choice.selectedElement();
        }

        while(true){
            directory = Console.readNonEmptyLine("Provide an output directory path:", "An output directory path is obligatory.");
            File check = new File(directory);
            if (check.exists() && check.isDirectory()) {
                break;
            }
            System.out.println("Invalid directory path.");
        }

        try {
            if(this.controller.exportTemplateFile(jobOpeningDTO, directory)){
                System.out.println("Requirement Template File successfully exported.");
            }else{
                System.out.println("It was not possible to export the requirement template.");
            }
            return false;
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            System.out.println("Error in exporting the selected job opening's requirement template.\n" + e.getMessage());
        }
        return false;
    }

    @Override
    public String headline() {
        return "Generate and Export Requirement Template File";
    }
}

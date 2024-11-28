package jobs4u.base.app.backoffice.console.presentation.interviewTemplate;

import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.jobopening.listing.JobOpeningPrinter;
import jobs4u.base.interviewmodelmanagement.application.GenerateInterviewModelTemplateFileController;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GenerateInterviewModelTemplateFileUI extends AbstractUI {

    private GenerateInterviewModelTemplateFileController controller = new GenerateInterviewModelTemplateFileController();

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

        while (answer.equals(answers.get(1))) {
            jobOpeningDTOSelectWidget = new SelectWidget<>("Select a job opening to generate its interview model template file",
                    controller.getJobOpeningList(), new JobOpeningPrinter());
            jobOpeningDTOSelectWidget.show();
            jobOpeningDTO = jobOpeningDTOSelectWidget.selectedElement();

            System.out.println("[Interview Model] " + jobOpeningDTO.getInterviewModelName() + "\n");

            choice.show();
            answer = choice.selectedElement();
        }

        while (true) {
            directory = Console.readNonEmptyLine("Provide an output directory path:", "An output directory path is obligatory.");
            File check = new File(directory);
            if (check.exists() && check.isDirectory()) {
                break;
            }
            System.out.println("Invalid directory path.");
        }

        try {
            if (this.controller.exportTemplateFile(jobOpeningDTO, directory)) {
                System.out.println("Interview Model Template File successfully exported.");
            } else {
                System.out.println("It was not possible to export the interview model template.");
            }
            return false;
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            System.out.println("Error in exporting the selected job opening's interview model template.\n" + e.getMessage());
        }
        return false;
    }

    @Override
    public String headline() {
        return "Generate and Export Interview Model Template File";
    }

}


package jobs4u.base.app.backoffice.console.presentation.applications;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.applicationmanagement.application.DisplayAllApplicationDataController;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;

import java.util.List;
import java.util.NoSuchElementException;

public class DisplayAllApplicationDataUI extends AbstractUI {

    private static final DisplayAllApplicationDataController controller = new DisplayAllApplicationDataController();

    @Override
    protected boolean doShow() {
        SelectWidget<ApplicationDTO> selectorApplication;
        final ApplicationDTO applicationDTO;

        try{
            selectorApplication = new SelectWidget<>("Please select an application", controller.getApplicationsList(), new ApplicationDtoPrinter());

            selectorApplication.show();
            applicationDTO = selectorApplication.selectedElement();

            if (applicationDTO != null){
                String sb;

                if (applicationDTO.getInterview().interviewResult() != null && applicationDTO.getInterview().interviewAnswer() != null  && applicationDTO.getRequirementAnswer() != null && applicationDTO.getRequirementResult() != null){
                    sb = "==================================================================\n" +
                            "[Application Number] " + applicationDTO.getId() + "\n" +
                            "[Files] " + applicationDTO.getApplicationFiles() + "\n" +
                            "[Application Date] " + applicationDTO.getApplicationDate() + "\n" +
                            "[Application Status] " + applicationDTO.getApplicationStatus() + "\n" +
                            "[Candidate Name] " + applicationDTO.getCandidateName() + "\n" +
                            "[Candidate Username] " + applicationDTO.getCandidate() + "\n" +
                            "[Interview Result] " + applicationDTO.getInterview().interviewResult().interviewGrade() + "\n" +
                            "[Interview Answer] " + applicationDTO.getInterview().interviewAnswer().name() + "\n" +
                            "[Requirement Result] " + applicationDTO.getRequirementResult().requirementResult() + "\n" +
                            "[Requirement Answer] " + applicationDTO.getRequirementAnswer().name() + "\n" +
                            "=====================================================================";
                } else if (applicationDTO.getInterview().interviewAnswer() != null  && applicationDTO.getRequirementAnswer() != null && applicationDTO.getRequirementResult() != null) {
                    sb = "==================================================================\n" +
                            "[Application Number] " + applicationDTO.getId() + "\n" +
                            "[Files] " + applicationDTO.getApplicationFiles() + "\n" +
                            "[Application Date] " + applicationDTO.getApplicationDate() + "\n" +
                            "[Application Status] " + applicationDTO.getApplicationStatus() + "\n" +
                            "[Candidate Name] " + applicationDTO.getCandidateName() + "\n" +
                            "[Candidate Username] " + applicationDTO.getCandidate() + "\n" +
                            "[Interview Answer] " + applicationDTO.getInterview().interviewAnswer().name() + "\n" +
                            "[Requirement Result] " + applicationDTO.getRequirementResult().requirementResult() + "\n" +
                            "[Requirement Answer] " + applicationDTO.getRequirementAnswer().name() + "\n" +
                            "=====================================================================";
                } else if (applicationDTO.getInterview().interviewResult() != null && applicationDTO.getInterview().interviewAnswer() != null) {
                    sb = "==================================================================\n" +
                            "[Application] " + applicationDTO.getId() + "\n" +
                            "[Files] " + applicationDTO.getApplicationFiles() + "\n" +
                            "[Application Date] " + applicationDTO.getApplicationDate() + "\n" +
                            "[Application Status] " + applicationDTO.getApplicationStatus() + "\n" +
                            "[Candidate Name] " + applicationDTO.getCandidateName() + "\n" +
                            "[Candidate Username] " + applicationDTO.getCandidate() + "\n" +
                            "[Interview Result] " + applicationDTO.getInterview().interviewResult().interviewGrade() + "\n" +
                            "[Interview Answer] " + applicationDTO.getInterview().interviewAnswer().name() + "\n" +
                            "=====================================================================";
                } else if (applicationDTO.getRequirementResult() != null && applicationDTO.getRequirementAnswer() != null) {
                    sb = "==================================================================\n" +
                            "[Application] " + applicationDTO.getId() + "\n" +
                            "[Files] " + applicationDTO.getApplicationFiles() + "\n" +
                            "[Application Date] " + applicationDTO.getApplicationDate() + "\n" +
                            "[Application Status] " + applicationDTO.getApplicationStatus() + "\n" +
                            "[Candidate Name] " + applicationDTO.getCandidateName() + "\n" +
                            "[Candidate Username] " + applicationDTO.getCandidate() + "\n" +
                            "[Requirement Result] " + applicationDTO.getRequirementResult().requirementResult() + "\n" +
                            "[Requirement Answer] " + applicationDTO.getRequirementAnswer().name() + "\n" +
                            "=====================================================================";
                } else {
                    sb = "==================================================================\n" +
                            "[Application] " + applicationDTO.getId() + "\n" +
                            "[Files] " + applicationDTO.getApplicationFiles() + "\n" +
                            "[Application Date] " + applicationDTO.getApplicationDate() + "\n" +
                            "[Application Status] " + applicationDTO.getApplicationStatus() + "\n" +
                            "[Candidate Name] " + applicationDTO.getCandidateName() + "\n" +
                            "[Candidate Username] " + applicationDTO.getCandidate() + "\n" +
                            "=====================================================================";
                }
                System.out.println(sb);
            }
        } catch (NoSuchElementException | IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    @Override
    public String headline() {
        return "Display All Data of Application";
    }
}

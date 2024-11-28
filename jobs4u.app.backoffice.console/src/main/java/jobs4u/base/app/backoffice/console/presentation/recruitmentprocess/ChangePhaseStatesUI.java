package jobs4u.base.app.backoffice.console.presentation.recruitmentprocess;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.JobOpeningDTOPrinter;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.recruitmentprocessmanagement.application.ChangePhaseStatesController;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcessStatusEnum;
import jobs4u.base.recruitmentprocessmanagement.dto.RecruitmentProcessDTO;

import java.util.Scanner;

public class ChangePhaseStatesUI extends AbstractUI {

    private ChangePhaseStatesController controller = new ChangePhaseStatesController();

    private Scanner read = new Scanner(System.in);

    @Override
    protected boolean doShow() {

        System.out.println();

        SelectWidget<JobOpeningDTO> selector_jo;
        final JobOpeningDTO jobOpeningDTO;

        try {
            selector_jo = new SelectWidget<>("Select A Job Opening:", controller.getJobOpeningList(), new JobOpeningDTOPrinter());
            selector_jo.show();
            jobOpeningDTO = selector_jo.selectedElement();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        RecruitmentProcessDTO recruitmentProcessDTO = null;

        try {
            recruitmentProcessDTO = controller.getRecruitmentProcessDTOWithJobReference(jobOpeningDTO.getJobReference());
            System.out.println(recruitmentProcessDTO.toString());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Do you want to go back a phase or move on to the next? (Back | Next)");
        String answer = read.next();

        while(true) {
            if (answer.equalsIgnoreCase("back")) {
                if(checkIfItCanGoBack(recruitmentProcessDTO)){
                    controller.goBackAPhase(recruitmentProcessDTO.getJobOpening());
                }
                break;
            } else if (answer.equalsIgnoreCase("next")) {
                if(checkIfItCanGoNext(recruitmentProcessDTO)){
                    controller.goNextPhase(recruitmentProcessDTO.getJobOpening());
                }
                break;
            } else {
                System.out.println("Wrong Answer. Try Again:\n");
                answer = read.next();
            }
        }

        System.out.println("Worked!\n");

        return false;
    }

    private boolean checkIfItCanGoNext(RecruitmentProcessDTO recruitmentProcessDTO) {
        if(recruitmentProcessDTO.getRecruitmentProcessStatus().equals(String.valueOf(RecruitmentProcessStatusEnum.RESULTS))) {
            System.out.println("You'll Be Ending The Recruitment Process.\n");
            System.out.println("Are You Sure? (Yes | No)");
            String answer = read.next();

            if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")){
                return true;
            } else {
                return false;
            }
        } else if(recruitmentProcessDTO.getRecruitmentProcessStatus().equals(String.valueOf(RecruitmentProcessStatusEnum.CONCLUDED))) {
            System.out.println("Unable To Open Next Phase.\n");
            System.out.println("REASON : Recruitment Process Has Concluded.\n");
            return false;
        } else if(recruitmentProcessDTO.getRecruitmentProcessStatus().equals(String.valueOf(RecruitmentProcessStatusEnum.PLANNED))) {
            System.out.println("You'll Be Starting The Recruitment Process.");
            System.out.println("Are You Sure? (Yes | No)");
            String answer = read.next();

            if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean checkIfItCanGoBack(RecruitmentProcessDTO recruitmentProcessDTO){
        if(recruitmentProcessDTO.getRecruitmentProcessStatus().equals(String.valueOf(RecruitmentProcessStatusEnum.PLANNED)) || recruitmentProcessDTO.getRecruitmentProcessStatus().equals(String.valueOf(RecruitmentProcessStatusEnum.CONCLUDED))){
            System.out.println("Unable To Go Back.");
            System.out.println("REASON : Recruitment Process Hasn't Started Or Has Concluded.");
            return false;
        } else {
            System.out.println("Has Any Progress Been Made On The Current Phase? (Yes | No)");
            String answer = read.next();

            if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")){
                System.out.println("Unable To Go Back A Phase.");
                System.out.println("REASON : Progress Has Been Made On The Current Phase.");
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public String headline() {
        return "=-=-=-=-=-=OPENING/CLOSING PHASES=-=-=-=-=-=\n";
    }
}

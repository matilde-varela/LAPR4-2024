package jobs4u.base.app.backoffice.console.presentation.interviewmodel;

import eapli.framework.visitor.Visitor;
import jobs4u.base.interviewmodelmanagement.dto.InterviewModelDTO;


public class InterviewModelDtoPrinter implements Visitor<InterviewModelDTO> {

    @Override
    public void visit(InterviewModelDTO intModel) {

        System.out.printf("%s%n", intModel.name());
    }
}

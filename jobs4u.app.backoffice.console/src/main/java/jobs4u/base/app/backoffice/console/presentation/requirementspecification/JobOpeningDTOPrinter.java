package jobs4u.base.app.backoffice.console.presentation.requirementspecification;

import eapli.framework.visitor.Visitor;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

public class JobOpeningDTOPrinter implements Visitor<JobOpeningDTO> {
    @Override
    public void visit(JobOpeningDTO visitee) {
        System.out.printf("\n%s", visitee.toString());
    }
}

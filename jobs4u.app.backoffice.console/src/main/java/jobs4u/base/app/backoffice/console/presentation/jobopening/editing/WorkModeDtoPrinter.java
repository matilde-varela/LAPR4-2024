package jobs4u.base.app.backoffice.console.presentation.jobopening.editing;

import eapli.framework.visitor.Visitor;
import jobs4u.base.workmodemanagement.dto.WorkModeDTO;

public class WorkModeDtoPrinter implements Visitor<WorkModeDTO> {
    @Override
    public void visit(WorkModeDTO visitee) {
        System.out.printf("%s", visitee.toString());
    }
}

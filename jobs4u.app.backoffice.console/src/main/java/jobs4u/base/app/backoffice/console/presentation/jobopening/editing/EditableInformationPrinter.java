package jobs4u.base.app.backoffice.console.presentation.jobopening.editing;

import eapli.framework.visitor.Visitor;
import jobs4u.base.jobopeningmanagement.domain.EditableInformation;

public class EditableInformationPrinter implements Visitor<EditableInformation> {
    @Override
    public void visit(EditableInformation visitee) {
        System.out.printf("%s", visitee.toString());
    }
}

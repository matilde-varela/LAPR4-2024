package jobs4u.base.app.backoffice.console.presentation.applications;

import eapli.framework.visitor.Visitor;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;

public class ApplicationDtoPrinter implements Visitor<ApplicationDTO> {
    @Override
    public void visit(ApplicationDTO applicationDTO){
        System.out.printf("%n%s",applicationDTO.toString());
    }
}

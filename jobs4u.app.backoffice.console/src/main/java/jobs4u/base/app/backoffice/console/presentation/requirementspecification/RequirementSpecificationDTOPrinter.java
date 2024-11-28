package jobs4u.base.app.backoffice.console.presentation.requirementspecification;

import eapli.framework.visitor.Visitor;
import jobs4u.base.requirementsmanagement.dto.RequirementSpecificationDTO;


public class RequirementSpecificationDTOPrinter implements Visitor<RequirementSpecificationDTO> {

    @Override
    public void visit(RequirementSpecificationDTO visit) {
        System.out.printf("%s", visit.toString());
    }
}

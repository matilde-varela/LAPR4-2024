package jobs4u.base.app.backoffice.console.presentation.jobopening.editing;

import eapli.framework.visitor.Visitor;
import jobs4u.base.contracttypemanagement.dto.ContractTypeDTO;

public class ContractTypeDtoPrinter implements Visitor<ContractTypeDTO> {

    @Override
    public void visit(ContractTypeDTO visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
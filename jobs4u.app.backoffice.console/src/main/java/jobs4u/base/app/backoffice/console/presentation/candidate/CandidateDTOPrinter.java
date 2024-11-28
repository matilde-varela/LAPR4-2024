package jobs4u.base.app.backoffice.console.presentation.candidate;

import eapli.framework.visitor.Visitor;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.candidatemanagement.dto.CandidateDTO;

public class CandidateDTOPrinter implements Visitor<CandidateDTO> {
    @Override
    public void visit(CandidateDTO candidateDTO){
        System.out.printf("%n%s",candidateDTO.toString());
    }
}

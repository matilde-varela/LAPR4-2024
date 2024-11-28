package jobs4u.base.app.user.console.presentation.jobopeninglist;

import eapli.framework.visitor.Visitor;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;

public class JobOpeningCustomerPrinter  implements Visitor<JobOpeningDTO> {

   //TODO CHANGE PRINTER CONTENT
    @Override
    public void visit(JobOpeningDTO jobOpening) {
        String sb = "==================================================================\n" +
                "[Job Reference] " + jobOpening.getJobReference() + "\n" +
                "[Position/Function] " + jobOpening.getFunction() + "\n" +
                "[Applications] : " + jobOpening.getNumberApplicants() + "\n" +
                "[Recruitment Process] Active since: " + jobOpening.getActiveSinceDate() + "\n" +
                "=====================================================================";
        System.out.println(sb);
    }
}

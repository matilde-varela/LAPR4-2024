package jobs4u.base.interviewmodelmanagement.repositories;

import eapli.framework.domain.repositories.DomainRepository;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModelName;

import java.util.Optional;

public interface InterviewModelRepository extends DomainRepository<InterviewModelName, InterviewModel> {

    Iterable<InterviewModel> interviewModels();

    Optional<InterviewModel> getFileByName(String filename);

    Optional<InterviewModel> interviewModelByInterviewName(String interviewModelName);
}

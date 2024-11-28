package jobs4u.base.persistence.impl.inmemory;

import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModelName;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryInterviewModelRepository
        extends InMemoryDomainRepository<InterviewModel, InterviewModelName>
        implements InterviewModelRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public List<InterviewModel> interviewModels() {
        List<InterviewModel> interviewModelList = new ArrayList<>();
        Iterable<InterviewModel> interviewModels = findAll();
        for (InterviewModel inter : interviewModels) {
            interviewModelList.add(inter);
        }
        return interviewModelList;
    }

    @Override
    public Optional<InterviewModel> getFileByName(String filename) {
        return matchOne(e -> e.identity().name().equals(filename));
    }

    @Override
    public Optional<InterviewModel> interviewModelByInterviewName(String interviewModelName) {
        return matchOne(e -> e.identity().name().equals(interviewModelName));

    }
}

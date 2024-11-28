package jobs4u.base.persistence.impl.jpa;

import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jobs4u.base.Application;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModel;
import jobs4u.base.interviewmodelmanagement.domain.InterviewModelName;
import jobs4u.base.interviewmodelmanagement.repositories.InterviewModelRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaInterviewModelRepository
        extends JpaAutoTxRepository<InterviewModel, InterviewModelName, InterviewModelName>
        implements InterviewModelRepository {


    public JpaInterviewModelRepository(final TransactionalContext autoTx) {
        super(autoTx, "interviewModelName");
    }

    public JpaInterviewModelRepository(final String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(), "interviewModelName");
    }

    @Override
    public Iterable<InterviewModel> interviewModels() {
        return findAll();
    }

    @Override
    public Optional<InterviewModel> getFileByName(String filename) {
        final Map<String, Object> params = new HashMap<>();
        params.put("filename", filename);
        return matchOne("e.interviewModelName.name=:filename", params);
    }

    @Override
    public Optional<InterviewModel> interviewModelByInterviewName(String interviewModelName) {
        final Map<String, Object> params = new HashMap<>();
        params.put("interview", interviewModelName);
        return matchOne("e.interviewModelName.name=:interview", params);
    }

}

package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.time.domain.model.DateInterval;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.Embeddable;

import java.util.Calendar;

@Embeddable
public class RecruitmentPeriod implements ValueObject {

    private DateInterval recruitmentInterval;

    protected RecruitmentPeriod(){
        //for ORM
    }

    public RecruitmentPeriod(DateInterval recruitmentInterval) {
        Preconditions.noneNull(recruitmentInterval);
        this.recruitmentInterval = recruitmentInterval;
    }

    public DateInterval getRecruitmentInterval(){
        return this.recruitmentInterval;
    }

    public Calendar getStartDate(){
        return recruitmentInterval.start();
    }

    public Calendar getEndDate(){
        return recruitmentInterval.end();
    }


}

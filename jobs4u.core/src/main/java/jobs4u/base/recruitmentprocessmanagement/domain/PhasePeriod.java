package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.time.domain.model.DateInterval;
import jakarta.persistence.Embeddable;

import java.util.Calendar;

@Embeddable
public class PhasePeriod implements ValueObject {

    private DateInterval phasePeriod;

    public PhasePeriod() {
        //for ORM
    }

    public PhasePeriod(DateInterval phasePeriod) {
        this.phasePeriod = phasePeriod;
    }

    public DateInterval getPhasePeriod() {
        return phasePeriod;
    }

    public Calendar getStartDate(){
        return phasePeriod.start();
    }

    public Calendar getEndDate(){
        return phasePeriod.end();
    }

}

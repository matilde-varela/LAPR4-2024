package jobs4u.base.recruitmentprocessmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.time.domain.model.DateInterval;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;
import jobs4u.base.recruitmentprocessmanagement.dto.PhaseDTO;

import java.util.Calendar;


@Embeddable
public class Phase implements ValueObject, DTOable<PhaseDTO> {

    private PhaseType phaseType;

    private PhaseDescription description;

    private PhasePeriod period;

    protected Phase() {
        //for ORM
    }

    public Phase(PhaseType phaseType, PhaseDescription description, PhasePeriod period) {
        Preconditions.noneNull(phaseType, period, description);
        this.phaseType = phaseType;
        this.description = description;
        this.period = period;
    }

    public Phase(String phaseType, String description, Calendar initial, Calendar end) {
        Preconditions.noneNull(phaseType, description, initial, end);
        Preconditions.ensure(initial.before(end));
        this.phaseType = new PhaseType(phaseType);
        this.description = new PhaseDescription(description);
        this.period = new PhasePeriod(new DateInterval(initial, end));
    }

    public String description() {
        return this.description.toString();
    }

    public PhasePeriod activePeriod(){
        return this.period;
    }

    @Override
    public PhaseDTO toDTO() {
        return new PhaseDTO(phaseType.getTypeDenomination(), description.getDescriptionText(), period.getPhasePeriod());
    }
}

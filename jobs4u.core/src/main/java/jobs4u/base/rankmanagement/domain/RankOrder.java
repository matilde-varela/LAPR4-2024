package jobs4u.base.rankmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.representations.dto.DTOable;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.*;

import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.rankmanagement.dto.RankOrderDTO;

@Entity
@Table(name = "T_RANK_ORDERS")
public class RankOrder implements ValueObject, DTOable<RankOrderDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    private Integer numberRanked;

    protected RankOrder() {
        //for ORM
    }

    public RankOrder(Application application, Integer numberRanked) {
        Preconditions.noneNull(application, numberRanked);
        Preconditions.isPositive(numberRanked);
        this.application = application;
        this.numberRanked = numberRanked;
    }

    public Application application() {
        return this.application;
    }

    public Integer numberRanked() {
        return this.numberRanked;
    }

    public EmailAddress candidateEmail() {
        return this.application.candidate().email();
    }


    public Application acceptApplication() {
        this.application.acceptApplication();
        return this.application;
    }


    public Application rejectApplication() {
        this.application.rejectApplication();
        return this.application;
    }

    @Override
    public RankOrderDTO toDTO() {
       return new RankOrderDTO(id, numberRanked, application.toDTO());
    }
}

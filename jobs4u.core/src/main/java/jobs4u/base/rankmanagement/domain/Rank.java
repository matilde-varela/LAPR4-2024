package jobs4u.base.rankmanagement.domain;


import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import jakarta.persistence.*;
import jobs4u.base.jobopeningmanagement.domain.JobReference;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "T_RANK")
public class Rank implements AggregateRoot<JobReference> {

    @EmbeddedId
    private JobReference jobReference;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RankOrder> order = new LinkedList<>();

    public Rank() {
        //for ORM
    }

    public Rank(JobReference jobOpeningAssociated, List<RankOrder> rankOrders) {
        this.jobReference = jobOpeningAssociated;
        this.order = rankOrders;
    }
    public Rank(JobReference jobOpeningAssociated) {
        this.jobReference = jobOpeningAssociated;
    }

    public JobReference jobReference() {
        return this.jobReference;
    }

    public List<RankOrder> rankOrder(){
        return this.order;
    }


    public void updateRankOrderList(LinkedList<RankOrder> newRankOrders) {
        this.rankOrder().clear();
        this.rankOrder().addAll(newRankOrders);
    }

    public LinkedList<RankOrder> rankOrderUntilOrder(int order) {
        LinkedList<RankOrder> untilOrder = new LinkedList<>();
        for (RankOrder rankOrder : this.rankOrder()) {
            if (rankOrder.numberRanked() < order){
                untilOrder.add(rankOrder);
            }
        }
        return untilOrder;
    }

    public LinkedList<RankOrder> rankOrdersSinceOrder(int order) {
        LinkedList<RankOrder> sinceOrder = new LinkedList<>();
        for (RankOrder rankOrder : this.rankOrder()) {
            if (rankOrder.numberRanked() >= order){
                sinceOrder.add(rankOrder);
            }
        }
        return sinceOrder;
    }

    @Override
    public JobReference identity() {
        return this.jobReference;
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this, other);
    }

}

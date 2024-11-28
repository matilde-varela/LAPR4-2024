package jobs4u.base.rankmanagement.dto;

import eapli.framework.validations.Preconditions;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;

public class RankOrderDTO {

    private final int numberRanked;
    private final Long id;
    private final ApplicationDTO application;

    public RankOrderDTO(long id, int numberRanked, ApplicationDTO application) {
        Preconditions.noneNull(id, numberRanked, application);
        Preconditions.isPositive(id);
        Preconditions.isPositive(numberRanked);
        this.id = id;
        this.numberRanked = numberRanked;
        this.application = application;
    }

    public ApplicationDTO application() {
        return application;
    }

    @Override
    public String toString() {
        return String.format("\n=====================================================================\n" +
                "#Number of Order: # %d%s", numberRanked, application.toString());
    }

}

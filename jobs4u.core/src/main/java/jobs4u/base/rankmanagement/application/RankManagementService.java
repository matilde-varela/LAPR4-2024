package jobs4u.base.rankmanagement.application;

import jobs4u.base.applicationmanagement.application.ApplicationManagementService;
import jobs4u.base.applicationmanagement.domain.Application;
import jobs4u.base.applicationmanagement.dto.ApplicationDTO;
import jobs4u.base.infrastructure.persistence.PersistenceContext;
import jobs4u.base.jobopeningmanagement.dto.JobOpeningDTO;
import jobs4u.base.rankmanagement.domain.Rank;
import jobs4u.base.rankmanagement.domain.RankOrder;
import jobs4u.base.rankmanagement.dto.RankOrderDTO;
import jobs4u.base.rankmanagement.persistence.RankRepository;

import java.util.*;

public class RankManagementService {

    public static double SYSTEM_CONFIG_NUMBER_RANK_ORDERS = 0.5;

    private final RankRepository rankRepository = PersistenceContext
            .repositories().ranks();

    private final ApplicationManagementService applicationManagementService = new ApplicationManagementService();


    public List<RankOrderDTO> getRank(String jobReference) {
        Optional<Rank> rank = rankRepository.getRankFromJobReference(jobReference);
        if (rank.isPresent() && !rank.get().rankOrder().isEmpty()) {
            List<RankOrderDTO> rankOrders = new ArrayList<>();
            for (RankOrder rankOrder : rank.get().rankOrder()) {
                rankOrders.add(rankOrder.toDTO());
            }
            return rankOrders;
        }
        return new ArrayList<>();
    }

    public void updateRanking(JobOpeningDTO jobOpeningDTO, ApplicationDTO applicationDTO, int order) {
        Application application = applicationManagementService.getApplication(applicationDTO);

        Optional<Rank> rank = rankRepository.getRankFromJobReference(jobOpeningDTO.getJobReference());

        if (rank.isPresent()) {
            RankOrder rankOrder;
            LinkedList<RankOrder> newRankOrders = new LinkedList<>();

            LinkedList<RankOrder> rankOrdersAbove = rank.get().rankOrderUntilOrder(order);
            LinkedList<RankOrder> rankOrdersBelow = rank.get().rankOrdersSinceOrder(order);

            rankOrdersAbove.removeIf(rankOrderAbove -> rankOrderAbove.application().equals(application));
            rankOrdersBelow.removeIf(rankOrderAbove -> rankOrderAbove.application().equals(application));

            int orderIndex = 1;

            if (order != 1) {
                for (RankOrder rankOrderAbove : rankOrdersAbove) {
                    newRankOrders.add(new RankOrder(rankOrderAbove.application(), orderIndex));
                    ++orderIndex;
                }
            }

            if (orderIndex < order) {
                rankOrder = new RankOrder(application, orderIndex);
            } else {
                rankOrder = new RankOrder(application, order);
            }

            newRankOrders.add(rankOrder);

            ++orderIndex;

            for (RankOrder rankOrderBelow : rankOrdersBelow) {
                newRankOrders.add(new RankOrder(rankOrderBelow.application(), orderIndex));
                ++orderIndex;
            }

            double maxNumber = calculateNumberOfRankOrdersToSave(jobOpeningDTO.getNumVacancies());
            if (newRankOrders.size() > maxNumber) {
                newRankOrders = new LinkedList<>(newRankOrders.subList(0, (int) maxNumber));
            }

            rank.get().updateRankOrderList(newRankOrders);
            rankRepository.save(rank.get());
        }
    }

    public boolean checkNeededApplicationsAreRanked(JobOpeningDTO jobOpeningDTO) {
        Optional<Rank> rank = rankRepository.getRankFromJobReference(jobOpeningDTO.getJobReference());
        if (rank.isPresent()) {
            int numVacancies = jobOpeningDTO.getNumVacancies();
            List<RankOrder> rankOrders = rank.get().rankOrder();

            int required = (int) calculateNumberOfRankOrdersToSave(numVacancies);

            if (jobOpeningDTO.getNumberApplicants() <= required && rankOrders.size() <= required) {
                return true;
            } else if (jobOpeningDTO.getNumberApplicants() >= required  && rankOrders.size() < required) {
                return false;
            }

            for (RankOrder rankOrder : rankOrders) {
                if (rankOrder.numberRanked() < 1) {
                    return false;
                }
            }
            return true;

        }
        throw new NoSuchElementException("No such rank: " + jobOpeningDTO.getJobReference() + " \n");
    }

    private double calculateNumberOfRankOrdersToSave(int numVacancies) {
        return numVacancies + (numVacancies * SYSTEM_CONFIG_NUMBER_RANK_ORDERS);
    }

    public void changeSystemConfigNumberRankOrdersToSave(double number) {
        SYSTEM_CONFIG_NUMBER_RANK_ORDERS = number;
    }
}

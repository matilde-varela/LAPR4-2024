package jobs4u.base.rankmanagement.application;

public class UpdateSystemRankConfigurationController {

    RankManagementService rankManagementService;

    public UpdateSystemRankConfigurationController() {
        rankManagementService = new RankManagementService();
    }

    public void updateSystemConfigurationForRank(int i) {
        double sysConfig = 0;

        switch (i) {
            case 1:
                sysConfig = 0.5;
                break;
            case 2:
                sysConfig = 1.0;
                break;
            case 3:
                sysConfig = 2.0;
                break;
        }

        rankManagementService.changeSystemConfigNumberRankOrdersToSave(sysConfig);
    }
}

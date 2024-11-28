package jobs4u.base.app.backoffice.console.presentation.rank;

import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import jobs4u.base.rankmanagement.application.UpdateSystemRankConfigurationController;

import java.util.ArrayList;

public class UpdateSystemRankConfigurationUI extends AbstractUI {

    UpdateSystemRankConfigurationController controller = new UpdateSystemRankConfigurationController();
    
    @Override
    protected boolean doShow() {
        System.out.println("The chosen configuration will affect how many rank entries will be saved within the system.");
        System.out.println("Formula: (number of vacancies) + (number of vacancies * SYSTEM_CONFIG)");

        SelectWidget<String> options = getOptions();
        controller.updateSystemConfigurationForRank(options.selectedOption());

        return false;
    }

    private SelectWidget<String> getOptions() {
        ArrayList<String> systemConfigOptions = new ArrayList<>();
        systemConfigOptions.add("SYSTEM_CONFIG = [0.5] | Number of vacancies = 2 -> rank entries saved: 3");
        systemConfigOptions.add("SYSTEM_CONFIG = [1] | Number of vacancies = 2 -> rank entries saved: 4");
        systemConfigOptions.add("SYSTEM_CONFIG = [2] | Number of vacancies = 2 -> rank entries saved: 6");

        SelectWidget<String> options = new SelectWidget<>("Choose a system configuration", systemConfigOptions);
        options.show();
        return options;
    }

    @Override
    public String headline() {
        return "Update System Rank Configuration";
    }
}

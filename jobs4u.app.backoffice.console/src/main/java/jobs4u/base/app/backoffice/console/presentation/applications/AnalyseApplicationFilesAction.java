package jobs4u.base.app.backoffice.console.presentation.applications;

import eapli.framework.actions.Action;

public class AnalyseApplicationFilesAction  implements Action {
    @Override
    public boolean execute() {
        return new AnalyseApplicationFilesUI().show();
    }
}

package jobs4u.base.app.backoffice.console.presentation.interviewmodel;

import eapli.framework.actions.Action;

public class UploadInterviewResponsesAction implements Action {

    @Override
    public boolean execute(){
        return new UploadInterviewResponsesUI().show();
    }
}

/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jobs4u.base.app.backoffice.console.presentation;

import jobs4u.base.Application;
import jobs4u.base.app.backoffice.console.presentation.applications.AnalyseApplicationFilesAction;
import jobs4u.base.app.backoffice.console.presentation.applications.DisplayAllApplicationDataAction;
import jobs4u.base.app.backoffice.console.presentation.rank.ApplicationRankingAction;
import jobs4u.base.app.backoffice.console.presentation.applications.ScheduleInterviewAction;
import jobs4u.base.app.backoffice.console.presentation.authz.AddUserUI;
import jobs4u.base.app.backoffice.console.presentation.authz.EnableDisableUserAction;
import jobs4u.base.app.backoffice.console.presentation.authz.ListUsersAction;
import jobs4u.base.app.backoffice.console.presentation.candidate.*;
import jobs4u.base.app.backoffice.console.presentation.customer.RegisterCustomerAction;
import jobs4u.base.app.backoffice.console.presentation.interviewTemplate.GenerateInterviewModelTemplateFileUI;
import jobs4u.base.app.backoffice.console.presentation.interviewmodel.SelectInterviewModelAction;
import jobs4u.base.app.backoffice.console.presentation.interviewmodel.UploadInterviewResponsesAction;
import jobs4u.base.app.backoffice.console.presentation.jobopening.editing.EditJobOpeningAction;
import jobs4u.base.app.backoffice.console.presentation.jobopening.editing.EvaluateInterviewsAction;
import jobs4u.base.app.backoffice.console.presentation.jobopening.listing.ListJobOpeningsAction;
import jobs4u.base.app.backoffice.console.presentation.jobopening.publishing.PublishResultJobOpeningAction;
import jobs4u.base.app.backoffice.console.presentation.jobopening.registration.RegisterJobOpeningAction;
import jobs4u.base.app.backoffice.console.presentation.applications.ListJobOpeningApplicationsAction;
import jobs4u.base.app.backoffice.console.presentation.rank.UpdateSystemRankConfigurationUI;
import jobs4u.base.app.backoffice.console.presentation.recruitmentprocess.ChangePhaseStatesAction;
import jobs4u.base.app.backoffice.console.presentation.requirementTemplate.GenerateRequirementsTemplateFileUI;
import jobs4u.base.app.backoffice.console.presentation.operator.RegisterJobOpeningApplicationsAction;
import jobs4u.base.app.backoffice.console.presentation.recruitmentprocess.SetupRecruitmentProcessAction;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.SelectRequirementSpecificationAction;
import jobs4u.base.app.backoffice.console.presentation.languageengineer.RegisterPluginAction;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.UploadRequirementAnswersUI;
import jobs4u.base.app.backoffice.console.presentation.requirementspecification.VerifyRequirementAction;
import jobs4u.base.app.common.console.presentation.authz.MyUserMenu;
import jobs4u.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 * TODO split this class in more specialized classes for each menu
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;

    //OPERATOR
//    private static final int REGISTER_CANDIDATE = 1;
//    private static final int LIST_CANDIDATES = 2;
//    private static final int REGISTER_APPLICATIONS = 1;
    private static final int PLUGIN_OPTION = 4;
//    private static final int GENERATE_REQUIREMENT_TEMPLATE = 1;

    // CUSTOMER MANAGER SETTINGS
//    private static final int REGISTER_JOB_OPENING = 1;
//    private static final int LIST_JOB_OPENINGS = 2;
//    private static final int SELECT_REQ_SPEC = 3;
//    private static final int SELECT_INT_MODEL = 4;
//    private static final int LIST_JOB_OPENING_APPLICATIONS = 5;
//    private static final int SETUP_RECRUITMENT_PROC = 6;
//
//    private static final int SEE_CANDIDATE_INFO = 7;
//    private static final int REGISTER_CUSTOMERS = 1;
    private static final int PLUGIN_OPTION_CUSTOMER = 5;
//    private static final int GENERATE_INTERVIEW_TEMPLATE = 1;
//
//    // LANGUAGE ENGINEER SETTINGS
//    private static final int REGISTER_PLUGIN = 1;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    //    private static final int JOB_OPENING_OPTION = 3;
//    private static final int CUSTOMER_OPTION = 4;
//    private static final int CANDIDATE_OPERATOR_OPTION = 5;
//    private static final int APPLICATION_OPTION = 6;
    private static final int LANGUAGE_ENGINEER_OPTION = 6;

//    private static final int CANDIDATE_CUSTOMER_M_OPTION = 7;

    private static final String SEPARATOR_LABEL = "--------------";


    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    public MainMenu() {
    }

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Jobs4U [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Jobs4U [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.ADMIN)) {
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);
            final Menu rankMenu = buildAdminRankMenu();
            mainMenu.addSubMenu(3, rankMenu);
//            final Menu settingsMenu = buildAdminSettingsMenu();
//            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.LANGUAGE_ENGINEER)) {
            final Menu pluginMenu = buildLanguageEngineerSettingsMenu();
            mainMenu.addSubMenu(LANGUAGE_ENGINEER_OPTION, pluginMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.CUSTOMER_MANAGER)) {
            final Menu jobOpeningMenu = buildCustomerManagerJobOpeningMenu();
            mainMenu.addSubMenu(2, jobOpeningMenu);
            final Menu customerMenu = buildCustomerManagerCustomerMenu();
            mainMenu.addSubMenu(3, customerMenu);
            final Menu candidateMenu = buildCustomerManagerCandidateMenu();
            mainMenu.addSubMenu(4, candidateMenu);
            final Menu pluginMenu = buildCustomerManagerPluginMenu();
            mainMenu.addSubMenu(5, pluginMenu);
            final Menu applicationMenu = buildCustomerManagerApplicationMenu();
            mainMenu.addSubMenu(6, applicationMenu);

        }
        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.OPERATOR)) {
            final Menu pluginMenu = buildOperatorPluginMenu();
            mainMenu.addSubMenu(2, pluginMenu);
            final Menu candidateMenu = buildOperatorCandidateMenu();
            mainMenu.addSubMenu(3, candidateMenu);
            final Menu application = buildOperatorApplicationMenu();
            mainMenu.addSubMenu(4, application);
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction("Closing."));

        return mainMenu;
    }

    private Menu buildAdminRankMenu() {
        final Menu menu = new Menu("Rank >");
        menu.addItem(1, "Update System Configuration for Rank", new UpdateSystemRankConfigurationUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildCustomerManagerApplicationMenu() {
        final Menu menu = new Menu("Applications >");
        menu.addItem(1, "See Application TOP 20 Used Words List", new AnalyseApplicationFilesAction());
        menu.addItem(2,"Schedule Interview", new ScheduleInterviewAction());
        menu.addItem(3, "Display all data of an application", new DisplayAllApplicationDataAction());
        menu.addItem(4, "Rank Applications", new ApplicationRankingAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildCustomerManagerCandidateMenu() {
        final Menu menu = new Menu("Candidates >");
        menu.addItem(1, "See all data of a candidate", new ListCandidateDataAction());
        menu.addItem(2, "Get an ordered list of candidates", new ListOrderedCandidatesAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildOperatorPluginMenu() {
        final Menu menu = new Menu("Plugins >");
        menu.addItem(1, "Generate and export Requirement Specification Template", new GenerateRequirementsTemplateFileUI()::show);
        menu.addItem(2, "Import Requirement Answer Files", new UploadRequirementAnswersUI()::show);
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildCustomerManagerPluginMenu() {
        final Menu menu = new Menu("Plugins >");
        menu.addItem(1, "Generate and export Interview Model Template", new GenerateInterviewModelTemplateFileUI()::show);
        menu.addItem(2, "Import Interview Answer Files", new UploadInterviewResponsesAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildLanguageEngineerSettingsMenu() {
        final Menu menu = new Menu("Language Engineer >");
        menu.addItem(1, "Register a new plugin", new RegisterPluginAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildOperatorCandidateMenu() {
        final Menu menu = new Menu("Candidates >");
        menu.addItem(1, "Register a New Candidate", new RegisterCandidateAction());
        menu.addItem(2, "List All Candidates", new ListAllCandidatesAction());
        menu.addItem(3, "Activate/Deactivate a Candidate", new EnableDisableCandidateAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildOperatorApplicationMenu() {
        final Menu menu = new Menu("Applications >");
        menu.addItem(1, "Register Job Opening Applications", new RegisterJobOpeningApplicationsAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }


    private Menu buildCustomerManagerCustomerMenu() {
        final Menu menu = new Menu("Customers >");

        menu.addItem(1, "Register a Customer", new RegisterCustomerAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildCustomerManagerJobOpeningMenu() {
        final Menu menu = new Menu("Job Opening >");

        menu.addItem(1, "Register a job opening", new RegisterJobOpeningAction());
        menu.addItem(2, "List job openings", new ListJobOpeningsAction());
        menu.addItem(3, "Select a requirement specification", new SelectRequirementSpecificationAction());
        menu.addItem(4, "Select an interview model", new SelectInterviewModelAction());
        menu.addItem(5, "List job opening applications", new ListJobOpeningApplicationsAction());
        menu.addItem(6, "Setup a recruitment process", new SetupRecruitmentProcessAction());
        menu.addItem(7, "Edit a job opening", new EditJobOpeningAction());
        menu.addItem(8, "Opening / Closing phases", new ChangePhaseStatesAction());
        menu.addItem(9, "Verify Requirement Specifitions of applications", new VerifyRequirementAction());
        menu.addItem(10,"Send Notification Email with Requirement Specification result", new SendNotificationEmailAction());
        menu.addItem(11,"Publish results of job opening", new PublishResultJobOpeningAction());
        menu.addItem(12, "Evaluate interviews for a job opening", new EvaluateInterviewsAction());

        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Enable/Disable User", new EnableDisableUserAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

}

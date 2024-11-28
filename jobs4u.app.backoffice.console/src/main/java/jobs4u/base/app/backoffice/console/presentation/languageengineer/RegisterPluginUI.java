package jobs4u.base.app.backoffice.console.presentation.languageengineer;

import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;
import jobs4u.base.interviewmodelmanagement.application.RegisterPluginController;

public class RegisterPluginUI extends AbstractUI {

    private static final RegisterPluginController controller = new RegisterPluginController();

    @Override
    protected boolean doShow() {

        System.out.println("To register a new plugin please provide the following details:");
        String option = Console.readNonEmptyLine("Would you like to register an interview plugin or a requirement plugin? 'Interview' or 'Requirement'\n1) Interview Plugin\n2) Requirement Plugin",
                "Choice: 'Interview' or 'Requirement'");
        String name = Console.readNonEmptyLine("What's the name of the plugin?",
                "Providing a name for the plugin is obligatory.");
        if(option.equals("1")) {
            while(controller.checkIfInterviewModelAlreadyExists(name)){
                System.out.println("Interview plugin already exists.");
                name = Console.readNonEmptyLine("Give another name for the plugin:",
                        "Providing a name for the plugin is obligatory.");
            }
        }else if(option.equals("2")){
            while(controller.checkIfRequirementSpecificationAlreadyExists(name)){
                System.out.println("Requirement plugin already exists.");
                name = Console.readNonEmptyLine("Give another name for the plugin:",
                        "Providing a name for the plugin is obligatory.");
            }
        }
        String description = Console.readNonEmptyLine("Write a small description of the plugin:",
                "Providing a small description of the plugin is obligatory.");
        String fullClassName = Console.readNonEmptyLine("Provide the full class name of the class of the plugin:",
                "Providing the full class name of the class of the plugin is obligatory.");
        String configFile =  Console.readNonEmptyLine("Provide the configuration file of the plugin:","Providing the configuration filepath is mandatory.");
        String dataImporter =  Console.readNonEmptyLine("Provide the data importer full class name of the plugin:","Providing the data importer full class name is mandatory.");
        if(option.equals("1")) {
            controller.registerInterviewPlugin(name, description, fullClassName,configFile,dataImporter);
        }else if(option.equals("2")){
            controller.registerRequirementPlugin(name, description, fullClassName,configFile,dataImporter);
        }
        System.out.println("Worked!");

        return false;
    }

    @Override
    public String headline() {
        return "Register New Interview Model/Requirement Specification Plugin";
    }
}

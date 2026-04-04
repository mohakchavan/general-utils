package io.github.mohakchavan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * This class is the entry point as this class contains the main method which runs the whole program.
 */
public class GeneralUtils {

    /**
     * Default constructor to create an object.
     */
    public GeneralUtils() {
    }

    private static final Logger log = LoggerFactory.getLogger(GeneralUtils.class);

    /**
     * Main method which runs the whole program and is called by the Java Virtual Machine (JVM).
     *
     * @param args Command-line arguments provided while running the program.
     * @throws Exception when any unhandled problem occurs in the code.
     */
    public static void main(String[] args) throws Exception {


        ApplicationProperties.load();
        ApplicationProperties properties = new ApplicationProperties();

        String activeProfile = properties.activeProfile();
        log.debug("activeProfile: {}", activeProfile);

        Helper helper = new Helper();
        MainMenu mainMenu = new MainMenu();
        boolean toContinue = true;

        while (toContinue) {
            try {
                new ClearConsole().clearConsole();
                mainMenu.showMainMenu();

                Scanner scanner = ConsoleInputOutput.getScanner();
                int featureNumber = scanner.nextInt();

                if (featureNumber >= 0) {
                    parseFeatureNumber(featureNumber);
                } else if (featureNumber == -1) {
                    ConsoleInputOutput.write("\nExiting...");
                    toContinue = false;
                    exitSystem(helper, properties);
                } else {
                    incorrectFeatureNumber();
                }
            } catch (ExitException e) {
                if (!e.isContinueMainLoop()) {
                    toContinue = false;
                    exitSystem(helper, properties);
                } else {
                    sleep();
                }
            }
        }
    }

    private static void exitSystem(Helper helper, ApplicationProperties properties) {
        if (properties.isFileLogEnabled() && !helper.isStringEmpty(properties.getLogFile())) {
            ConsoleInputOutput.write("\nDebug logs for more details can be found in " +
                    "\"" + properties.getLogFile() + "\" file. Program will now exit.");
        }
        System.exit(0);
    }

    private static void parseFeatureNumber(int featureNumber) {
        switch (featureNumber) {
            case 1: {
                GithubFeature githubFeature = new GithubFeature();
                githubFeature.startGithubFeature();
                break;
            }

            default: {
                incorrectFeatureNumber();
            }
        }
    }

    private static void incorrectFeatureNumber() {
        ConsoleInputOutput.write("\nPlease enter correct feature number.");
        sleep();
    }

    private static void sleep() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            ConsoleInputOutput.write("Some error occurred while thread sleep. Program will now exit.");
            if (e.getMessage() != null) {
                ConsoleInputOutput.write("\nMessage: " + e.getMessage());
            }
            log.error("Exception while thread sleep.", e);
            throw new ExitException(false);
        }
    }
}

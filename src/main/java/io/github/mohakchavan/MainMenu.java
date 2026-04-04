package io.github.mohakchavan;

/**
 * This class is used to show the user all the available features, to select from, in the console.
 */
public class MainMenu {

    /**
     * Default constructor to create an object.
     */
    public MainMenu() {
    }

    /**
     * This method prints all the available features in the console.
     */
    public void showMainMenu() {

        ConsoleInputOutput.write("\nBelow are the features and their respective numbers");
        ConsoleInputOutput.write("1 -> GitHub Secret Encoder for GitHub REST API.");
        ConsoleInputOutput.write("-1 -> Exit.");

        ConsoleInputOutput.write("\nEnter single feature number:");

    }

}

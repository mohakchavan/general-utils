package io.github.mohakchavan;

import java.io.IOException;

/**
 * This class is used to clear the contents which are already present in the console
 * in which this shaded jar file is being run.
 */
public class ClearConsole {

    /**
     * Default constructor for {@link ClearConsole}.
     */
    public ClearConsole() {
        // This is default constructor.
        // Further implementations can be done in this constructor.
    }

    /**
     * This method clears the contents of the console.
     */
    public void clearConsole() {

        try {

            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            ConsoleInputOutput.write(System.lineSeparator().repeat(50));
        }
    }

}

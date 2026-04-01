package io.github.mohakchavan;

import java.io.IOException;

public class ClearConsole {

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

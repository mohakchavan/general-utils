package io.github.mohakchavan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.util.Scanner;

/**
 * This class is used to accept input from the user and provide the output/info to the user.
 */
public class ConsoleInputOutput {

    private static final Logger log = LoggerFactory.getLogger(ConsoleInputOutput.class);

    private Scanner scanner = null;

    private static ConsoleInputOutput consoleInputOutput = null;

    private ConsoleInputOutput() {
        Console console = System.console();
        if (console != null) {
            scanner = new Scanner(console.reader());
        } else {
            scanner = new Scanner(System.in);
        }
    }

    private static ConsoleInputOutput getInstance() {
        if (consoleInputOutput == null) {
            consoleInputOutput = new ConsoleInputOutput();
        }
        return consoleInputOutput;
    }

    /**
     * This method writes the provided {@code message} to console.
     *
     * @param message Message to be written to console.
     */
    public static void write(String message) {
        log.info(message);
    }

    /**
     * This method writes the formatted {@code message} with provided {@code args} to console.
     *
     * @param message Message to be written to console.
     * @param args    Arguments to be formatted in the provided {@code message}.
     */
    public static void write(String message, Object... args) {
        log.info(message, args);
    }

    /**
     * This method writes the provided {@code message} along with the provided {@code t} exception to console.
     *
     * @param message Message to be written to console.
     * @param t       Exception(Throwable) to be written to console.
     */
    public static void write(String message, Throwable t) {
        log.info(message, t);
    }

    /**
     * This method returns the {@link Scanner} object which can be used to accept inputs from the user.
     *
     * @return The {@link Scanner} object.
     */
    public static Scanner getScanner() {
        return getInstance().scanner;
    }

    public static void clear() {
        consoleInputOutput = null;
    }

}

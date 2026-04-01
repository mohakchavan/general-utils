package io.github.mohakchavan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.util.Scanner;

public class ConsoleInputOutput {

    private ConsoleInputOutput() {
    }

    private static final Logger log = LoggerFactory.getLogger(ConsoleInputOutput.class);

    public static void write(String message) {
        log.info(message);
    }

    public static void write(String message, Object... args) {
        log.info(message, args);
    }

    public static void write(String message, Throwable t) {
        log.info(message, t);
    }

    public static Scanner getScanner() {
        Console console = System.console();
        Scanner scanner;
        if (console != null) {
            scanner = new Scanner(console.reader());
        } else {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

}

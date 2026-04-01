package io.github.mohakchavan;

public class ExitException extends RuntimeException {

    private final boolean continueMainLoop;

    public ExitException() {
        super();
        continueMainLoop = true;
    }

    public ExitException(boolean continueMainLoop) {
        super();
        this.continueMainLoop = continueMainLoop;
    }

    public ExitException(String message) {
        super(message);
        continueMainLoop = true;
    }

    public boolean isContinueMainLoop() {
        return continueMainLoop;
    }
}

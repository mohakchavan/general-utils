package io.github.mohakchavan.generalutils;

import java.io.Serial;

/**
 * This {@link Exception} class is thrown when the program needs to be terminated
 * based on {@code continueMainLoop} parameter defined while creating the object.
 * <p>Default value of {@code continueMainLoop} is true.</p>
 */
public class ExitException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5446274904124100531L;

    /**
     * This variable represents whether this exception needs the program to be terminated or not.
     */
    private final boolean continueMainLoop;

    /**
     * Default constructor to create an object with default values.
     * <p>Default value of {@code continueMainLoop} is true.</p>
     */
    public ExitException() {
        super();
        continueMainLoop = true;
    }

    /**
     * This constructor creates the object with the provided {@code continueMainLoop} value.
     *
     * @param continueMainLoop determines whether to continue the program or terminate the program.
     */
    public ExitException(boolean continueMainLoop) {
        super();
        this.continueMainLoop = continueMainLoop;
    }

    /**
     * This constructor creates the object with the provided exception message.
     * <p>Default value of {@code continueMainLoop} is true.</p>
     *
     * @param message exception message
     */
    public ExitException(String message) {
        super(message);
        continueMainLoop = true;
    }

    /**
     * This method returns whether to continue the program or terminate the program.
     *
     * @return {@link Boolean} value of {@code continueMainLoop}.
     */
    public boolean isContinueMainLoop() {
        return continueMainLoop;
    }
}

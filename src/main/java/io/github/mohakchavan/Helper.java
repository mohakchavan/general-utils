package io.github.mohakchavan;

/**
 * This is a utility class.
 */
public class Helper {

    /**
     * Default construtor to create an object.
     */
    public Helper() {
        // This is default constructor.
        // Further implementations can be done in this constructor.
    }

    /**
     * This method checks whether if the provided {@link String} {@code value} is null or empty.
     *
     * @param value to be checked.
     * @return {@link Boolean} value.
     */
    public boolean isStringEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

}

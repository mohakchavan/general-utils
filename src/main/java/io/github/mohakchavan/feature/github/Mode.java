package io.github.mohakchavan.feature.github;

/**
 * This enum class provides the modes in which the {@link GithubFeature} needs to start.
 */
public enum Mode {

    /**
     * Specifies the text mode.
     */
    TEXT(1),

    /**
     * Specifies the file mode.
     */
    FILE(2);

    private final int modeValue;

    Mode(int modeValue) {
        this.modeValue = modeValue;
    }
}

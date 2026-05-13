package io.github.mohakchavan.feature.github;

public enum Mode {
    TEXT(1),
    FILE(2);

    private final int mode;

    Mode(int mode) {
        this.mode = mode;
    }
}

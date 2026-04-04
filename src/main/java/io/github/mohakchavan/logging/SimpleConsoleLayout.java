package io.github.mohakchavan.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

/**
 * This class provides the custom layout for logging in the console.
 */
public class SimpleConsoleLayout extends LayoutBase<ILoggingEvent> {

    /**
     * Default constructor to create an object.
     */
    public SimpleConsoleLayout() {
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        return event.getFormattedMessage() +
                CoreConstants.LINE_SEPARATOR;
    }
}

package io.github.mohakchavan.generalutils.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.tyler.TylerConfiguratorBase;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.LayoutBase;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import io.github.mohakchavan.generalutils.Helper;

/**
 * Custom Configurator Class for logging.
 * This class configures both the logging types i.e.: Console &amp; Debug(file) Logs.
 */
public class CustomLogConfigurator extends TylerConfiguratorBase implements Configurator {

    private final Helper helper;

    /**
     * Default constructor to initialize the parameters.
     */
    public CustomLogConfigurator() {
        this.helper = new Helper();
    }

    @Override
    public ExecutionStatus configure(LoggerContext loggerContext) {
        addInfo("Setting up CustomLogConfigurator");
        setContext(loggerContext);
        this.propertyModelHandlerHelper.handlePropertyModel(this, "", "", "",
                "application.properties", "");

        ConsoleAppender<ILoggingEvent> interactionAppender = setConsoleAppender(loggerContext);

        boolean isFileEnabled = Boolean.parseBoolean(subst("${customLogging.fileLogging.enabled}"));
        Appender<ILoggingEvent> devLogAppender;
        if (isFileEnabled) {
            devLogAppender = setFileAppender(loggerContext);
        } else {
            devLogAppender = setConsoleAppender(loggerContext, true);
        }

        Logger interactionLogger = setupLogger("io.github.mohakchavan.generalutils.ConsoleInputOutput", Level.TRACE, false);
        interactionLogger.addAppender(interactionAppender);

        String defaultLogLevel = subst("${customLogging.defaultLogLevel}");
        if (helper.isStringEmpty(defaultLogLevel)) {
            defaultLogLevel = Level.DEBUG.toString();
        }
        Logger devLogLogger = setupLogger("io.github.mohakchavan", defaultLogLevel);
        devLogLogger.addAppender(devLogAppender);

        setupLogger(org.slf4j.Logger.ROOT_LOGGER_NAME, Level.OFF);

        return ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY;
    }

    private ConsoleAppender<ILoggingEvent> setConsoleAppender(LoggerContext loggerContext) {
        return setConsoleAppender(loggerContext, false);
    }

    private ConsoleAppender<ILoggingEvent> setConsoleAppender(LoggerContext loggerContext, boolean isDevLogs) {
        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(loggerContext);
        consoleAppender.setName("CONSOLE");

        LayoutBase<ILoggingEvent> layout;
        if (isDevLogs) {
            layout = getCustomPatternLayout();
        } else {
            layout = new SimpleConsoleLayout();
        }
        layout.setContext(loggerContext);
        layout.start();

        LayoutWrappingEncoder<ILoggingEvent> wrappingEncoder = new LayoutWrappingEncoder<>();
        wrappingEncoder.setContext(loggerContext);
        wrappingEncoder.setLayout(layout);

        consoleAppender.setEncoder(wrappingEncoder);
        consoleAppender.start();
        return consoleAppender;
    }

    private FileAppender<ILoggingEvent> setFileAppender(LoggerContext loggerContext) {
        FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
        fileAppender.setContext(loggerContext);
        fileAppender.setName("FILE");
        fileAppender.setFile(subst("${customLogging.fileLogging.file}"));

        PatternLayout patternLayout = getCustomPatternLayout();
        patternLayout.setContext(loggerContext);
        patternLayout.start();

        LayoutWrappingEncoder<ILoggingEvent> wrappingEncoder = new LayoutWrappingEncoder<>();
        wrappingEncoder.setContext(loggerContext);
        wrappingEncoder.setLayout(patternLayout);

        fileAppender.setEncoder(wrappingEncoder);
        fileAppender.start();
        return fileAppender;
    }

    private PatternLayout getCustomPatternLayout() {
        PatternLayout patternLayout = new PatternLayout();
        String dateFormat = subst("${customLogging.dateTimeFormat}");
        if (helper.isStringEmpty(dateFormat)) {
            dateFormat = "HH:mm:ss.SSS";
        }
        // This pattern is taken from TTLLLayout while replacing the date format.
        patternLayout.setPattern("%d{" + dateFormat + "} [%thread] %-5level %logger{36} -%kvp- %msg%n");
        return patternLayout;
    }

    private Logger setupLogger(String loggerName, Level level) {
        return setupLogger(loggerName, level.toString());
    }

    private Logger setupLogger(String loggerName, Level level, boolean additivity) {
        return setupLogger(loggerName, level.toString(), additivity);
    }

    private Logger setupLogger(String loggerName, String levelString) {
        return setupLogger(loggerName, levelString, null);
    }
}

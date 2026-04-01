package io.github.mohakchavan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

    private static final Properties PROPERTIES = new Properties();

    private Helper helper = new Helper();

    public static void load() throws IOException {
        try (InputStream inputStream = ApplicationProperties.class.getClassLoader().
                getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public String getProperty(String propertyKey) {
        if (!helper.isStringEmpty(propertyKey)) {
            return PROPERTIES.getProperty(propertyKey);
        }
        return null;
    }

    public String applicationName() {
        return getProperty("application.name");
    }

    public String activeProfile() {
        return getProperty("profiles.active");
    }

    public boolean isFileLogEnabled() {
        return Boolean.parseBoolean(getProperty("customLogging.fileLogging.enabled"));
    }

    public String getLogFile() {
        return getProperty("customLogging.fileLogging.file");
    }

}

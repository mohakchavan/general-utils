package io.github.mohakchavan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used to get the value of the properties defined in the {@code application.properties} resource file.
 */
public class ApplicationProperties {

    private static final Properties PROPERTIES = new Properties();

    private final Helper helper;

    /**
     * Default constructor to initialize the parameters.
     */
    public ApplicationProperties() {
        this.helper = new Helper();
    }

    /**
     * This method loads the properties defined in the {@code application.properties} resource file
     * into the class variable.
     *
     * @throws IOException when the resource file is not present.
     */
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

    /**
     * This method returns the value of the provided property key.
     *
     * @param propertyKey the key whose value is needed to be returned.
     * @return The {@link String} value associated to the provided {@code propertyKey}.
     */
    public String getProperty(String propertyKey) {
        if (!helper.isStringEmpty(propertyKey)) {
            return PROPERTIES.getProperty(propertyKey);
        }
        return null;
    }

    /**
     * This method returns the name of the application.
     *
     * @return The {@link String} value associated to {@code application.name} key.
     */
    public String applicationName() {
        return getProperty("application.name");
    }

    /**
     * This method returns the profile which is currently active.
     *
     * @return The {@link String} value associated to {@code profiles.active} key.
     */
    public String activeProfile() {
        return getProperty("profiles.active");
    }

    /**
     * This method returns if the logging in the file is enabled or not.
     *
     * @return The {@link Boolean} value associated to {@code customLogging.fileLogging.enabled} key.
     */
    public boolean isFileLogEnabled() {
        return Boolean.parseBoolean(getProperty("customLogging.fileLogging.enabled"));
    }

    /**
     * This method returns the location of the debug log file.
     *
     * @return The {@link String} value associated to {@code customLogging.fileLogging.file} key.
     */
    public String getLogFile() {
        return getProperty("customLogging.fileLogging.file");
    }

}

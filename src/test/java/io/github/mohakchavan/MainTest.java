package io.github.mohakchavan;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

//@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class MainTest {

    private static final Properties PROPERTIES = new Properties();
    private static SimpleDateFormat simpleDateFormat;

    static {
        System.out.println("MainTest.static initializer");

        try {
            loadProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setDateFormatter();
    }

    /*@BeforeAll
    static void beforeAll() throws IOException {
        System.out.println("MainTest.beforeAll");
    }*/

    public static String getProperty(String propertyKey) {
        if (propertyKey == null || propertyKey.isEmpty()) {
            return null;
        }
        return PROPERTIES.getProperty(propertyKey);
    }

    public static void println(String message) {
        println(message, false);
    }

    public static void println(String message, boolean printThreadName) {
        System.out.println(
                simpleDateFormat.format(new Date()) + " :: " +
                        (printThreadName ? ("Thread: " + Thread.currentThread().getName() + " :: ") : "") +
                        message
        );
    }

    private static void loadProperties() throws IOException {
        try (InputStream inputStream = MainTest.class.getClassLoader().
                getResourceAsStream("test-application.properties")) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    private static void setDateFormatter() {
        String dateFormat = getProperty("custom.dateTimeFormat");
        try {
            if (dateFormat != null && !dateFormat.isEmpty()) {
                simpleDateFormat = new SimpleDateFormat(dateFormat);
            } else {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            }
        } catch (Exception e) {
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        }
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
    }

    /*@AfterAll
    static void afterAll() {
        System.out.println("MainTest.afterAll");
    }*/

}

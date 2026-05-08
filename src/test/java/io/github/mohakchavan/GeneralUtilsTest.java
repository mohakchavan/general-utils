package io.github.mohakchavan;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
public class GeneralUtilsTest extends MainTest {

    @Test
    void testIfProgramExistsWithoutRunningAnyFeature() {

        SystemExit systemExit = mock(SystemExit.class);

        InputStream originalInputStream = System.in;
        String input = "-1";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            new GeneralUtils(systemExit).executeMainMethod(new String[]{});
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        verify(systemExit).exit(0);
        System.setIn(originalInputStream);

    }
}
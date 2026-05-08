package io.github.mohakchavan;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Mockito.*;

@Order(2)
public class GitHubFeatureTest extends MainTest {

    @Test
    @Disabled
    void testProgramEncodesSecretValue() {

        SystemExit systemExit = mock(SystemExit.class);

        InputStream originalInputStream = System.in;
        String input = "1" +
                System.lineSeparator() +
                "errtrytryty" +
                System.lineSeparator() +
                "dfgdfgerwerwersdfsdfsdf" +
                System.lineSeparator() +
                System.lineSeparator() +
                "-1";

        println("Provided Input (System.lineSeparator() is replaced by \" | \"):\n" + input.replaceAll(System.lineSeparator(), " | "));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);

        GeneralUtils generalUtils = new GeneralUtils(systemExit);
        try {
            generalUtils.executeMainMethod(new String[]{});
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        verify(systemExit, times(1)).exit(0);
        System.setIn(originalInputStream);
    }

    @Test
    void testInvalidPublicKey() {

        SystemExit systemExit = mock(SystemExit.class);

        InputStream originalInputStream = System.in;
        String input = "1" +
                System.lineSeparator() +
                "eretertertert" +
                System.lineSeparator() +
                "dfgdfgerwerwersdfsdfsdf" +
                System.lineSeparator() +
                System.lineSeparator() +
                "-1";

        println("Provided Input (System.lineSeparator() is replaced by \" | \"):\n" + input.replaceAll(System.lineSeparator(), " | "));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(byteArrayInputStream);

        GeneralUtils generalUtils = new GeneralUtils(systemExit);
        try (MockedConstruction<ExitException> mockedConstruction = Mockito.mockConstruction(ExitException.class)) {
            generalUtils.executeMainMethod(new String[]{});

            println("" + mockedConstruction.constructed().size());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        verify(systemExit, times(1)).exit(0);
        System.setIn(originalInputStream);
        println("end");
    }

}

package io.github.mohakchavan.feature.github;

import com.goterl.lazysodium.LazySodiumJava;
import com.goterl.lazysodium.SodiumJava;
import com.goterl.lazysodium.exceptions.SodiumException;
import com.goterl.lazysodium.utils.Base64MessageEncoder;
import com.goterl.lazysodium.utils.Key;
import com.goterl.lazysodium.utils.LibraryLoader;
import io.github.mohakchavan.ClearConsole;
import io.github.mohakchavan.ConsoleInputOutput;
import io.github.mohakchavan.ExitException;
import io.github.mohakchavan.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Base64;
import java.util.Scanner;

/**
 * This class provides all the methods which are used for the features related to GitHub.
 */
public class GithubFeature {

    private static final Logger log = LoggerFactory.getLogger(GithubFeature.class);

    private final Scanner scanner;
    private final Helper helper;

    /**
     * Default constructor to create an object with default values.
     */
    public GithubFeature() {
        scanner = ConsoleInputOutput.getScanner();
        helper = new Helper();
    }


    public void startGithubFeature() {
        startGithubFeature(Mode.TEXT);
    }

    public void startGithubFeature(Mode mode) {

        new ClearConsole().clearConsole();
        ConsoleInputOutput.write("\nRunning GitHub feature...");

        showProceedInfo();

        String publicKey = acquirePublicKey();

        String secret = null;
        if (mode == Mode.TEXT) {
            secret = acquireSecret();
        } else if (mode == Mode.FILE) {
            secret = acquireFileSecret();
        } else {

        }

        if (helper.isStringEmpty(secret)) {
            ConsoleInputOutput.write("Secret value cannot be null or empty.");
            log.warn("Secret value is null or empty.");
            throw new ExitException();
        }

        String encryptedSecret = encryptSecretWithKey(secret, publicKey);

        if (mode == Mode.TEXT) {
            showEncryptedSecret(encryptedSecret);
        } else if (mode == Mode.FILE) {
//            showEncryptedSecret(encryptedSecret);
            writeEncryptedSecret(encryptedSecret);
        }

        waitAndExitFeature();
    }

    private void showProceedInfo() {
        ConsoleInputOutput.write("Before proceeding, please acquire the public key details (id & value) by calling " +
                "\"/repos/{owner}/{repo}/actions/secrets/public-key\" GitHub's REST API. Press Enter to proceed...");

        scanner.nextLine();
    }

    private String acquirePublicKey() {
        ConsoleInputOutput.write("\nEnter Public Key (without quotes):");
        String publicKey = scanner.nextLine();
        publicKey = publicKey.trim();
        return publicKey;
    }

    private String acquireSecret() {
        ConsoleInputOutput.write("\nEnter Secret Value:");
        String secret = scanner.nextLine();
        secret = secret.trim();
        return secret;
    }

    private String acquireFileSecret() {
        ConsoleInputOutput.write("\nEnter file-path including the file to be encrypted:");
        String filePath = scanner.nextLine();
        if (helper.isStringEmpty(filePath)) {
            ConsoleInputOutput.write("Path of the file cannot be null or empty.");
            log.warn("Path of file is null or empty.");
            throw new ExitException();
        }
        File file = new File(filePath);
        if (!file.exists()) {
            ConsoleInputOutput.write("Provided file path does not exists.");
            log.warn("file path does not exists.");
            throw new ExitException();
        }
        if (!file.isFile()) {
            ConsoleInputOutput.write("Provided file path is not a file.");
            log.warn("file path is not a file.");
            throw new ExitException();
        }
        if (!file.canRead()) {
            ConsoleInputOutput.write("Provided file path does not have read permission.");
            log.warn("file path does not have read permission.");
            throw new ExitException();
        }
        byte[] fileBytes = readBytesFromFile(file);
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    private byte[] readBytesFromFile(File fileToRead) {
        ConsoleInputOutput.write("\nReading the file...");
        try (FileInputStream fileInputStream = new FileInputStream(fileToRead)) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int readBytes;

            while ((readBytes = fileInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, readBytes);
            }
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            ConsoleInputOutput.write("Problem in reading file. Will exit now.");
            log.error("Problem in reading file.", e);
            throw new ExitException(false);
        }
    }

    private String encryptSecretWithKey(String secret, String publicKey) {
        ConsoleInputOutput.write("\nEncrypting the secret...");
        String encrypted = null;

        byte[] keyBytes = decodePublicKey(publicKey);
        LazySodiumJava lazySodiumJava = new LazySodiumJava(
                new SodiumJava(LibraryLoader.Mode.PREFER_BUNDLED),
                new Base64MessageEncoder());

        try {
            encrypted = lazySodiumJava.cryptoBoxSealEasy(secret, Key.fromBytes(keyBytes));
        } catch (SodiumException e) {
            ConsoleInputOutput.write("Error occured while encrypting secret value. Message: " + e.getMessage());
            log.error("Cannot encrypt secret value. Stacktrace: ", e);
            throw new ExitException();
        }
        return encrypted;
    }

    private void showEncryptedSecret(String encrypted) {
        ConsoleInputOutput.write("\nUse following encrypted secret, along with public key id," +
                " as a request body for \"/repos/{owner}/{repo}/actions/secrets/{secret_name}\" GitHub's REST API:" +
                "\n" + encrypted + "\n");
    }

    private void writeEncryptedSecret(String encrypted) {
        ConsoleInputOutput.write("\nWriting encrypted secret to file...");
        File outputSecret = new File("encryptedSecret.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputSecret))) {
            bufferedWriter.write(encrypted);
            bufferedWriter.flush();

            ConsoleInputOutput.write("Encrypted secret written to file: \"" + outputSecret.getAbsolutePath() + "\"");
            ConsoleInputOutput.write("Use the encrypted secret written to above file, along with public key id," +
                    " as a request body for \"/repos/{owner}/{repo}/actions/secrets/{secret_name}\" GitHub's REST API.");
        } catch (IOException e) {
            ConsoleInputOutput.write("Some problem in writing encrypted secret to file.");
            ConsoleInputOutput.write("Showing encrypted secret here.");
            showEncryptedSecret(encrypted);
        }
    }

    private void waitAndExitFeature() {
        ConsoleInputOutput.write("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private byte[] decodePublicKey(String publicKey) {
        try {
            return Base64.getDecoder().decode(publicKey);
        } catch (IllegalArgumentException e) {
            ConsoleInputOutput.write("Error occurred while decoding public key." +
                    "\nMessage: " + e.getMessage());
            log.error("Invalid Base64 public key. Stacktrace: ", e);
            throw new ExitException();
        }
    }

}

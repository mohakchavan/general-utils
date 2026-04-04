package io.github.mohakchavan;

import com.goterl.lazysodium.LazySodiumJava;
import com.goterl.lazysodium.SodiumJava;
import com.goterl.lazysodium.exceptions.SodiumException;
import com.goterl.lazysodium.utils.Base64MessageEncoder;
import com.goterl.lazysodium.utils.Key;
import com.goterl.lazysodium.utils.LibraryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.Scanner;

/**
 * This class provides all the methods which are used for the features related to GitHub.
 */
public class GithubFeature {

    private static final Logger log = LoggerFactory.getLogger(GithubFeature.class);

    private final Scanner scanner;

    /**
     * Default constructor to create an object with default values.
     */
    public GithubFeature() {
        scanner = ConsoleInputOutput.getScanner();
    }

    /**
     * This method starts the GitHub feature.
     */
    public void startGithubFeature() {

        new ClearConsole().clearConsole();
        ConsoleInputOutput.write("\nRunning GitHub feature...");

        showProceedInfo();

        String publicKey = acquirePublicKey();

        String secret = acquireSecret();

        encryptSecretWithKey(secret, publicKey);
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

    private void encryptSecretWithKey(String secret, String publicKey) {
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
        ConsoleInputOutput.write("\nUse following encoded secret, along with public key id," +
                " as a request body for \"/repos/{owner}/{repo}/actions/secrets/{secret_name}\" GitHub's REST API:" +
                "\n" + encrypted + "\n");

        ConsoleInputOutput.write("Press Enter to continue...");
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

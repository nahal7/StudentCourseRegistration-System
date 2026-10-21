
package security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final int ITERATIONS = 600_000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    public static String hashPassword(char[] password) {

        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);

        byte[] hash = deriveKey(password, salt);

        return ITERATIONS + ":"
                + Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verifyPassword(
            char[] password, String storedValue) {

        if (storedValue == null) {
            return false;
        }

        try {
            String[] parts = storedValue.split(":");

            if (parts.length != 3) {
                return false;
            }

            int iterations = Integer.parseInt(parts[0]);

            if (iterations < 1 || iterations > ITERATIONS) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expectedHash =
                    Base64.getDecoder().decode(parts[2]);

            if (salt.length != SALT_LENGTH
                    || expectedHash.length != KEY_LENGTH / 8) {
                return false;
            }

            byte[] actualHash =
                    deriveKey(password, salt, iterations);

            return java.security.MessageDigest.isEqual(
                    expectedHash, actualHash);

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] deriveKey(char[] password, byte[] salt) {
        return deriveKey(password, salt, ITERATIONS);
    }

    private static byte[] deriveKey(
            char[] password, byte[] salt, int iterations) {

        PBEKeySpec spec =
                new PBEKeySpec(password, salt, iterations, KEY_LENGTH);

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            return factory.generateSecret(spec).getEncoded();

        } catch (GeneralSecurityException e) {
            throw new IllegalStateException(
                    "Password hashing is unavailable.", e);

        } finally {
            spec.clearPassword();
        }
    }
}

/*
 * Multiline comment at the top of the file.
 */
package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * The purpose of this class is to generate encrypted passwords from a passed
 * String.
 *
 * @author Kaleb
 * @version 2019-04-15
 */
public class PasswordGenerator {

    /**
     * This method is responsible for creating the SHA-512 password and returning
     * it to the calling program.
     *
     * @param thePassword : This is the password to be encrypted.
     * @param theSalt : This is the random salt that increases the encryption complexity.
     * @return : A new encrypted password in the form of a String.
     */
    public static String getSHA512Password(String thePassword, byte[] theSalt) {
        String generatedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(theSalt);

            // Converts password into a string of bytes 
            byte[] bytes = messageDigest.digest(thePassword.getBytes());
            
            // Converting back to string
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = stringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
        return generatedPassword;
    }

    /**
     * The purpose of this method is to generate a random salt to increase the complexity
     * of the encrypted password.
     *
     * @return : a byte[] that contains the random salt.
     * @throws NoSuchAlgorithmException : In the event that the algorithm is not found.
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }
}

package Model;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class passTester {

    public static void main (String[] args) throws NoSuchAlgorithmException {

        DataBaseTools dataBaseTools = new DataBaseTools();
        System.out.println(dataBaseTools.getCurrentAccountBalance());
//        String password = "simple";
//        byte[] salt = PasswordGenerator.getSalt();
//        System.out.printf("password: %s%n", PasswordGenerator.getSHA512Password(password, salt));
//        System.out.printf("password: %s%n", PasswordGenerator.getSHA512Password(password, salt));
//        System.out.printf("password: %s%n", PasswordGenerator.getSHA512Password(password, salt));
        }
}

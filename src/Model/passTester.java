package Model;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;

public class passTester {

    public static void main (String[] args) throws SQLException {
//
        DataBaseTools dataBaseTools = new DataBaseTools();

        System.out.println(dataBaseTools.getTheAccounts());
        }
}

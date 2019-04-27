package Model;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;

public class passTester {

    public static void main (String[] args) throws SQLException {
        DataBaseTools db = new DataBaseTools();
        db.getTheTransactionList("SELECT * FROM transactions");
        db.getTheBudget();
        System.out.println(db.getMyCashItems());
    }
}

package Model;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;

public class passTester {

    public static void main (String[] args) throws SQLException {
        DataBaseTools dbTools = new DataBaseTools();
        dbTools.getTheTransactionList("SELECT * FROM transactions");
        System.out.println("Total period one income: " + DataBaseTools.TOTAL_PERIOD_ONE_INCOME);
        System.out.println("Total period two income: " + DataBaseTools.TOTAL_PERIOD_TWO_INCOME);
        System.out.println("Total Period two spending: " + DataBaseTools.TOTAL_PERIOD_SPENDING);
        }
}

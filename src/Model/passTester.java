package Model;

import javafx.beans.binding.ObjectExpression;

import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class passTester {

    public static void main (String[] args) throws SQLException {
        DataBaseTools db = new DataBaseTools();
        String sql = "SELECT * FROM transactions ORDER BY itemId DESC";

        db.getTheTransactionList(sql);
        Double p1 = db.getMyTotalPeriodOneIncome();
        Double p2 = db.getMyTotalPeriodTwoIncome();

        System.out.println("Period 1: " + p1);
        System.out.println("Period 2: " + p2);
    }
}

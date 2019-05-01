/*
 * Multiline comment at the top of the file.
 */
package Model;

import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The purpose of this class is to house all of the methods
 * that handle the communication with the database so as to keep
 * them all in one place for easier maintenance.
 *
 * @author : Kaleb
 * @version : 2019-04-14
 */
public class DataBaseTools {

    /**
     *
     */
    private  Double myTotalCurrentBudgetAmount = 0.0;

    /**
     *
     */
    private  Double myBudgetAmount = 0.0;

    /**
     *
     */
    private  Double myTotalMonthlyExpenses = 0.0;

    /**
     *
     */
    private  Double myTotalMonthlyIncome = 0.0;

    /**
     *
     */
    private  Double myTotalPeriodOneIncome = 0.0;

    /**
     *
     */
    private  Double myTotalPeriodTwoIncome = 0.0;

    /**
     *
     */
    private  Double myTotalPeriodOneSpending = 0.0;

    /**
     *
     */
    private  Double myTotalPeriodTwoSpending = 0.0;

    /**
     *
     */
    private  Integer myCurrentPeriod = 0;

    /**
     *
     */
    private  Double myCashItems = 0.0;

    /**
     *
     */
    private  Double myAllIncome = 0.0;

    /**
     *
     */
    private  Double myAllExpenses = 0.0;



    /**
     * This method is responsible for getting the connection to the db,
     * and preparing the SQL statements.
     *
     * @param theSql : The statement that is going to be prepared
     * @return : PreparedStatement that the calling program manipulates
     */
    private PreparedStatement manageConnection(String theSql) {
        // Set the period every time the database is interacted with
        if (LocalDate.now().getDayOfMonth() < 15) {
            myCurrentPeriod = 1;
        } else {
            myCurrentPeriod = 2;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Get the connection
            connection = DriverManager.getConnection("jdbc:mysql://162.241.219.194/kalebsc1_MyBlueDataBase", "kalebsc1_theBoss", "Cassandra1$");

            // prepare the statement
            preparedStatement = connection.prepareStatement(theSql);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return preparedStatement;
    }

    /**
     * This method is responsible for checking if the use is in the
     * database.
     *
     * @param theUserName : The user name to be checked.
     * @param thePassword : The password to be checked.
     * @throws SQLException : In the event that something goes wrong.
     * @return : boolean indicating if the user is in the db or not.
     */
    public boolean checkIfUserInDb(String theUserName, String thePassword) throws SQLException {
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        String dbPassword = "";
        byte[] salt = null;
        boolean result = false;

        // Get the string
        String sql = "SELECT userPassword, salt FROM users WHERE userName = ?";

        // Prepare the statement
        preparedStatement = manageConnection(sql);

        // Bind the question mark to the actual field
        preparedStatement.setString(1, theUserName);

        // Execute query
        resultSet = preparedStatement.executeQuery();

        // extract the password and the salt from the resultset
        while (resultSet.next()) {
            dbPassword = resultSet.getString("userPassword");

            // Get the binary set of data
            Blob blob = resultSet.getBlob("salt");

            // Convert the salt
            int blobLength = (int) blob.length();
            salt = blob.getBytes(1, blobLength);
        }

        // Convert the password given by user into an encrypted password using the
        // salt from the database
        String userPassword = PasswordGenerator.getSHA512Password(thePassword, salt);

        // Finally compare the passwords, and if true change scene
        if (userPassword.equals(dbPassword)) {
            result = true;
        }
        // If they do not match, update error message
        else {
            result = false;
        }
        // Close things out
        preparedStatement.close();
        resultSet.close();

        return result;
    }

    /**
     * This method queries the remote MySQL database and retrieves the account balance.
     *
     * @throws SQLException : In the event that something goes wrong.
     * @return : double which is a representation of the account balance in the db.
     */
    public double getCurrentAccountBalance(int theChosenAccount) throws SQLException{
        String accountString = Integer.toString(theChosenAccount);
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        double returnResult = -1.0;
        String sql = "SELECT balance FROM accounts WHERE itemId = ?";
        preparedStatement = manageConnection(sql);

        preparedStatement.setString(1, accountString);

        result = preparedStatement.executeQuery();

        while (result.next()) {
            returnResult = result.getDouble("balance");
        }
        // close things out
        preparedStatement.close();
        result.close();

        return returnResult;
    }

    /**
     * This method queries the remote MySQL database and retrieves the account type.
     *
     * @throws SQLException : In the event that something goes wrong.
     * @return : double which is a representation of the account balance in the db.
     */
    public String getAccountType(int theChosenAccount) throws SQLException{
        String accountString = Integer.toString(theChosenAccount);
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String returnResult = "";
        String sql = "SELECT type FROM accounts WHERE itemId = ?";
        preparedStatement = manageConnection(sql);
        preparedStatement.setString(1, accountString);
        result = preparedStatement.executeQuery();

        while (result.next()) {
            returnResult = result.getString("type");
        }
        // close things out
        preparedStatement.close();
        result.close();

        return returnResult;
    }

    /**
     * This method creates
     *
     * @return
     * @throws SQLException
     */
    public ObservableList<Transaction> getTheTransactionList(String theSQL) throws SQLException{

        // List of transactions
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        // Gets connection and prepares the SQL statement
        preparedStatement = manageConnection(theSQL);

        // Execute the query
        result = preparedStatement.executeQuery();

        while (result.next()) {

            // Create a new transaction
            Transaction newTransaction = new Transaction(result.getDate("dateOfTransaction").toLocalDate(),
                    result.getString("purchaser"), result.getString("vendor"),
                    result.getString("description"), result.getString("category"),
                    result.getDouble("amount"), result.getDouble("accountBalance"),
                    result.getInt("accountID"));
            if (!newTransaction.getMyCategory().equals("medical & dental")) {
                myAllExpenses += newTransaction.getMyAmount();
            }
            if (newTransaction.getMyCategory().equals("income")) {
                myAllIncome += newTransaction.getMyAmount();
            }
            if (newTransaction.getMyTransactionDate().getMonth() == LocalDate.now().getMonth()) {
                if (newTransaction.getMyTransactionDate().getDayOfMonth() < 15) {
                    if (!newTransaction.getMyCategory().equals("income")) {
                        myTotalPeriodOneSpending += java.lang.Math.abs(newTransaction.getMyAmount());
                    }
                    if (newTransaction.getMyCategory().equals("income")) {
                        myTotalPeriodOneIncome += newTransaction.getMyAmount();
                    }
                }
                if (newTransaction.getMyTransactionDate().getDayOfMonth() >= 15) {
                    if (!newTransaction.getMyCategory().equals("income")) {
                        myTotalPeriodTwoSpending += java.lang.Math.abs(newTransaction.getMyAmount());
                    }
                    if (newTransaction.getMyCategory().equals("income")) {
                        myTotalPeriodTwoIncome += newTransaction.getMyAmount();
                    }
                }
            }
            // Set the ID
            newTransaction.setMyTransactionId(result.getInt("itemId"));

            // Add to the list to be returned
            transactions.add(newTransaction);
        }
        // Set total monthly income
        myTotalMonthlyIncome = myTotalPeriodOneIncome + myTotalPeriodTwoIncome;

        // Close things out
        preparedStatement.close();
        result.close();

        return transactions;
    }

    /**
     * This method updates the account information in the database.
     *
     * @param theItem : The name of the budget item user is looking for.
     * @return : Returns a double of the value of that budget item.
     */
    public Double getBudgetItemValue(String theItem) {
        ResultSet result = null;
        Double returnResult = 0.0;
        PreparedStatement preparedStatement = null;
        String sql = "SELECT currentValue FROM budget WHERE itemName = ?";
        preparedStatement = manageConnection(sql);
        try {
            preparedStatement.setString(1, theItem);
            // Do the update
            result = preparedStatement.executeQuery();
            returnResult = result.getDouble("currentValue");

            // Close things out
            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return returnResult;
    }

    /**
     *
     * @param theDateLastPaid
     * @param theItemName
     * @param theCurrentV
     * @param theBudgetedV
     * @param theExpectedM
     * @param theDueDate
     * @param theNotes
     */
    public void insertBudgetItem(String sql, LocalDate theDateLastPaid, String theItemName, Double theCurrentV, Double theBudgetedV, Double theExpectedM, LocalDate theDueDate, String theNotes) {
        PreparedStatement preparedStatement = null;
        preparedStatement = manageConnection(sql);

        Date dueDate = Date.valueOf(theDueDate);
        Date dateLastPaid = Date.valueOf(theDateLastPaid);

        try {
            preparedStatement.setDate(1, dateLastPaid);
            preparedStatement.setString(2, theItemName);
            preparedStatement.setDouble(3, theCurrentV);
            preparedStatement.setDouble(4, theBudgetedV);
            preparedStatement.setDouble(5, theExpectedM);
            preparedStatement.setDate(6, dueDate);
            preparedStatement.setString(7, theNotes);
            // Do the update
            preparedStatement.executeUpdate();

            // close things out
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method creates
     *
     * @return
     * @throws SQLException
     */
    public ObservableList<Budget> getTheBudget() throws SQLException{

        // List of transactions
        ObservableList<Budget> budget = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        // Getting everything from the table
        String sql = "SELECT * FROM budget";

        // Gets connection and prepares the SQL statement
        preparedStatement = manageConnection(sql);

        // Execute the query
        result = preparedStatement.executeQuery();
        String[] cashItems= {"groceries", "allowance", "fuel", "home needs", "date night", "wishlist"};
        while (result.next()) {

            // Create a new transaction
            // Null pointer exception is handles in the event that something is missing in the budget.
            try {
                Budget newBudget = new Budget(result.getDate("dateLastPaid").toLocalDate(),
                        result.getString("itemName"), result.getDouble("currentValue"),
                        result.getDouble("budgetedValue"), result.getDouble("expectedMonthlyValue"),
                        result.getDate("dueDate").toLocalDate(), result.getString("itemNotes"));

                // Populate the cash withdrawal field in the view data view
                String name = newBudget.getMyItemName();
                for (String item: cashItems) {
                    if (name.equals(item)) {
                        myCashItems += newBudget.getMyCurrentValue();
                    }
                }
                // Get the totals for the fields
                myTotalCurrentBudgetAmount += newBudget.getMyCurrentValue();
                myBudgetAmount += newBudget.getMyBudgetedValue();
                if (!newBudget.getMyItemName().equals("medical & dental")) {
                    myTotalMonthlyExpenses += newBudget.getMyExpectedMonthly();
                }

                // Set the ID
                newBudget.setMyItemId(result.getInt("itemId"));

                // Add to the list to be returned
                budget.add(newBudget);
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            }
        }
        // Close things out
        preparedStatement.close();
        result.close();

        return budget;
    }


    /**
     * This method creates
     *
     * @return
     * @throws SQLException
     */
    public ObservableList<Account> getTheAccounts() throws SQLException{

        // List of transactions
        ObservableList<Account> account = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        // Getting everything from the table
        String sql = "SELECT * FROM accounts";

        // Gets connection and prepares the SQL statement
        preparedStatement = manageConnection(sql);

        // Execute the query
        result = preparedStatement.executeQuery();

        while (result.next()) {

            // Create a new transaction
            // Null pointer exception is handles in the event that something is missing in the budget.
            try {
                Account newAccount = new Account(result.getDouble("balance"), result.getString("type"));

                // Set the ID
                newAccount.setMyAccountId(result.getInt("itemId"));

                // Add to the list to be returned
                account.add(newAccount);
            } catch (NullPointerException e) {
                System.err.println(e.getMessage());
            }
        }
        // Close things out
        preparedStatement.close();
        result.close();

        return account;
    }

    /**
     *
     * @param sql
     * @param theId
     * @param theDate
     * @param theP
     * @param theV
     * @param theDesc
     * @param theCat
     * @param theAmount
     * @param theBal
     * @param theAccId
     * @throws SQLException
     */
    public void updateTransactionInDb(String sql, Integer theId, LocalDate theDate, String theP, String theV, String theDesc, String theCat, Double theAmount, Double theBal, Integer theAccId) throws SQLException{
        PreparedStatement preparedStatement = null;

        preparedStatement = manageConnection(sql);

        Date date = Date.valueOf(theDate);

        preparedStatement.setDate(1, date);
        preparedStatement.setString(2, theP);
        preparedStatement.setString(3, theV);
        preparedStatement.setString(4, theDesc);
        preparedStatement.setString(5, theCat);
        preparedStatement.setDouble(6, theAmount);
        preparedStatement.setDouble(7, theBal);
        preparedStatement.setInt(8, theAccId);
        preparedStatement.setInt(9, theId);

        // Do the update
        preparedStatement.executeUpdate();

        // close things out
        preparedStatement.close();

    }

    /**
     * This method is responsible for adding transactions into the database.
     *
     * @param sql : This is some sql passed to it.
     * @param theList : This is the list to be added.
     */
    public void insertList(String sql, List<Transaction> theList) {
        PreparedStatement preparedStatement = null;

        Connection connection = null;

        try {
            // Get the connection
            connection = DriverManager.getConnection("jdbc:mysql://162.241.219.194/kalebsc1_MyBlueDataBase", "kalebsc1_theBoss", "Cassandra1$");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


        try {
            for (Transaction trans: theList) {
                preparedStatement = connection.prepareStatement(sql);
                Date date = Date.valueOf(trans.getMyTransactionDate());
                preparedStatement.setDate(1, date);
                preparedStatement.setString(2, trans.getMyPurchaser());
                preparedStatement.setString(3, trans.getMyVendor());
                preparedStatement.setString(4, trans.getMyDescription());
                preparedStatement.setString(5, trans.getMyCategory());
                preparedStatement.setDouble(6, trans.getMyAmount());
                preparedStatement.setDouble(7, trans.getMyBalanceAfter());
                preparedStatement.setInt(8, trans.getMyAccountFrom());
                // Enter the transaction
                preparedStatement.execute();

                // account update
                preparedStatement = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE itemId = ?");
                preparedStatement.setDouble(1, trans.getMyAmount());
                preparedStatement.setInt(2,trans.getMyAccountFrom());

                preparedStatement.executeUpdate();

//                // budget update
                preparedStatement = connection.prepareStatement("UPDATE budget SET currentValue = ? WHERE itemName = ?");
                preparedStatement.setDouble(1, getBudgetItemValue(trans.getMyCategory()) - trans.getMyAmount());
                preparedStatement.setString(2, trans.getMyCategory());
//
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method updates the account information in the database.
     *
     * @param theId : The ID to be checked for updating.
     * @param theCharge : The new account charge.
     */
    public void applyChargeToAccount(String theType, Integer theId, Double theCharge) {
        // Get the current balance in that account
        Double balance = 0.0;
        try {
            balance = getCurrentAccountBalance(theId);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE accounts SET balance = ? WHERE itemId = ?";
        preparedStatement = manageConnection(sql);
        try {
            if (!theType.equals("income")) {
                preparedStatement.setDouble(1, balance - theCharge);
                preparedStatement.setDouble(2, theId);
            } else {
                preparedStatement.setDouble(1, balance + theCharge);
                preparedStatement.setDouble(2, theId);
            }
            // Do the update
            preparedStatement.executeUpdate();

            // Close things out
            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method updates the account information in the database.
     *
     * @param  theCategory: The ID to be checked for updating.
     * @param theCharge : The new account charge.
     */
    public void applyChargeToBudget(String theCategory, Double theCharge) {
        // Get the current balance in that account
        Double currentValue = getBudgetItemValue(theCategory);

        PreparedStatement preparedStatement = null;
        String sql = "UPDATE budget SET currentValue = ? WHERE itemName = ?";
        preparedStatement = manageConnection(sql);
        try {
            preparedStatement.setDouble(1, currentValue - theCharge);
            preparedStatement.setString(2, theCategory);

            preparedStatement.execute();

            // Close things out
            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method is responsible for updating the budget in the db.
     *
     * @param theId : The id that may be changed
     * @param theDateLastPaid : The new date the item was last paid.
     * @param theItemName : The new item name
     * @param theCurrentV : The new current value of the item.
     * @param theBudgetedV : The new budgeted value of the item
     * @param theExpectedM : The new expected monethly value
     * @param theDueDate : The new due date
     * @param theNotes : The new notes for the item.
     */
    public void updateBudgetItemInDb(int theId, LocalDate theDateLastPaid,
                                     String theItemName, Double theCurrentV,
                                     Double theBudgetedV, Double theExpectedM,
                                     LocalDate theDueDate, String theNotes) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE budget SET dateLastPaid = ?, itemName = ?, currentValue = ?, budgetedValue = ?, expectedMonthlyValue = ?, dueDate = ?, itemNotes = ? WHERE itemId = ?";
        preparedStatement = manageConnection(sql);

        // Convert the dates into dates SQL can understand
        Date dueDate = Date.valueOf(theDueDate);
        Date dateLastPaid = Date.valueOf(theDateLastPaid);

        try {
            preparedStatement.setDate(1, dateLastPaid);
            preparedStatement.setString(2, theItemName);
            preparedStatement.setDouble(3, theCurrentV);
            preparedStatement.setDouble(4, theBudgetedV);
            preparedStatement.setDouble(5, theExpectedM);
            preparedStatement.setDate(6, dueDate);
            preparedStatement.setString(7, theNotes);
            preparedStatement.setInt(8, theId);

            // Do the update
            preparedStatement.executeUpdate();

            // Close things out
            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     * This method updates the account information in the database.
     *
     * @param theId : The ID to be checked for updating.
     * @param theAccountBalance : The new account balance.
     * @param theAccountType : The new account type.
     */
    public void updateAccountInDb(int theId, Double theAccountBalance, String theAccountType) {
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE accounts SET balance = ?, type = ? WHERE itemId = ?";
        preparedStatement = manageConnection(sql);

        try {
            preparedStatement.setDouble(1, theAccountBalance);
            preparedStatement.setString(2, theAccountType);
            preparedStatement.setInt(3, theId);

            // Do the update
            preparedStatement.executeUpdate();

            // Close things out
            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method updates the account information in the database.
     *
     * @param theAccountBalance : The new account balance.
     * @param theAccountType : The new account type.
     */
    public void insertAccount(String sql, Double theAccountBalance, String theAccountType) {
        PreparedStatement preparedStatement = null;
        preparedStatement = manageConnection(sql);

        try {
            preparedStatement.setDouble(1, theAccountBalance);
            preparedStatement.setString(2, theAccountType);

            // Do the update
            preparedStatement.executeUpdate();

            // Close things out
            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    /**
     *
     * @param theId
     * @param theTable
     * @param theIdName
     */
    public void deleteFromDb(Integer theId, String theTable, String theIdName) {
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM " + theTable + " WHERE " + theIdName + " = ?";
        preparedStatement = manageConnection(sql);

        try {
            preparedStatement.setInt(1, theId);

            // Do the update
            preparedStatement.executeUpdate();

            // Close things out
            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Double getMyAllIncome() {
        return myAllIncome;
    }

    public Double getMyAllExpenses() {
        return myAllExpenses;
    }

    public Double getMyTotalCurrentBudgetAmount() {
        return myTotalCurrentBudgetAmount;
    }

    public Double getMyBudgetAmount() {
        return myBudgetAmount;
    }

    public Double getMyTotalMonthlyExpenses() {
        return myTotalMonthlyExpenses;
    }

    public Double getMyTotalMonthlyIncome() {
        return myTotalMonthlyIncome;
    }

    public Double getMyTotalPeriodOneIncome() {
        return myTotalPeriodOneIncome;
    }

    public Double getMyTotalPeriodTwoIncome() {
        return myTotalPeriodTwoIncome;
    }

    public Double getMyTotalPeriodOneSpending() {
        return myTotalPeriodOneSpending;
    }

    public Double getMyTotalPeriodTwoSpending() {
        return myTotalPeriodTwoSpending;
    }

    public Integer getMyCurrentPeriod() {
        return myCurrentPeriod;
    }

    public Double getMyCashItems() {
        return myCashItems;
    }

}

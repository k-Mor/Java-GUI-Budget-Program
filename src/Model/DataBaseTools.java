/*
 * Multiline comment at the top of the file.
 */
package Model;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.SceneChanger;

import java.sql.*;
import java.time.LocalDate;

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
     * This method is responsible for getting the connection to the db,
     * and preparing the SQL statements.
     *
     * @param theSql : The statement that is going to be prepared
     * @return : PreparedStatement that the calling program manipulates
     */
    private PreparedStatement manageConnection(String theSql) {
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
                    result.getDouble("accountID"));

            // Set the ID
            newTransaction.setMyTransactionId(result.getInt("itemId"));

            // Add to the list to be returned
            transactions.add(newTransaction);
        }

        // Close things out
        preparedStatement.close();
        result.close();

        return transactions;
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

        while (result.next()) {

            // Create a new transaction
            // Null pointer exception is handles in the event that something is missing in the budget.
            try {
                Budget newBudget = new Budget(result.getDate("dateLastPaid").toLocalDate(),
                        result.getString("itemName"), result.getDouble("currentValue"),
                        result.getDouble("budgetedValue"), result.getDouble("expectedMonthlyValue"),
                        result.getDate("dueDate").toLocalDate(), result.getString("itemNotes"));

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




    public void updateTransactionInDb(String sql, int theId, LocalDate theDate, String theP, String theV, String theDesc, String theCat, Double theAmount, Double theBal, Double theAccId) throws SQLException{
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
        preparedStatement.setDouble(8, theAccId);
        preparedStatement.setInt(9, theId);

        // Do the update
        preparedStatement.executeUpdate();

        // close things out
        preparedStatement.close();

    }

    /**
     *
     * @param sql
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
    public void InsertTransactionInDb(String sql, LocalDate theDate, String theP, String theV, String theDesc, String theCat, Double theAmount, Double theBal, Double theAccId) throws SQLException{
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
        preparedStatement.setDouble(8, theAccId);
//        preparedStatement.setInt(9, theId);

        // Do the update
        preparedStatement.execute();

        // close things out
        preparedStatement.close();

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
}

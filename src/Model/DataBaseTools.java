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
     * This method creates
     *
     * @return
     * @throws SQLException
     */
    public ObservableList<Transaction> getTheTransactionList() throws SQLException{

        // List of transactions
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        // Getting everything from the table
        String sql = "SELECT * FROM transactions";

        // Gets connection and prepares the SQL statement
        preparedStatement = manageConnection(sql);

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

    public void updateTransactionInDb(int theId, LocalDate theDate, String theP, String theV, String theDesc, String theCat, Double theAmount, Double theBal, Double theAccId) throws SQLException{
        PreparedStatement preparedStatement = null;

        String sql = "UPDATE transactions SET dateOfTransaction = ?, purchaser = ?, vendor = ?, description = ?, category = ?, amount = ?, accountBalance = ?, accountID = ? WHERE itemId = ?";
        preparedStatement = manageConnection(sql);

        System.out.println(theDesc);

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

        System.out.println("After the execute");

        // close things out
//        preparedStatement.close();

    }
}

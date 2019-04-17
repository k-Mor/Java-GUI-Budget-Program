/*
 * Multiline comment at the top of the file.
 */
package Model;

import animatefx.animation.FadeIn;
import view.SceneChanger;

import java.sql.*;

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
        // Close the connection
        preparedStatement.close();
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
        System.out.println(accountString);
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
        preparedStatement.close();
        return returnResult;
    }
}

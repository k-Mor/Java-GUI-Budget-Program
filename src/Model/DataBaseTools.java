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
     * This method is responsible for checking if the use is in the
     * database.
     *
     * @param theUserName : The user name to be checked.
     * @param thePassword : The password to be checked.
     * @return : boolean indicating if the user is in the db or not.
     */
    public boolean checkIfUserInDb(String theUserName, String thePassword) throws SQLException {

        //Query the database and search for this user
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String dbPassword = "";
        byte[] salt = null;
        boolean result = false;

        try {
            // Get a connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Admin-PASSwo-Rd");

            // Get the string
            String sql = "SELECT userPassword, salt FROM users WHERE userName = ?";

            // Prepare statement
            preparedStatement = connection.prepareStatement(sql);

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
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        connection.close();
        return result;
    }

    /**
     * This method queries the remote MySQL database and retrieves the account balance.
     * @return
     */
    public double getCurrentAccountBalance() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        double returnResult = -1.0;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://162.241.219.194/kalebsc1_MyBlueDataBase", "kalebsc1_theBoss", "Cassandra1$");

            String sql = "SELECT balance FROM accounts WHERE itemId = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "1");

            result = preparedStatement.executeQuery();

            while (result.next()) {
                returnResult = result.getDouble("balance");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return returnResult;
    }
}

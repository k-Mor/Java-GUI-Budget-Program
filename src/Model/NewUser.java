/*
 * Multiline comment at the top of the file.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This is the new user class that creates a new user upon
 * instantiation, and houses the appropriate methods.
 *
 * @author Kaleb
 * @version 2019-04-10
 */
public class NewUser {

    /**
     * This is the user ID that is generated upon
     * inseration into the DB.
     */
    private int myUserID;

    /**
     * This is the userName that is selected during
     * sign up.
     */
    private String myUserName;

    /**
     * This is the password created during sign up.
     */
    private String myPassword;

    /**
     * This is the constructor that passes the username
     * and password to the appropriate methods.
     *
     * @param theUserName : This is the userName passed.
     * @param thePassword : This is the password that is passed.
     */
    public NewUser(String theUserName, String thePassword) {

        //TODO
        // 1.) Need to encrypt the password still.
        // 2.) Ser up a decryption process.

        setTheUsername(theUserName);
        setThePassword(thePassword);
        System.out.println("CONSTRUCTOR CALLED");
    }

    /**
     * This method is responsible for performing a check on the username and
     * ensuring that it meets the proper standards.
     *
     * @param theUsername : This is the username that is going to be checked.
     */
    private void setTheUsername(String theUsername) {
        if (theUsername.length() <= 5) {
            throw new IllegalArgumentException("Username must me greater than 5 characters");
        } else {
            myUserName = theUsername;
        }
    }

    /**
     * This method checks the password for special characters,
     * will not allow user creation if the standard is not met.
     *
     * @param thePassword : This is the password to be checked.
     */
    protected void setThePassword(String thePassword) {

        //TODO (CALL THE ENCRYPTION METHODS HERE)
       char[] specialChars = {'!','@','#','$','%','^','&','*','(',')','_'};
       boolean passOK = false;
       for (int i = 0; i < thePassword.length(); i++) {
           for (int j = 0; j < specialChars.length; j++) {
               if (thePassword.charAt(i) == specialChars[j]) {
                   passOK = true;
               }
           }
       }
       if (passOK) {
           myPassword = thePassword;
       } else {
           throw new IllegalArgumentException("Password must contain a special character");
       }
    }

    /**
     * This method is responsible for inserting the user into the MySQL local database.
     *
     * @throws SQLException : In the event that something goes wrong with the commands.
     */
    public void insertUserIntoDB() throws SQLException {
        System.out.println("DATABASE METHOD CALLED");
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            // Connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "Admin-PASSwo-Rd");
            // Create a string that holds the query with ? as user inputs
            String sql = "INSERT INTO users(userName, userPassword)" +
                    "VALUES(?, ?)";
            // prepare the query
            preparedStatement = conn.prepareStatement(sql);

            // bind the values to the parameters
            preparedStatement.setString(1, myUserName);
            preparedStatement.setString(2, myPassword);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
//                conn.close();
            }
        }
    }

    /**
     * This is the overridden toString method that returns a string
     * representation of the new user object.
     *
     * @return : returns a String representation of the object.
     */
    @Override
    public String toString() {
        return "NewUser{" +
                "myUserID=" + myUserID +
                ", myUserName='" + myUserName + '\'' +
                ", myPassword='" + myPassword + '\'' +
                '}';
    }
}

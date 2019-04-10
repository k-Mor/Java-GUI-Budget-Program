package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewUser {

    /**
     *
     */
    private int myUserID;

    /**
     *
     */
    private String myUserName;

    /**
     *
     */
    private String myPassword;

    /**
     *
     * @param theUserName
     * @param thePassword
     */
    public NewUser(String theUserName, String thePassword) {
        setTheUsername(theUserName);
        setThePassword(thePassword);
        System.out.println("CONSTRUCTOR CALLED");
    }

    /**
     *
     * @param theUsername
     */
    private void setTheUsername(String theUsername) {
        if (theUsername.length() <= 5) {
            throw new IllegalArgumentException("Username must me greater than 5 characters");
        } else {
            myUserName = theUsername;
        }
    }

    /**
     *
     * @param thePassword
     */
    protected void setThePassword(String thePassword) {
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
     * @throws SQLException
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
     *
     * @return
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

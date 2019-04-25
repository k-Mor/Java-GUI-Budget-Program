/*
 * Multiline comment at the top of the file
 */
package Model;

import java.time.LocalDate;

/**
 * This is the transaction class which houses all of the methods
 * pertaining to transaction objects.
 *
 * @author : Kaleb
 * @version : 2019-04-17
 */
public class Account {

    /**
     *  This is a field.
     */
    private int myAccountId;

    /**
     *
     */
    private Double myAccountBalance;

    /**
     *  This is a field.
     */
    private String myAccountType;

    public Account(Double theAccountBalance, String theAccountType) {
        setMyAccountBalance(theAccountBalance);
        setMyAccountType(theAccountType);
    }

    public int getMyAccountId() {
        return myAccountId;
    }


    public Double getMyAccountBalance() {
        return myAccountBalance;
    }

    public void setMyAccountBalance(Double theAccountBalance) {
        myAccountBalance = theAccountBalance;
    }

    public String getMyAccountType() {
        return myAccountType;
    }

    public void setMyAccountType(String theAccountType) {
        myAccountType = theAccountType;
    }

    public void setMyAccountId(int theAccountId) {
        myAccountId = theAccountId;
    }

    /**
     *
     */
    public void updateAccountInDb() {
        DataBaseTools dbTools = new DataBaseTools();
        dbTools.updateAccountInDb(myAccountId, myAccountBalance, myAccountType);

    }

    /**
     *
     */
    public void insertAccountInDb() {
        DataBaseTools dbTools = new DataBaseTools();
        String sql = "INSERT INTO accounts (balance, type) VALUES (?,?)";
        dbTools.insertAccount(sql, myAccountBalance, myAccountType);

    }

    @Override
    public String toString() {
        return "Account{" +
                "myAccountId=" + myAccountId +
                ", myAccountBalance=" + myAccountBalance +
                ", myAccountType='" + myAccountType + '\'' +
                '}';
    }
}

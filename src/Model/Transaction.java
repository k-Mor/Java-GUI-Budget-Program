/*
 * Multiline comment at the top of the file
 */
package Model;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * This is the transaction class which houses all of the methods
 * pertaining to transaction objects.
 *
 * @author : Kaleb
 * @version : 2019-04-17
 */
public class Transaction {

    /**
     *  This is a field.
     */
    private Integer myTransactionId;

    /**
     *  This is a field.
     */
    private LocalDate myTransactionDate;

    /**
     *  This is a field.
     */
    private String myPurchaser;

    /**
     *  This is a field.
     */
    private String myVendor;

    /**
     *  This is a field.
     */
    private String myDescription;

    /**
     *  This is a field.
     */
    private String myCategory;

    /**
     *  This is a field.
     */
    private Double myAmount;

    /**
     *  This is a field.
     */
    private Double myBalanceAfter;

    /**
     *  This is a field.
     */
    private Integer myAccountFrom;

    /**
     * This is the constructor.
     *
     * @param theTransactionDate : The date of transaction.
     * @param thePurchaser : The person who made the purchase.
     * @param theVendor : The place the transaction was made.
     * @param theDescription : The description.
     * @param theCategory : The category that it falls in.
     * @param theAmount : The amount.
     * @param theBalanceAfter : The remaining balance in the account after the transaction.
     */
    public Transaction(LocalDate theTransactionDate, String thePurchaser, String theVendor, String theDescription,
                       String theCategory, Double theAmount, Double theBalanceAfter, Integer theAccountFrom) {

        setMyTransactionDate(theTransactionDate);
        setMyPurchaser(thePurchaser);
        setMyVendor(theVendor);
        setMyDescription(theDescription);
        setMyCategory(theCategory);
        setMyAmount(theAmount);
        myBalanceAfter = theBalanceAfter;
        setMyAccountFrom(theAccountFrom);
    }

    /**
     * Setter for the account whos balance after transaction is
     * reflected.
     *
     * @param theAccountFrom
     */
    public void setMyAccountFrom(Integer theAccountFrom) {
        myAccountFrom = theAccountFrom;
    }

    /**
     * Setter for the ID
     *
     * @param theTransactionId : The ID.
     */
    public void setMyTransactionId(int theTransactionId) {
        if (theTransactionId >= 0) {
            myTransactionId = theTransactionId;
        } else {
            throw new IllegalArgumentException("ID must be greater than 0");
        }
    }

    /**
     * Setter for balance after
     *
     * @param theBalanceAfter
     */
    public void setMyBalanceAfter(Double theBalanceAfter) {
        myBalanceAfter = theBalanceAfter;
    }

    /**
     * This sets the Transaction date.
     *
     * @param : theTransactionDate
     */
    public void setMyTransactionDate(LocalDate theTransactionDate) {
        myTransactionDate = theTransactionDate;
    }

    /**
     * This sets the purchaser.
     *
     * @param : thePurchaser
     */
    public void setMyPurchaser(String thePurchaser) {
        myPurchaser = thePurchaser;
    }

    /**
     * This sets the vendor.
     *
     * @param : theVendor
     */
    public void setMyVendor(String theVendor) {
        myVendor = theVendor;
    }

    /**
     * This sets the description.
     *
     * @param : theDescription
     */
    public void setMyDescription(String theDescription) {
        myDescription = theDescription;
    }

    /**
     * This sets the category.
     *
     * @param : theCategory
     */
    public void setMyCategory(String theCategory) {
        myCategory = theCategory;
    }

    /**
     * This sets the amount.
     *
     * @param : theAmount
     */
    public void setMyAmount(Double theAmount) {
        myAmount = theAmount;
    }

    /**
     * Getter for the ID.
     *
     * @return
     */
    public int getMyTransactionId() {
        return myTransactionId;
    }
    /**
     * This is a getter for the transaction date.
     *
     * @return
     */
    public LocalDate getMyTransactionDate() {
        return myTransactionDate;
    }

    /**
     * This is a getter for the purchaser.
     *
     * @return
     */
    public String getMyPurchaser() {
        return myPurchaser;
    }

    /**
     * This is a getter for the vendor.
     *
     * @return
     */
    public String getMyVendor() {
        return myVendor;
    }

    /**
     * This is a getter for the description.
     *
     * @return
     */
    public String getMyDescription() {
        return myDescription;
    }

    /**
     * This is a getter for the category.
     *
     * @return
     */
    public String getMyCategory() {
        return myCategory;
    }

    /**
     * This is a getter for the amount.
     *
     * @return
     */
    public Double getMyAmount() {
        return myAmount;
    }

    /**
     * This is a getter for the after balance.
     *
     * @return
     */
    public Double getMyBalanceAfter() {
        return myBalanceAfter;
    }

    /**
     *
     *
     * @return
     */
    public Integer getMyAccountFrom() {
        return myAccountFrom;
    }

    /**
     *
     */
    public void updateTransactionInDb() {
        String sql = "UPDATE transactions SET dateOfTransaction = ?, purchaser = ?, vendor = ?, description = ?, category = ?, amount = ?, accountBalance = ?, accountID = ? WHERE itemId = ?";
        DataBaseTools dbTools = new DataBaseTools();
        try {
            dbTools.updateTransactionInDb(sql, myTransactionId, myTransactionDate, myPurchaser, myVendor, myDescription, myCategory, myAmount, myBalanceAfter, myAccountFrom);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

//    /**
//     *
//     */
//    public void insertTransactionInDb() {
//        String sql = "INSERT INTO transactions (dateOfTransaction, purchaser, vendor, description, category, amount, accountBalance, accountID) VALUES (?,?,?,?,?,?,?,?)";
//        DataBaseTools dbTools = new DataBaseTools();
//        try {
//            dbTools.InsertTransactionInDb(sql, myTransactionDate, myPurchaser, myVendor, myDescription, myCategory, myAmount, myBalanceAfter, myAccountFrom);
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//        }

//    }

    /**
     * This is the toString.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "myTransactionDate=" + myTransactionDate +
                ", myPurchaser='" + myPurchaser + '\'' +
                ", myVendor='" + myVendor + '\'' +
                ", myDescription='" + myDescription + '\'' +
                ", myCategory='" + myCategory + '\'' +
                ", myAmount=" + myAmount +
                ", myBalanceAfter=" + myBalanceAfter +
                '}';
    }
}

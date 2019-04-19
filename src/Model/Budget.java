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
public class Budget {

    /**
     *  This is a field.
     */
    private int myItemId;

    /**
     *  This is a field.
     */
    private LocalDate myDateLastPaid;

    /**
     *  This is a field.
     */
    private String myItemName;

    /**
     *  This is a field.
     */
    private Double myCurrentValue;

    /**
     *  This is a field.
     */
    private Double myBudgetedValue;

    /**
     *  This is a field.
     */
    private Double myExpectedMonthly;

    /**
     *  This is a field.
     */
    private LocalDate myDueDate;

    /**
     *  This is a field.
     */
    private String myItemNotes;

    /**
     * This is the constructor for the budget object.
     *
     * @param theDateLastPaid : The date the bill was last paid.
     * @param theItemName : The name of the budget line item.
     * @param theCurrentValue : The current value of the line item.
     * @param theBudgetedValue : The value that was budgeted for.
     * @param theExpectedMonthly : The expected monthly cost of the item.
     * @param theDueDate : The due date of the item.
     * @param theItemNotes : Any associated notes for the item.
     */
    public Budget(LocalDate theDateLastPaid, String theItemName, Double theCurrentValue,
                  Double theBudgetedValue, Double theExpectedMonthly, LocalDate theDueDate, String theItemNotes) {

        setMyDateLastPaid(theDateLastPaid);
        setMyItemName(theItemName);
        setMyCurrentValue(theCurrentValue);
        setMyBudgetedValue(theBudgetedValue);
        setMyExpectedMonthly(theExpectedMonthly);
        setMyDueDate(theDueDate);
        setMyItemNotes(theItemNotes);
    }

    /**
     * A getter for the item ID.
     *
     * @return
     */
    public int getMyItemId() {
        return myItemId;
    }

    /**
     * A setter for the itemID
     *
     * @param theItemId : The item ID that the item will change to.
     */
    public void setMyItemId(int theItemId) {
        myItemId = theItemId;
    }

    /**
     * A getter for the date last paid.
     *
     * @return
     */
    public LocalDate getMyDateLastPaid() {
        return myDateLastPaid;
    }

    /**
     * A setter for the date last paid.
     *
     * @param theDateLastPaid : The new date last paid
     */
    public void setMyDateLastPaid(LocalDate theDateLastPaid) {
        myDateLastPaid = theDateLastPaid;
    }

    /**
     * A getter for the item name.
     *
     * @return
     */
    public String getMyItemName() {
        return myItemName;
    }

    /**
     * A setter for the item name
     *
     * @param theItemName : The new item name.
     */
    public void setMyItemName(String theItemName) {
        myItemName = theItemName;
    }

    /**
     * A getter for the current value.
     *
     * @return
     */
    public Double getMyCurrentValue() {
        return myCurrentValue;
    }

    /**
     * Setter for the current value.
     *
     * @param theCurrentValue : The new current value.
     */
    public void setMyCurrentValue(Double theCurrentValue) {
        myCurrentValue = theCurrentValue;
    }

    /**
     * A getter for the budgeted value
     *
     * @return
     */
    public Double getMyBudgetedValue() {
        return myBudgetedValue;
    }

    /**
     * This is a setter for the budgeted value.
     *
     * @param theBudgetedValue : The new budgeted value.
     */
    public void setMyBudgetedValue(Double theBudgetedValue) {
        myBudgetedValue = theBudgetedValue;
    }

    /**
     * This is a getter for the field.
     *
     * @return
     */
    public Double getMyExpectedMonthly() {
        return myExpectedMonthly;
    }

    /**
     * A setter for the expected monthly value
     *
     * @param theExpectedMonthly : The new expected value.
     */
    public void setMyExpectedMonthly(Double theExpectedMonthly) {
        myExpectedMonthly = theExpectedMonthly;
    }

    /**
     * A getter for the due date.
     *
     * @return
     */
    public LocalDate getMyDueDate() {
        return myDueDate;
    }

    /**
     * A setter for the due date.
     *
     * @param theDueDate : The new due date.
     */
    public void setMyDueDate(LocalDate theDueDate) {
        myDueDate = theDueDate;
    }

    /**
     * A getter for the item notes.
     *
     * @return
     */
    public String getMyItemNotes() {
        return myItemNotes;
    }

    /**
     * A setter for the item notes.
     *
     * @param theItemNotes : The new notes.
     */
    public void setMyItemNotes(String theItemNotes) {
        myItemNotes = theItemNotes;
    }

    /**
     *
     */
    public void updateBudgetItem() {
        DataBaseTools dbTools = new DataBaseTools();
        dbTools.updateBudgetItemInDb(myItemId, myDateLastPaid, myItemName, myCurrentValue, myBudgetedValue, myExpectedMonthly, myDueDate, myItemNotes);

    }
    /**
     * The overriden two string method.
     *
     * @return
     */
    @Override
    public String toString() {
        return "Budget{" +
                "myItemId=" + myItemId +
                ", myDateLastPaid=" + myDateLastPaid +
                ", myItemName='" + myItemName + '\'' +
                ", myCurrentValue=" + myCurrentValue +
                ", myBudgetedValue=" + myBudgetedValue +
                ", myExpectedMonthly=" + myExpectedMonthly +
                ", myDueDate=" + myDueDate +
                ", myItemNotes='" + myItemNotes + '\'' +
                '}';
    }
}

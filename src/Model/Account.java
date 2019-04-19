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

    public Account(LocalDate theDateLastPaid, String theItemName, Double theCurrentValue,
                   Double theBudgetedValue, Double theExpectedMonthly, LocalDate theDueDate, String theItemNotes) {

        setMyDateLastPaid(theDateLastPaid);
        setMyItemName(theItemName);
        setMyCurrentValue(theCurrentValue);
        setMyBudgetedValue(theBudgetedValue);
        setMyExpectedMonthly(theExpectedMonthly);
        setMyDueDate(theDueDate);
        setMyItemNotes(theItemNotes);
    }

    public int getMyItemId() {
        return myItemId;
    }

    public void setMyItemId(int myItemId) {
        this.myItemId = myItemId;
    }

    public LocalDate getMyDateLastPaid() {
        return myDateLastPaid;
    }

    public void setMyDateLastPaid(LocalDate myDateLastPaid) {
        this.myDateLastPaid = myDateLastPaid;
    }

    public String getMyItemName() {
        return myItemName;
    }

    public void setMyItemName(String myItemName) {
        this.myItemName = myItemName;
    }

    public Double getMyCurrentValue() {
        return myCurrentValue;
    }

    public void setMyCurrentValue(Double myCurrentValue) {
        this.myCurrentValue = myCurrentValue;
    }

    public Double getMyBudgetedValue() {
        return myBudgetedValue;
    }

    public void setMyBudgetedValue(Double myBudgetedValue) {
        this.myBudgetedValue = myBudgetedValue;
    }

    public Double getMyExpectedMonthly() {
        return myExpectedMonthly;
    }

    public void setMyExpectedMonthly(Double myExpectedMonthly) {
        this.myExpectedMonthly = myExpectedMonthly;
    }

    public LocalDate getMyDueDate() {
        return myDueDate;
    }

    public void setMyDueDate(LocalDate myDueDate) {
        this.myDueDate = myDueDate;
    }

    public String getMyItemNotes() {
        return myItemNotes;
    }

    public void setMyItemNotes(String myItemNotes) {
        this.myItemNotes = myItemNotes;
    }

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

/*
 * Multiline comment at the top of the file.
 */
package controller;

import Model.Account;
import Model.AscendingOrder;
import Model.DataBaseTools;
import Model.Transaction;
import animatefx.animation.FadeIn;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.ControllerInterface;
import view.SceneChanger;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class is the controller for the NewTransaction.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class NewTransactionViewController implements Initializable, AscendingOrder<Transaction> {

    /**
     * This is the actual transaction object that is captured when
     * a user selects a line item in the transaction table.
     */
    private Transaction myTransaction;

    private List<Transaction> myTransactionList;

    /**
     * These are textFields for the form which allows edits.
     */
    @FXML private  TextField myTransactionDate;
    @FXML private  TextField myPurchaser;
    @FXML private  TextField myVendor;
    @FXML private  TextField myDescription;
    @FXML private  TextField myCategory;
    @FXML private  TextField myAmount;
    @FXML private ChoiceBox<String> myFromAccount;
    @FXML private Label myLbl;


    /**
     * This is the overriden method that
     * initializes the controller class.
     *
     * @param theUrl : This is an unused parameter.
     * @param theRb  : This is an unused parameter.
     */
    @Override
    public void initialize(URL theUrl, ResourceBundle theRb) {
        myTransactionList = new LinkedList<>();
        DataBaseTools dbTools = new DataBaseTools();
        try {
            ObservableList<Account> accounts = dbTools.getTheAccounts();
            Iterator<Account> itr = accounts.iterator();

            // Get the selection of accounts
            while (itr.hasNext()) {
                myFromAccount.getItems().add(itr.next().getMyAccountType());
            }
        } catch (SQLException e) {
            myLbl.setText(e.getMessage());
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date theDate = new Date();
        myTransactionDate.setText(dateFormat.format(theDate));
        myLbl.setText("");
    }

    /**
     * This method is responsible for controlling what happens when
     * the save button is pushed.
     */
    public void saveButtonPushed() {
        // Validate the fields
        //TODO

        // Create the transaction
        createNewTransaction();

        // Tell the user
        myLbl.setText("Charge successfully saved, don't forget to commit!");
        new FadeIn(myLbl).play();
    }

    /**
     *
     */
    public void commitButtonPushed() {
        int cnt = 0;
        for (Transaction trans: myTransactionList) {
            if (compareTo(trans) < 0) {
                trans.insertTransactionInDb();
            } else if (compareTo(trans) > 0) {

            }
            cnt++;
            myLbl.setText(cnt + " Charges committed out of: " + myTransactionList.size());
        }
    }

    /**
     *
     * @param trans
     * @return
     */
    public int compareTo(Transaction trans) {
        if (myTransaction.getMyTransactionId() < trans.getMyTransactionId()) {
            return -1;
        } else if (myTransaction.getMyTransactionId() > trans.getMyTransactionId()){
            return 1;
        }
        return 0;
    }

    /**
     * This method is responsible for handling the event when the goBack
     * button is pushed.
     *
     * @param theEvent : The event that is triggered when the button is pushed.
     */
    public void goBackButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "ViewData.fxml", "Data Page");
    }

    /**
     * This method updates a particular instance of the selected Transaction
     * by accessing the setters for that instance.
     */
    public void createNewTransaction() {
        myFromAccount.getValue();
        Double balance = 0.0;
        Integer theId = 0;
        Account accountHolding;
        LocalDate date = LocalDate.parse(myTransactionDate.getText());
        DataBaseTools dbTools = new DataBaseTools();
        try {
            ObservableList<Account> accounts = dbTools.getTheAccounts();
            Iterator<Account> itr = accounts.iterator();

            // This should get the balance-after after the transaction data has been captured
            while (itr.hasNext()) {
                accountHolding = itr.next();
                if (accountHolding.getMyAccountType().equals(myFromAccount.getValue())) {
                    balance = accountHolding.getMyAccountBalance() - Double.valueOf(myAmount.getText());
                    theId = accountHolding.getMyAccountId();
                }
            }
        } catch (SQLException e) {
            myLbl.setText(e.getMessage());
        }

        //Create the new transaction
        myTransaction = new Transaction(date, myPurchaser.getText(),
                myVendor.getText(), myDescription.getText(),
                myCategory.getText(), Double.valueOf(myAmount.getText()),
                balance, theId);

        //Add to the list
        myTransactionList.add(myTransaction);
    }
}
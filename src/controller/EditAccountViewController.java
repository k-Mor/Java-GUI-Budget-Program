/*
 * Multiline comment at the top of the file.
 */
package controller;

import Model.Budget;
import Model.Transaction;
import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.ControllerInterface;
import view.SceneChanger;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * This class is the controller for the NewTransaction.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class EditAccountViewController implements Initializable, ControllerInterface {

    /**
     * This is the actual transaction object that is captured when
     * a user selects a line item in the transaction table.
     */
    private Transaction myTransaction;

    /**
     * These are textFields for the form which allows edits.
     */
    @FXML private  TextField myTransactionID;
    @FXML private  TextField myTransactionDate;
    @FXML private  TextField myPurchaser;
    @FXML private  TextField myVendor;
    @FXML private  TextField myDescription;
    @FXML private  TextField myCategory;
    @FXML private  TextField myAmount;
    @FXML private  TextField myBalanceAfter;
    @FXML private  TextField myFromAccount;
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
        myLbl.setText("");
    }

    /**
     * This method is responsible for controlling what happens when
     * the save button is pushed.
     */
    public void saveButtonPushed() {
        // First updates the actual object
        updateTransaction();

        // Updates that user in the DB
        myTransaction.updateTransactionInDb();

        // Tell the user
        myLbl.setText("Change successfully recorded");
        new FadeIn(myLbl).play();
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
    public void updateTransaction() {

        // Do conversions
        // convert String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(myTransactionDate.getText(), formatter);

        myTransaction.setMyTransactionDate(localDate);
        myTransaction.setMyPurchaser(myPurchaser.getText());
        myTransaction.setMyVendor(myVendor.getText());
        myTransaction.setMyDescription(myDescription.getText());
        myTransaction.setMyCategory(myCategory.getText());
        myTransaction.setMyAmount(Double.parseDouble(myAmount.getText()));
        myTransaction.setMyBalanceAfter(Double.parseDouble(myBalanceAfter.getText()));
        myTransaction.setMyAccountFrom(Double.parseDouble(myFromAccount.getText()));
    }

    /**
     * This method is an overidden method that brings the data from this controller
     * to the chosen view. This method specifically brings over all of the fields from the
     * table view and drops them in the textFields in the edit view.
     *
     * @param theTransaction : This is the instance of the transaction that is being brought
     *                       over to be changed, or not.
     */
    @Override
    public void preLoadData(Transaction theTransaction) {

        // Transfer the data from the passed transaction object to the new view.
        myTransaction = theTransaction;
        myTransactionID.setText(Integer.toString(myTransaction.getMyTransactionId()));
        myTransactionDate.setText(myTransaction.getMyTransactionDate().toString());
        myPurchaser.setText(myTransaction.getMyPurchaser());
        myVendor.setText(myTransaction.getMyVendor());
        myDescription.setText(myTransaction.getMyDescription());
        myCategory.setText(myTransaction.getMyCategory());
        myAmount.setText(Double.toString(myTransaction.getMyAmount()));
        myBalanceAfter.setText(Double.toString(myTransaction.getMyBalanceAfter()));
        myFromAccount.setText(Double.toString(myTransaction.getMyAccountFrom()));

    }

    /**
     * This is required by the interface and is not yet used.
     *
     * @param theBudget
     */
    @Override
    public void preLoadData(Budget theBudget) {
    }
}
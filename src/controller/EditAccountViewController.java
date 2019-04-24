/*
 * Multiline comment at the top of the file.
 */
package controller;

import Model.Account;
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
    private Account myAccount;

    /**
     * These are textFields for the form which allows edits.
     */
    @FXML private  TextField myAccountId;
    @FXML private  TextField myAccountBalance;
    @FXML private  TextField myAccountType;

    /**
     *
     */
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
        updateAccount();

        // Updates that user in the DB
        myAccount.updateAccountInDb();

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
    public void updateAccount() {
        myAccount.setMyAccountBalance(Double.parseDouble(myAccountBalance.getText()));
        myAccount.setMyAccountType(myAccountType.getText());
    }

    /**
     * This method is an overidden method that brings the data from this controller
     * to the chosen view. This method specifically brings over all of the fields from the
     * table view and drops them in the textFields in the edit view.
     *
     * @param theAccount : This is the instance of the transaction that is being brought
     *                       over to be changed, or not.
     */
    @Override
    public void preLoadData(Object theAccount) {

        // Transfer the data from the passed transaction object to the new view.
        myAccount = (Account) theAccount;
        myAccountId.setText(Integer.toString(myAccount.getMyAccountId()));
        myAccountBalance.setText(myAccount.getMyAccountBalance().toString());
        myAccountType.setText(myAccount.getMyAccountType());
    }
}
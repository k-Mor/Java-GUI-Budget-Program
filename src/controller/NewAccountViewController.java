/*
 * Multiline comment at the top of the file.
 */
package controller;

import Model.Account;
import Model.DataBaseTools;
import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.ControllerInterface;
import view.SceneChanger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller for the NewTransaction.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class NewAccountViewController implements Initializable {

    /**
     * These are textFields for the form which allows edits.
     */
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
        Double balance = Double.parseDouble(myAccountBalance.getText());
        Account newAccount = new Account(balance, myAccountType.getText());

        // Set the fields for the account
        newAccount.setMyAccountBalance(balance);
        newAccount.setMyAccountType(myAccountType.getText());

        try {
            newAccount.insertAccountInDb();
            // Tell the user
            myLbl.setText("Account successfully added");
            new FadeIn(myLbl).play();
        } catch (Exception e) {
            myLbl.setText(e.getMessage());
        }
    }

    /**
     * This method is responsible for handling the event when the goBack
     * button is pushed.6
     *
     * @param theEvent : The event that is triggered when the button is pushed.
     */
    public void goBackButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "ViewData.fxml", "Data Page");
    }
}
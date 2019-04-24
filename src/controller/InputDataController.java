/*
 * Multiline comment at the top of the file.
 */
package controller;

import Model.DataBaseTools;
import Model.PasswordGenerator;
import animatefx.animation.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.SceneChanger;

/**
 * This class is the controller for the LogInView.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class InputDataController implements Initializable {

    /**
     * This is the log in button.
     */
    @FXML
    private Button myInputTransactions;

    /**
     * This is the sign up button.
     */
    @FXML
    private Button myCreateBudgetItem;

    /**
     * This is the error label that indicates a problem
     * should one occur during sign in.
     */
    @FXML
    private Button myCreateAccount;

    /**
     *
     */
    @FXML
    private Button myGoBack;


    /**
     * This method is responsible for handling the log in button event
     * click.
     *
     * @param theEvent : This is the event.
     */
    public void inputTransactionButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "NewTransactions.fxml", " Transactions page");
    }

    /**
     * This method handles the sign up botton and changes the view to
     * the sign up page.
     *
     * @param theEvent : This is the event that triggers the change.
     */
    public void createBudgetItemButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "SignUpView.fxml", "Sign up Page");
    }

    /**
     * This method handles the sign up botton and changes the view to
     * the sign up page.
     *
     * @param theEvent : This is the event that triggers the change.
     */
    public void createAccountButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "SignUpView.fxml", "Sign up Page");
        }

    /**
     * This method handles the sign up botton and changes the view to
     * the sign up page.
     *
     * @param theEvent : This is the event that triggers the change.
     */
    public void goBackButtonPushed(ActionEvent theEvent) {
        // Back to main page
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "HomePage.fxml", "Home Page");
    }

    /**
     * This is the overriden method that
     * initializes the controller class.
     *
     * @param theUrl : This is an unused parameter.
     * @param theRb  : This is an unused parameter.
     */
    @Override
    public void initialize(URL theUrl, ResourceBundle theRb) {
    }
}
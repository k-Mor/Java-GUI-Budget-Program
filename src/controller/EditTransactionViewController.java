/*
 * Multiline comment at the top of the file.
 */
package controller;

import Model.Budget;
import Model.DataBaseTools;
import Model.PasswordGenerator;
import Model.Transaction;
import animatefx.animation.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import view.ControllerInterface;
import view.SceneChanger;

/**
 * This class is the controller for the NewTransaction.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class EditTransactionViewController implements Initializable, ControllerInterface {

    private Transaction myTransaction;

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
     *
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
     *
     * @param theEvent
     */
    public void goBackButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "ViewData.fxml", "Data Page");
    }

    /**
     *
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
     *
     * @param theTransaction
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
     *
     * @param theBudget
     */
    @Override
    public void preLoadData(Budget theBudget) {
    }
}
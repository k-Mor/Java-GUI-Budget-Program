/*
 * Multiline comment at the top of the file.
 */
package controller;

import Model.Budget;
import animatefx.animation.FadeIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.ControllerInterface;
import view.SceneChanger;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * This class is the controller for the NewTransaction.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class NewBudgetItemViewController implements Initializable {

    /**
     * These are textFields for the form which allows edits.
     */
    @FXML private  TextField myDateLastPaid;
    @FXML private  TextField myItemName;
    @FXML private  TextField myCurrentValue;
    @FXML private  TextField myBudgetedValue;
    @FXML private  TextField myExpectedMonthly;
    @FXML private  TextField myDueDate;
    @FXML private  TextField myItemNotes;

    /**
     * This is a label to indicate to the user when the budget has been
     * successfully updated.
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
        // Set the default dates
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date theDate = new Date();
        myDateLastPaid.setText(dateFormat.format(theDate));
        myDueDate.setText(dateFormat.format(theDate));

        myLbl.setText("");
    }

    /**
     * This method is responsible for controlling what happens when
     * the save button is pushed.
     */
    public void saveButtonPushed() {
        Double currentValue = Double.valueOf(myCurrentValue.getText());
        Double budgetedValue = Double.valueOf(myBudgetedValue.getText());
        Double expectedValue = Double.valueOf(myExpectedMonthly.getText());
        LocalDate lastPaidDate = LocalDate.parse(myDateLastPaid.getText());
        LocalDate dueDate = LocalDate.parse(myDateLastPaid.getText());

        // Create the new item
        Budget newItem = new Budget(lastPaidDate, myItemName.getText(),
                currentValue, budgetedValue,
                expectedValue, dueDate,
                myItemNotes.getText());

        // Updates that budget item in the DB
        try {
            newItem.insertIntoDb();
            // Tell the user
            myLbl.setText("Item successfully added");
            new FadeIn(myLbl).play();
        } catch (Exception e) {
            myLbl.setText(e.getMessage());
        }
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
}
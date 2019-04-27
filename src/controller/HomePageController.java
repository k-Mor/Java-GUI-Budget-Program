/*
 * Multiline comment at the top of the file.
 */
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import Model.DataBaseTools;
import Model.OtherTools;
import animatefx.animation.Flash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import view.SceneChanger;

/**
 * This class is the controller for the HomePage.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class HomePageController implements Initializable {

    /**
     * This is the field that displays the
     * current account balance.
     */
    @FXML private Text moneyDisplay;

    /**
     * This is a field that shows the average period savings
     * compared to other logged periods.
     */
    @FXML private Text averagePeriodSpending;

    /**
     * This is a field that shows the cash flow from year to date.
     */
    @FXML private Text expensesVsIncome;

    /**
     * This is a field that connects the header for
     * the graph.
     */
    @FXML private Label graphLbl;

    /**
     * This is the link that hangs above the account
     * balance.
     */
    @FXML private Hyperlink accountBalanceLnk;

    /**
     * This field connects the view data button
     */
    @FXML private Button viewDataBtn;

    /**
     * This field connects the input data button.
     */
    @FXML private Button inputDataBtn;

    /**
     * This field connects the generate reports button.
     */
    @FXML private Button generateReportsBtn;

    /**
     * This field connects the edit button.
     */
    @FXML private Button editBtn;

    /**
     * This field connects the log out button.
     */
    @FXML private Button logOutBtn;

    /**
     * This field connects the date view
     */
    @FXML private Text date;

    /**
     * This field connects the time view
     */
    @FXML private Text time;
    /**
     *
     */

    @FXML private Label accountTypeLabel;

    /**
     *
     */
    private  int myChosenAccount;

    /**
     * Initializes this controller class and sets initial values.
     *
     * @param url : This is the url.
     * @param rb : This is the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set the default account view which is account [1]
        myChosenAccount = 1;

        OtherTools otherTools = new OtherTools();
        DataBaseTools dataBaseTools = new DataBaseTools();

        // Setting the time in the main view
        otherTools.getTheTime(time);

        // Setting the date
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date theDate = new Date();
        date.setText(dateFormat.format(theDate));

        Double allIncome = 0.0;
        Double allExpenses = 0.0;
        Double currentMonthIncome = 0.0;
        Double expenses = 0.0;
        // Set the account balance
        try {
            double money = dataBaseTools.getCurrentAccountBalance(myChosenAccount);
            String type = dataBaseTools.getAccountType(myChosenAccount);
            String currentBalance = String.format("$%,.2f", money);
            moneyDisplay.setText(currentBalance);
            accountTypeLabel.setText(type);
            dataBaseTools.getTheTransactionList("SELECT * FROM transactions");
            dataBaseTools.getTheBudget();
            allIncome = dataBaseTools.getMyAllIncome();
            allExpenses = dataBaseTools.getMyAllExpenses();
            currentMonthIncome = dataBaseTools.getMyTotalPeriodOneIncome() + dataBaseTools.getMyTotalPeriodTwoIncome();
            expenses = Math.abs(dataBaseTools.getMyTotalPeriodOneSpending() + dataBaseTools.getMyTotalPeriodTwoSpending());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // Set the percents
        averagePeriodSpending.setText(String.format("%10.2f%%", (currentMonthIncome / expenses) * 100));
        expensesVsIncome.setText(String.format("%10.2f%%", (allIncome / allExpenses) * 100));

        // Populate the graph
        graphLbl.setText("Test");

        // Account changing link
        accountBalanceLnk.setText("Click to change account");
    }

    /**
     * This method handles the flow of control when the
     * view data button is pushed.
     *
     * @param theEvent : This is the event.
     */
    public void viewDataButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "ViewData.fxml", "View Data");
    }

    /**
     * This method handles the flow of control when the input data
     * button is pushed.
     *
     * @param theEvent : This is the event.
     */
    public void inputDataButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "InputData.fxml", "Input Data");
    }

    /**
     * This method handles the flow of control when the generate reports
     * button is pushed.
     *
     * @param theEvent : This is the event.
     */
    public void generateReportsButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "GenerateReportView.fxml", "Generate Reports");
    }

    /**
     * This method handles the flow of control when the log out button
     * is pushed.
     *
     * @param theEvent : This is the event.
     */
    public void logOutButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "LogInView.fxml", "Log In");
    }

    /**
     * This is a link that will allow the user to change which account
     * they would like to view, and the associated data.
     *
     * @param theEvent : This is the event.
     */
    public void accountBalanceLinkClicked(ActionEvent theEvent) {
        double newBalance;
        String accountType;
        new Flash(accountBalanceLnk).play();
        DataBaseTools dbTools = new DataBaseTools();
        try {
            if (myChosenAccount < 3) {
                myChosenAccount += 1;
            } else {
                myChosenAccount = 1;
            }
            newBalance = dbTools.getCurrentAccountBalance(myChosenAccount);
            accountType = dbTools.getAccountType(myChosenAccount);
            moneyDisplay.setText(String.format("$%,.2f", newBalance));
            accountTypeLabel.setText(accountType);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
}
/*
 * Multiline comment at the top of the file.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    @FXML private Text yearToDate;

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
     * Initializes the controller class
     *
     * @param url : This is the url.
     * @param rb : This is the resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        moneyDisplay.setText("$9,345.44");
        averagePeriodSpending.setText("+15.0%");
        yearToDate.setText("+3.4%");
        graphLbl.setText("Test");
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
        sceneChanger.changeScene(theEvent, "ViewDataView.fxml", "View Data");
    }

    /**
     * This method handles the flow of control when the input data
     * button is pushed.
     *
     * @param theEvent : This is the event.
     */
    public void inputDataButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "InputDataView.fxml", "Input Data");
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
     * This method handles the flow of control when the edit button is
     * pushed.
     *
     * @param theEvent : This is the event.
     */
    public void editButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "EditView.fxml", "Edit");
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
     * @param event : This is the event.
     */
    public void accountBalanceLinkClicked(ActionEvent event) {
        //TODO
    }
}
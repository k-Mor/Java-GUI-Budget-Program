/*
 * Multiline comment at the top of the file.
 */
package controller;
import Model.Budget;
import Model.DataBaseTools;
import Model.OtherTools;
import Model.Transaction;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import view.SceneChanger;

/**
 * This class is the controller for the ViewData.FXML file.
 *
 * @author : Kaleb
 * @version : 2019-04-10
 */
public class ViewDataController implements Initializable {

    /**
     * The time that is displayed.
     */
    @FXML private Text myTime;

    /**
     * The date that is displayed.
     */
    @FXML private Text myDate;

    /**
     * The edit button that triggers a view change.
     */
    @FXML private Button editBtn;

    /**
     * The table tabs
     */
    @FXML private Tab theTransactionsTab;
    @FXML private Tab theBudgetTab;
    @FXML private Tab theAccountsTab;


    /**
     * These are table columns for the transaction table view tab
     */
    @FXML private TableView<Transaction> myTransactionTable;
    @FXML private TableColumn<Transaction, Integer> myTransactionIDColumn;
    @FXML private TableColumn<Transaction, LocalDate> myTransactionDateColumn;
    @FXML private TableColumn<Transaction, String> myPurchaserColumn;
    @FXML private TableColumn<Transaction, String> myVendorColumn;
    @FXML private TableColumn<Transaction, String> myDescriptionColumn;
    @FXML private TableColumn<Transaction, String> myCategoryColumn;
    @FXML private TableColumn<Transaction, Double> myAmountColumn;
    @FXML private TableColumn<Transaction, Double> myBalanceAfterColumn;
    @FXML private TableColumn<Transaction, Integer> myFromAccountColumn;

    /**
     * These are the table columns for the budget table view tab.
     */
    @FXML private TableView<Budget> myBudgetTable;
    @FXML private TableColumn<Budget, Integer> myBudgetIDColumn;
    @FXML private TableColumn<Budget, String> myItemNameColumn;
    @FXML private TableColumn<Budget, LocalDate> myDateLastPaidColumn;
    @FXML private TableColumn<Budget, Double> myCurrentValueColumn;
    @FXML private TableColumn<Budget, Double> myBudgetedValueColumn;
    @FXML private TableColumn<Budget, Double> myExpectedMonthlyColumn;
    @FXML private TableColumn<Budget, LocalDate> myDueDateColumn;
    @FXML private TableColumn<Budget, String> myNotesColumn;


    /**
     * This is the overriden method that
     * initializes the controller class.
     *
     * @param theUrl : This is an unused parameter.
     * @param theRb  : This is an unused parameter.
     */
    @Override
    public void initialize(URL theUrl, ResourceBundle theRb) {
        // Set the transactions up
        setTheTransactionTable();

        // set the budget up
        setTheBudgetTable();

        // set the time and date
        OtherTools ot = new OtherTools();
        ot.getTheTime(myTime);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date theDate = new Date();
        myDate.setText(dateFormat.format(theDate));

        //set the accounts up
//        setTheAccountTable();

        // Disable the edit button until row is selected.
        editBtn.setDisable(true);
    }

    /**
     * This method is responsible for assigning the table cells to the
     * fields in the Transaction class.
     */
    public void setTheTransactionTable() {
        // Configure the table columns
        myTransactionIDColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("myTransactionId"));
        myTransactionDateColumn.setCellValueFactory(new PropertyValueFactory<Transaction, LocalDate>("myTransactionDate"));
        myPurchaserColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myPurchaser"));
        myVendorColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myVendor"));
        myDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myDescription"));
        myCategoryColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myCategory"));
        myAmountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("myAmount"));
        myBalanceAfterColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("myBalanceAfter"));
        myFromAccountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("myAccountFrom"));

        try {
            // Get a new instance of the dbtools.
            DataBaseTools dbTools = new DataBaseTools();

            // Adds all of the newly created transactions to the transaction table.
            myTransactionTable.getItems().addAll(dbTools.getTheTransactionList());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method is responsible for assigning the table cells to the
     * fields in the Budget class.
     */
    public void setTheBudgetTable() {
        // Configure the table columns
        myBudgetIDColumn.setCellValueFactory(new PropertyValueFactory<Budget, Integer>("myItemId"));
        myDateLastPaidColumn.setCellValueFactory(new PropertyValueFactory<Budget, LocalDate>("myDateLastPaid"));
        myItemNameColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("myItemName"));
        myCurrentValueColumn.setCellValueFactory(new PropertyValueFactory<Budget, Double>("myCurrentValue"));
        myBudgetedValueColumn.setCellValueFactory(new PropertyValueFactory<Budget, Double>("myBudgetedValue"));
        myExpectedMonthlyColumn.setCellValueFactory(new PropertyValueFactory<Budget, Double>("myExpectedMonthly"));
        myDueDateColumn.setCellValueFactory(new PropertyValueFactory<Budget, LocalDate>("myDueDate"));
        myNotesColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("myItemNotes"));

        try {
            // Get a new instance of the dbtools.
            DataBaseTools dbTools = new DataBaseTools();

            // Adds all of the newly created transactions to the transaction table.
            myBudgetTable.getItems().addAll(dbTools.getTheBudget());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

//    public void setTheAccountTable() {
//
//    }
    /**
     * This method handles the flow of control when the input data
     * button is pushed.
     *
     * @param theEvent : This is the event.
     */
    public void goBackButtonPushed(ActionEvent theEvent) {
        SceneChanger sceneChanger = new SceneChanger();
        sceneChanger.changeScene(theEvent, "HomePage.fxml", "Home Page");
    }

    /**
     * This method handles the flow of control when the edit
     * button is pushed.
     *
     */
    public void rowHasBeenSelected() {
        editBtn.setDisable(false);
    }

    /**
     *
     * @param theEvent
     */
    public void editButtonPushedTransactions(ActionEvent theEvent) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        System.out.println(theEvent.getSource());
        System.out.println(theEvent.toString());
        System.out.println(theEvent.getTarget());
        System.out.println(theEvent);

        // Figure out which tab you are on.
        Transaction transaction = myTransactionTable.getSelectionModel().getSelectedItem();
        EditTransactionViewController etvc = new EditTransactionViewController();

        sceneChanger.changeScene(theEvent, "EditTransactions.fxml", "Edit Page", transaction, etvc);
    }

//    /**
//     *
//     * @param theEvent
//     */
//    public void editButtonPushedBudget(ActionEvent theEvent) throws IOException {
//        SceneChanger sceneChanger = new SceneChanger();
//        Budget budgetItem = myBudgetTable.getSelectionModel().getSelectedItem();
//        EditTransactionViewController etvc = new EditTransactionViewController();
//
//        sceneChanger.changeScene(theEvent, "EditTransactions.fxml", "Edit Page", budgetItem, etvc);
//    }
}
/*
 * Multiline comment at the top of the file.
 */
package controller;
import Model.*;
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
     *
     */
    private DataBaseTools myDbTools;

    /**
     *
     */
    private Tab mySelectedTab;

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
    @FXML private Button myEditBtn;

    /**
     * The edit button DELETES.
     */
    @FXML private Button myDeleteBtn;

    /**
     *
     */
    @FXML private Label myTotalSearchedAmount;

    /**
     *
     */
    @FXML private Button myLoadTransactionsButton;

    /**
     *
     */
    @FXML private TabPane myPane;

    /**
     * These are the search options in the search tab.
     */
    @FXML private ChoiceBox<String> myChoiceBox;

    /**
     *
     */
    @FXML private TextField myKeyword;

    /**
     * Labels for the insights tab
     */
    @FXML private Label myAmountNeeded;
    @FXML private Label myTotalCashWithdrawal;
    @FXML private Label myPeriodCashFlow;
    @FXML private Label myMonthlyExpenses;
    @FXML private Label myTotalPeriodSavings;
    @FXML private Label myTotalBudgeted;
    @FXML private Label myTotalCurrent;
    @FXML private Label myTotalMonthly;
    @FXML private Label myTotalMonthlyIncome;

    /**
     * Labels for the insights tab
     */
    @FXML private Label myAmount;
    @FXML private Label myTotalCash;
    @FXML private Label myPeriodCash;
    @FXML private Label myMonthlyVs;
    @FXML private Label mySavings;
    @FXML private Label myTotalBudgetedValue;
    @FXML private Label myTotalCurrentValue;
    @FXML private Label myTotalMonthlyExpensesValue;
    @FXML private Label myTotalMonthlyIncomeValue;
    @FXML private Label myPeriod;

    /**
     * The table tabs
     */
    @FXML private Tab myTransactionsTab;
    @FXML private Tab myBudgetTab;
    @FXML private Tab myAccountTab;
    @FXML private Tab mySearchTab;

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
     * These are the table columns for the account tab.
     */
    @FXML private TableView<Account> myAccountTable;
    @FXML private TableColumn<Account, Integer> myAccountIDColumn;
    @FXML private TableColumn<Account, Double> myAccountBalanceColumn;
    @FXML private TableColumn<Account, String> myAccountTypeColumn;

    /**
     * These are table columns for the Search table view tab
     */
    @FXML private TableView<Transaction> mySearchTable;
    @FXML private TableColumn<Transaction, Integer> mySearchedTransactionIDColumn;
    @FXML private TableColumn<Transaction, LocalDate> mySearchedTransactionDateColumn;
    @FXML private TableColumn<Transaction, String> mySearchedPurchaserColumn;
    @FXML private TableColumn<Transaction, String> mySearchedVendorColumn;
    @FXML private TableColumn<Transaction, String> mySearchedDescriptionColumn;
    @FXML private TableColumn<Transaction, String> mySearchedCategoryColumn;
    @FXML private TableColumn<Transaction, Double> mySearchedAmountColumn;
    @FXML private TableColumn<Transaction, Double> mySearchedBalanceAfterColumn;
    @FXML private TableColumn<Transaction, Integer> mySearchedFromAccountColumn;

    /**
     * This is the overriden method that
     * initializes the controller class.
     *
     * @param theUrl : This is an unused parameter.
     * @param theRb  : This is an unused parameter.
     */
    @Override
    public void initialize(URL theUrl, ResourceBundle theRb) {
        // Set the transaction total in the search tab to 0
        myTotalSearchedAmount.setText("$0.00");

        // Set the tab starting position
        mySelectedTab = myTransactionsTab;

        // Get an instance of the dbTools
        DataBaseTools dbTools = new DataBaseTools();

        // Populate the fields in the dbMethods class
        try {
            String sql = "SELECT * FROM transactions ORDER BY itemId DESC";
            dbTools.getTheTransactionList(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        // Assign the instance
        myDbTools = dbTools;

        // set the budget up
        setTheBudgetTable(myDbTools);

        // Set up insights
        populateTheInsightTab(myDbTools);

        // Set the accounts up
        setTheAccountTable(myDbTools);

        // set the time and date
        OtherTools ot = new OtherTools();
        ot.getTheTime(myTime);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date theDate = new Date();
        myDate.setText(dateFormat.format(theDate));

        // Disable the edit button until row is selected.
        myEditBtn.setDisable(true);
        myDeleteBtn.setDisable(true);

        // Set the search tab options
        myChoiceBox.getItems().add("Search Transactions by date");
        myChoiceBox.getItems().add("Search Transactions by Purchaser");
        myChoiceBox.getItems().add("Search Transactions by Vendor");
        myChoiceBox.getItems().add("Search Transactions by Category");
        myChoiceBox.getItems().add("Search Transactions by Amount");
        myChoiceBox.getItems().add("Search Transactions by Account");
        myChoiceBox.getItems().add("Search Transactions by Description");

    }

    /**
     *
     * @param theEvent
     */
    public void loadTransactionsButtonPushed(ActionEvent theEvent) {
        // Set the transactions up
        String sql = "SELECT * FROM transactions ORDER BY itemId DESC";
        setTheTransactionTable(myDbTools, sql);
    }
    /**
     *
     */
    public void populateTheInsightTab(DataBaseTools dbTools) {
        // Set the insights labels
        Double amountNeeded = 0.0;
        Double totalBudgetedValue = 0.0;
        Double totalCurrentValue = 0.0;
        Double totalMonthlyValue = 0.0;
        Double totalMonthlyIncome = 0.0;
        Double currentAccountBalance = 0.0;
        Double totalCash = 0.0;


        try {
            myPeriod.setText("Current Period: " + dbTools.getMyCurrentPeriod());
            amountNeeded = dbTools.getCurrentAccountBalance(1) -
                    dbTools.getMyTotalCurrentBudgetAmount();
            totalCash = dbTools.getMyCashItems();
            totalBudgetedValue = dbTools.getMyBudgetAmount();
            totalCurrentValue = dbTools.getMyTotalCurrentBudgetAmount();
            totalMonthlyValue = dbTools.getMyTotalMonthlyExpenses();
            totalMonthlyIncome = dbTools.getMyTotalMonthlyIncome();
            currentAccountBalance = dbTools.getCurrentAccountBalance(1);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        Double savings = currentAccountBalance - totalCurrentValue;
        // My amount needed to cover expenses
        if (amountNeeded > 0) {
            amountNeeded = 0.0;
        }
        myAmount.setText(String.format("$%,2.2f", Math.abs(amountNeeded)));
        // Total cash withdrawal
        myTotalCash.setText(String.format("$%,2.2f",totalCash));

        // Period cash flow
        if (LocalDate.now().getDayOfMonth() < 15) {
            myPeriodCash.setText(String.format("$%,2.2f",
                    dbTools.getMyTotalPeriodOneIncome()));
        } else {
            myPeriodCash.setText(String.format("$%,2.2f",
                    dbTools.getMyTotalPeriodTwoIncome()));
        }
        // Monthly expenses Vs. Income
        myMonthlyVs.setText(String.format("$%,2.2f", totalMonthlyIncome - totalMonthlyValue));

        //See the savings
        if (savings < 0.0) {
            savings = 0.0;
        }
        mySavings.setText(String.format("$%,2.2f", savings));
        // Total budgeted values
        myTotalBudgetedValue.setText(String.format("$%,2.2f", totalBudgetedValue));
        // Total currently budgeted values
        myTotalCurrentValue.setText(String.format("$%,2.2f", totalCurrentValue));
        // Total monthly amount of money to be spent
        myTotalMonthlyExpensesValue.setText(String.format("$%,2.2f", totalMonthlyValue));
        // Total monthly income.
        myTotalMonthlyIncomeValue.setText(String.format("$%,2.2f", totalMonthlyIncome));
    }

    /**
     * This method is responsible for assigning the table cells to the
     * fields in the Transaction class.
     */
    public void setTheTransactionTable(DataBaseTools dbTools, String theSQL) {
        // Configure the Transaction table columns
        myTransactionIDColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("myTransactionId"));
        myTransactionDateColumn.setCellValueFactory(new PropertyValueFactory<Transaction, LocalDate>("myTransactionDate"));
        myPurchaserColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myPurchaser"));
        myVendorColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myVendor"));
        myDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myDescription"));
        myCategoryColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myCategory"));
        myAmountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("myAmount"));
        myBalanceAfterColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("myBalanceAfter"));
        myFromAccountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("myAccountFrom"));

        // Configure the search table columns
        mySearchedTransactionIDColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("myTransactionId"));
        mySearchedTransactionDateColumn.setCellValueFactory(new PropertyValueFactory<Transaction, LocalDate>("myTransactionDate"));
        mySearchedPurchaserColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myPurchaser"));
        mySearchedVendorColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myVendor"));
        mySearchedDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myDescription"));
        mySearchedCategoryColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("myCategory"));
        mySearchedAmountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("myAmount"));
        mySearchedBalanceAfterColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("myBalanceAfter"));
        mySearchedFromAccountColumn.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("myAccountFrom"));

        try {
            // Adds all of the newly created transactions to the transaction table.
            if (mySelectedTab.equals(mySearchTab)) {
                mySearchTable.getItems().addAll(dbTools.getTheTransactionList(theSQL));

            } else {
                myTransactionTable.getItems().addAll(dbTools.getTheTransactionList(theSQL));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method is responsible for assigning the table cells to the
     * fields in the Budget class.
     */
    public void setTheBudgetTable(DataBaseTools dbTools) {
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

            // Adds all of the newly created transactions to the transaction table.
            myBudgetTable.getItems().addAll(dbTools.getTheBudget());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method is responsible for assigning the table cells to the
     * fields in the Budget class.
     */
    public void setTheAccountTable(DataBaseTools dbTools) {
        // Configure the table columns
        myAccountIDColumn.setCellValueFactory(new PropertyValueFactory<Account, Integer>("myAccountId"));
        myAccountBalanceColumn.setCellValueFactory(new PropertyValueFactory<Account, Double>("myAccountBalance"));
        myAccountTypeColumn.setCellValueFactory(new PropertyValueFactory<Account, String>("myAccountType"));

        try {
            // Adds all of the newly created transactions to the transaction table.
            myAccountTable.getItems().addAll(dbTools.getTheAccounts());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

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
     * This method handles wakes up the edit button when a row has been selected.
     */
    public void rowHasBeenSelected() {
        myEditBtn.setDisable(false);
        myDeleteBtn.setDisable(false);
    }

    /**
     * This method makes the selected tab equal to whatever tab the user has clicked on.
     */
    public void selectedTab() {
        mySelectedTab = myPane.getSelectionModel().selectedItemProperty().get();
    }

    /**
     *
     * @param theEvent
     */
    public void editButtonPushedTransactions(ActionEvent theEvent) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();

        // Figure out which tab you are on.
        if (mySelectedTab.equals(myTransactionsTab)) {
            Transaction transaction = myTransactionTable.getSelectionModel().getSelectedItem();
            EditTransactionViewController editTrans = new EditTransactionViewController();

            sceneChanger.changeScene(theEvent, "EditTransactions.fxml", "Edit Page", transaction, editTrans);
        } else if (mySelectedTab.equals(myBudgetTab)) {
            // This is where everything for the budget view goes.
            Budget selectedBudgetItem = myBudgetTable.getSelectionModel().getSelectedItem();
            EditBudgetViewController editBudget = new EditBudgetViewController();
            sceneChanger.changeScene(theEvent, "EditBudget.fxml", "Edit Page", selectedBudgetItem, editBudget);
        } else if (mySelectedTab.equals(myAccountTab)) {
            // This is where everything for the budget view goes.
            Account selectedAccount = myAccountTable.getSelectionModel().getSelectedItem();
            EditAccountViewController editAccount = new EditAccountViewController();
            sceneChanger.changeScene(theEvent, "EditAccounts.fxml", "Edit Page", selectedAccount, editAccount);
        }
    }

    public void searchButtonPushed(ActionEvent theEvent) {
        mySelectedTab = mySearchTab;
        String value = myChoiceBox.getValue();
        mySearchTable.getItems().clear();
        String sql = "";

        if (value == "Search Transactions by date") {
            // Handling the date search
            java.sql.Date date = java.sql.Date.valueOf(myKeyword.getText());
            sql = "SELECT * FROM transactions WHERE dateOfTransaction ='" + date + "'";
        } else if (value == "Search Transactions by Purchaser") {
            // Handling the purchaser
            sql = "SELECT * FROM transactions WHERE purchaser LIKE'%" + myKeyword.getText() + "%'";
        } else if (value == "Search Transactions by Vendor") {
            // Handling the Vendor
            sql = "SELECT * FROM transactions WHERE vendor LIKE'%" + myKeyword.getText() + "%'";
        } else if (value == "Search Transactions by Category") {
            // Handling the category
            sql = "SELECT * FROM transactions WHERE category ='" + myKeyword.getText() + "'";
        } else if (value == "Search Transactions by Amount") {
            // Handling the specific amount
            sql = "SELECT * FROM transactions WHERE amount LIKE'%" + myKeyword.getText() + "%'";
        } else if (value == "Search Transactions by Account") {
            // Handling the account
            sql = "SELECT * FROM transactions WHERE accountID ='" + myKeyword.getText() + "'";
        } else if (value == "Search Transactions by Description"){
            sql = "SELECT * FROM transactions WHERE description LIKE '%" + myKeyword.getText() + "%'";
        }
        try {
            setTheTransactionTable(myDbTools, sql);
            myTotalSearchedAmount.setText(String.format("$%,.2f", myDbTools.getTransactionAmount(sql)));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method handles the flow of control when the input data
     * button is pushed.
     *
     * @param theEvent : This is the event.
     */
    public void deleteButtonPushed(ActionEvent theEvent) {
        // Figure out which tab you are on.
        if (mySelectedTab.equals(myTransactionsTab)) {
            Integer transaction = myTransactionTable.getSelectionModel().getSelectedItem().getMyTransactionId();
            myDbTools.deleteFromDb(transaction, "transactions", "itemId");

            //Reset
            myTransactionTable.getItems().clear();
            String sql = "SELECT * FROM transactions";
            setTheTransactionTable(myDbTools, sql);

        } else if (mySelectedTab.equals(myBudgetTab)) {
            Integer selectedBudgetItem = myBudgetTable.getSelectionModel().getSelectedItem().getMyItemId();
            myDbTools.deleteFromDb(selectedBudgetItem, "budget", "itemId");

            //Reset
            myBudgetTable.getItems().clear();
            setTheBudgetTable(myDbTools);

        } else if (mySelectedTab.equals(myAccountTab)) {
            // This is where everything for the budget view goes.
            Integer selectedAccount = myAccountTable.getSelectionModel().getSelectedItem().getMyAccountId();
            myDbTools.deleteFromDb(selectedAccount, "accounts", "itemId");

            //Reset
            myAccountTable.getItems().clear();
            setTheAccountTable(myDbTools);
        }
    }

}
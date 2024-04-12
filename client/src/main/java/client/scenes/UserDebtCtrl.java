package client.scenes;

import client.utils.ServerUtils;
import commons.Debt;
import commons.Event;
import commons.Expense;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDebtCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private commons.Event event;
    private User user;
    private String selectedDebt;
    private List<String> instructions = new ArrayList<>();
    @FXML
    private ListView<String> payments;
    @FXML
    private AnchorPane error;
    @FXML
    private Button goBack;
    @FXML
    private Button payButton;
    @FXML
    private AnchorPane success;
    @FXML
    private Button details;
    @FXML
    private TitledPane showedDetails;
    /**
     * constructor for the page
     * @param server for accessing the database
     * @param mainCtrl for accessing other pages
     */
    @Inject
    public UserDebtCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * stringconverter for the list of debts
     */
    private StringConverter<String> sc = new StringConverter<String>() {
        @Override
        public String toString(String string) {
            Scanner scanner = new Scanner(string);
            int payerId = Integer.parseInt(scanner.next());
            int payeeId = Integer.parseInt(scanner.next());
            double amount = Double.parseDouble(scanner.next());
            return  "You owe " +
                    server.getUserById(payeeId).getUsername() + " " + amount + " euros";
        }

        @Override
        public String fromString(String string) {
            return null;
        }
    };
    /**
     * initializes the data of the page
     * @param list stores the payments that will take place
     * @param event of the debts
     * @param user that will pay their debts
     */
    public void setData(List<String> list, Event event, User user) {
        this.user = user;
        this.event = event;
        details.setText("Show Details");
        //showedDetails.getChildren().get(0).setVisible(false);
        payButton.setDisable(false);
        goBack.setDisable(false);
        error.setVisible(false);
        error.getChildren().get(0).setVisible(false);
        error.getChildren().get(1).setVisible(false);
        success.setVisible(false);
        success.getChildren().get(0).setVisible(false);
        success.getChildren().get(1).setVisible(false);
        payments.setCellFactory(param -> new TextFieldListCell<>(sc));

        for (String st : list) {
            Scanner scanner = new Scanner(st);
            int payerId = Integer.parseInt(scanner.next());
            int payeeId = Integer.parseInt(scanner.next());
            double amount = Double.parseDouble(scanner.next());
            String in = "You owe " +
                    server.getUserById(payeeId).getUsername() + " " + amount + " euros";
            if (payerId == user.getUserID())
                this.instructions.add(st);
        }
        payments.getItems().setAll(instructions);
    }

    /**
     * returns to the settle debts page of the event
     * @param actionEvent when the button is clicked
     */
    public void exit(ActionEvent actionEvent) {
        mainCtrl.showSettleDebts(event);
    }

    /**
     * selects a debt from the list
     * @param mouseEvent when the debt is clicked on
     */
    public void selectDebt(MouseEvent mouseEvent) {
        selectedDebt = payments.getSelectionModel().getSelectedItem();

    }

    /**
     * pays the selected debt by an expense
     * @param actionEvent when the button is clicked
     */
    public void payDebt(ActionEvent actionEvent) {
        if (selectedDebt != null) {
            Scanner scanner = new Scanner(selectedDebt);
            int payerId = Integer.parseInt(scanner.next());
            int payeeId = Integer.parseInt(scanner.next());
            double amount = Double.parseDouble(scanner.next());
            Expense expense = new Expense(event);
            expense.setPayer(user);
            List<User> payingParticipants = new ArrayList<>();
            payingParticipants.add(server.getUserById(payeeId));
            expense.setPayingParticipants(payingParticipants);
            expense.setName(user.getUsername() +
                    " settled debt with " + server.getUserById(payeeId).getUsername());
            expense.setAmount(amount);
            Expense temp = server.addExpense(expense);
            expense.setId(temp.getId());
            List<Expense> expenses = new ArrayList<>(event.getExpenses());
            if (!expenses.contains(expense)) {
                expenses.add(expense);
            }
            Debt debt = new Debt(user, server.getUserById(payerId), amount, event);
            server.addDebt(debt);
            event.setExpenses(expenses);
            server.updateEvent(event);
            payments.getItems().remove(selectedDebt);
            if (payments.getItems().size() == 0) {
                noDebtsLeft();
            }
        }
        else
            errorMessage();
    }

    /**
     * returns to the settledebts page when all debts of a participant is paid
     */
    private void noDebtsLeft() {
        payButton.setDisable(true);
        goBack.setDisable(true);
        success.toFront();
        success.setVisible(true);
        success.getChildren().get(0).setVisible(true);
        success.getChildren().get(1).setVisible(true);
    }

    /**
     * if no debt is chosen, an error occurs.
     */
    private void errorMessage() {
        payButton.setDisable(true);
        goBack.setDisable(true);
        error.toFront();
        error.setVisible(true);
        error.getChildren().get(0).setVisible(true);
        error.getChildren().get(1).setVisible(true);
    }

    /**
     * closes the error message
     * @param actionEvent when the button is clicked
     */
    public void closePopup(ActionEvent actionEvent) {
        payButton.setDisable(false);
        goBack.setDisable(false);
        error.setVisible(false);
        error.getChildren().get(0).setVisible(false);
        error.getChildren().get(1).setVisible(false);
    }

    /**
     * goes back to the debts page
     * @param actionEvent when the button is clicked
     */
    public void goToDebts(ActionEvent actionEvent) {
        mainCtrl.removeOpenDebt(event, user);
    }

    /**
     * shows/hides details of the selected debt
     * @param actionEvent when the button is clicked
     */
    public void showHideDetails(ActionEvent actionEvent) {
        if (details.getText().equals("Show Details")) {
            if (selectedDebt != null) {
                Scanner scanner = new Scanner(selectedDebt);
                int payerId = Integer.parseInt(scanner.next());
                int payeeId = Integer.parseInt(scanner.next());
                double amount = Double.parseDouble(scanner.next());
                User payee = server.getUserById(payeeId);
                Label contentLabel = new Label(payee.getUsername() + "\n%iban: " + payee.getIban()
                        + "\n%bic: " + payee.getBic());
                showedDetails.setContent(contentLabel);
                showedDetails.setExpanded(true);
                details.setText("%hide-details");
            } else {
                errorMessage();
            }
        } else {
            details.setText("%show-details");
            showedDetails.setExpanded(false);
        }
    }
}

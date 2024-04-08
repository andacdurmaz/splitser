package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javax.inject.Inject;
public class ExpenseInfoCtrl  {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private Expense expense;

    @FXML
    private Label title;

    @FXML
    private Label amount;

    @FXML
    private Label date;

    @FXML
    private ListView<User> payingParticipantsList;

    @FXML
    private Label payer;

    @FXML
    private AnchorPane warning;
    /**
     * constructor for the page
     * @param server for accessing the server
     * @param mainCtrl for accessing the other pages
     */
    @Inject
    public ExpenseInfoCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * deletes the expense from the database
     * @param actionEvent when the button is clicked
     */
    public void deleteExpense(ActionEvent actionEvent) {
        warning.setVisible(true);
    }

    /**
     * goes back to the event info page
     * @param actionEvent when the button is clicked
     */
    public void back(ActionEvent actionEvent) {
        mainCtrl.showEventInfo(event);
    }

    /**
     * sets the event of the expense
     * @param event of the expense
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * sets the expense of the page
     * @param expense of the page
     */
    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    /**
     * initializes the texts on the page
     */
    public void setData() {
        payingParticipantsList.setCellFactory(param ->
                new TextFieldListCell<>(new StringConverter<>() {
                    @Override
                    public String toString(User user) {
                        double total = expense.getAmount()/(expense.getPayingParticipants().size());
                        return user.getUsername() + " owes " + total + " \u20AC";
                    }
                    @Override
                    public User fromString(String string) {
                        return null;
                    }
                }));
        if (expense != null) {
            title.setText(expense.getName());
            amount.setText(expense.getAmount() + " \u20AC");
            payer.setText(expense.getPayer().getUsername());
            date.setText(String.valueOf(expense.getDate()));
            payingParticipantsList.getItems().setAll(expense.getPayingParticipants());
        }
        warning.setVisible(false);
    }


    /**
     * confirmation for the deletion of the expense
     * @param actionEvent when button is clicked, the expense is deleted
     */
    public void delete(ActionEvent actionEvent) {
        event.getExpenses().remove(expense);
        server.updateEvent(event);
        server.deleteExpense(expense);
        warning.setVisible(false);
        mainCtrl.showEventInfo(event);
    }

    /**
     * closes the warning
     * @param actionEvent when the cancel button is clicked
     */
    public void goBack(ActionEvent actionEvent) {
        warning.setVisible(false);
    }
}

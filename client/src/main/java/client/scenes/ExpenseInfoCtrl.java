package client.scenes;

import client.services.ExpenseInfoService;
import commons.Debt;
import commons.Event;
import commons.Expense;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import javax.inject.Inject;


public class ExpenseInfoCtrl  {

    private Event event;
    private Expense expense;
    private final ExpenseInfoService service;

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
    private Label expenseTag;

    @FXML
    private AnchorPane warning;

    /**
     * Constructor
     *
     * @param service DI service
     */
    @Inject
    public ExpenseInfoCtrl(ExpenseInfoService service) {
        this.service = service;
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
        service.getMainCtrl().showEventInfo(event);
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
                        double total = expense.getAmount()/
                                (expense.getPayingParticipants().size()+1);
                        return user.getUsername() + " " + service
                                .getString("owes") + " " + total + " \u20AC";
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
            if(expense.getExpenseTag()!=null){
                Color color = Color.valueOf(expense.getExpenseTag().getColour());
                expenseTag.setBackground(new Background(
                        new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                expenseTag.setText(expense.getExpenseTag().getName());
            }
            else expenseTag.setText("This is a debt");
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
        for (User u : expense.getPayingParticipants()) {
            double debtAmount = expense.getAmount()/(expense.getPayingParticipants().size() + 1);
            Debt debt = new Debt(expense.getPayer(), u, debtAmount, event);
            service.getServer().addDebt(debt);
        }
        event.getExpenses().remove(expense);
        service.getServer().updateEvent(event);
        service.getServer().send("/app/expenses/del", expense);
        warning.setVisible(false);
        service.getMainCtrl().showEventInfo(event);
    }

    /**
     * closes the warning
     * @param actionEvent when the cancel button is clicked
     */
    public void goBack(ActionEvent actionEvent) {
        warning.setVisible(false);
    }
}

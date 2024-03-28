package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import commons.ExpenseTag;
import commons.User;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;

import javax.inject.Inject;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class AddOrEditExpenseCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private final Event event;
    private Expense expense;
    @FXML
    private ExpenseTag expenseTag;

    @FXML
    private Button ok;
    @FXML
    private ComboBox<User> payer;
    @FXML
    private TextField name;
    @FXML
    private Spinner<Double> amount;

    /**
     * Constructor
     * @param server   serverUtils
     *
     * @param mainCtrl mainCtrl
     * @param event event of expense
     */
    @Inject
    public AddOrEditExpenseCtrl(ServerUtils server, MainCtrl mainCtrl, Event event) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.event = event;
    }

    /**
     * Set expense
     * @param expense to set
     */
    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    /**
     * Cancel add/edit
     */
    public void cancel() {
        clearFields();
        mainCtrl.showEventInfo(event);
    }

    /**
     * Confirm add/edit
     */
    public void ok() {
        if(expense == null) {
            expense = getExpense();
            try {
                Expense temp = server.addExpense(getExpense());
                expense.setId(temp.getId());
            } catch (WebApplicationException e) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }
            List<Expense> expenses = event.getExpenses();
            if(!expenses.contains(expense)) {
                expenses.add(expense);
            }
            event.setExpenses(expenses);
            server.updateEvent(event);
            clearFields();
            mainCtrl.showEventInfo(event);
        }
        else {
            selectedExpense();
        }

    }
    private void selectedExpense() {
        try {
            List<Expense> expenses = event.getExpenses();
            expenses.remove(expense);
            expense.setExpenseTag(expenseTag);
            expense.setAmount(amount.getValue());
            expense.setName(name.getText());
            expense.setPayer(payer.getValue());
            expense.setPayingParticipants(selectedParticipants());
            server.updateExpense(expense);
            expenses.add(expense);
            event.setExpenses(expenses);
            server.updateEvent(event);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
    }
    /**
     * Get expense for adding a new expense
     *
     * @return expense
     */
    private Expense getExpense() {
        var p = new Expense(event);
        p.setPayer(payer.getValue());
        p.setName(name.getText());
        p.setAmount(amount.getValue());
        p.setExpenseTag(expense.getExpenseTag());
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.addAll(selectedParticipants());
        return p;
    }

    /**
     * IMPORTANT
     * This method should be changed
     * for now it returns all participants but it should be only
     * the selected ones
     * @return paying participants
     */
    private List<User> selectedParticipants() {
        return event.getParticipants();
    }

    /**
     * Clears fields
     */
    private void clearFields() {

    }

    /**
     * @param e key event
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
            default:
                break;
        }
    }

}


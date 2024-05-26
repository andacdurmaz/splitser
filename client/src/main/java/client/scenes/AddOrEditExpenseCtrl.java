package client.scenes;

import client.services.AddOrEditExpenseService;
import commons.*;
import jakarta.ws.rs.WebApplicationException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.StringConverter;

import javax.inject.Inject;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddOrEditExpenseCtrl implements Initializable {
    private final AddOrEditExpenseService service;
    private Event event;
    private Expense expense;

    @FXML
    private ComboBox<ExpenseTag> expenseTag;
    @FXML
    private TextField howMuch;
    @FXML
    private ComboBox currency;
    @FXML
    private AnchorPane error;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<User> payer;
    @FXML
    private TextField whatFor;
    @FXML
    private DatePicker when;
    @FXML
    private CheckBox allParticipants;
    @FXML
    private CheckBox someParticipants;
    @FXML
    private VBox someParticipantsSelector;

    /**
     * Constructor
     *
     * @param service service
     * @param event   event of expense
     */
    @Inject
    public AddOrEditExpenseCtrl(AddOrEditExpenseService service, Event event) {
        this.service = service;
        this.event = event;
    }

    /**
     * removes a participant from the vbox options
     *
     * @param payer the removed participant
     */
    private void excludePayerFromVBox(User payer) {
        for (Node n : someParticipantsSelector.getChildren()) {
            if (n instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) n;
                String text = checkBox.getText();
                int index = text.indexOf("(id: ");
                long id = Long.parseLong(text.substring(index + 5, text.length() - 1));
                if (id == payer.getUserID()) {
                    someParticipantsSelector.getChildren().remove(checkBox);
                    break;
                }
            }
        }

    }

    /**
     * during each page load, makes sure
     * the participant combobox display only usernames
     */
    public void initialize() {
        service.getServer().setSession();
        updatePayingParticipants();
        if (!expenseTag.getItems().isEmpty()) {
            expenseTag.setCellFactory(param -> new TextFieldListCell<>(new StringConverter<>() {
                @Override
                public String toString(ExpenseTag tag) {
                    return tag.getName();
                }

                @Override
                public ExpenseTag fromString(String string) {
                    return null;
                }
            }));
        }

        payer.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getUsername();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });
        expenseTag.setConverter(new StringConverter<ExpenseTag>() {
            @Override
            public String toString(ExpenseTag tag) {
                return tag.getName();
            }

            @Override
            public ExpenseTag fromString(String string) {
                return null;
            }
        });
    }

    /**
     * checks if a participant is in the vbox
     *
     * @param payer the checked user
     * @return true if the user is present
     */
    private boolean isPayerInVBox(User payer) {
        for (Node n : someParticipantsSelector.getChildren()) {
            if (n instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) n;
                String text = checkBox.getText();
                int index = text.indexOf("(id: ");
                long id = Long.parseLong(text.substring(index + 5, text.length() - 1));
                if (id == payer.getUserID()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Clears fields
     */
    public void clearFields() {
        expenseTag.setValue(event.getExpenseTags().get(0));
        howMuch.clear();
        currency.setValue(null);
        payer.setValue(null);
        whatFor.clear();
        when.setValue(null);
        allParticipants.setSelected(false);
        someParticipants.setSelected(false);
        someParticipantsSelector.setVisible(false);
        someParticipantsSelector.getChildren().clear();

    }

    /**
     * Checkbox method for allParticipants
     * if-clause is there to check only when the checkbox is
     * selected and not when it is de-selected.
     */
    @FXML
    private void allParticipantsPay() {
        if (allParticipants.isSelected()) {
            someParticipants.setSelected(false);
            someParticipantsSelector.setVisible(false);
        }
    }

    /**
     * Checkbox method for some participant
     * if-clause is there to check only when the checkbox is
     * selected anc not when it is de-selected.
     */
    @FXML
    private void someParticipantsPay() {
        if (someParticipants.isSelected()) {
            allParticipants.setSelected(false);
            someParticipantsSelector.setVisible(true);
        }
    }

    /**
     * Set expense
     *
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
        service.showEventInfo(event);
    }

    /**
     * Confirm add/edit
     */
    public void ok() {
        if (expense == null) {
            expense = getExpense();
            if (expense == null) {
                return;
            }
            try {
                Expense temp = service.addExpense(getExpense());
                expense.setId(temp.getId());
            } catch (WebApplicationException e) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }
            List<Expense> expenses = new ArrayList<>(event.getExpenses());
            if (!expenses.contains(expense)) {
                expenses.add(expense);
            }
            event.setExpenses(expenses);
            for (User u : expense.getPayingParticipants()) {
                double debtAmount = expense.getAmount() /
                        (expense.getPayingParticipants().size() + 1);
                Debt debt = new Debt(u, expense.getPayer(), debtAmount, event);
                service.addDebt(debt);

            }
            clearFields();
            service.updateAndShow(event);
        } else {
            selectedExpense();
        }

    }

    /**
     * warns the user if a mandatory field is not filled
     */
    private void errorMessage() {
        error.toFront();
        error.getChildren().get(1).setVisible(true);
        error.getChildren().get(0).setVisible(true);
        error.setVisible(true);
    }

    /**
     * edits the existing expense
     */
    private void selectedExpense() {
        try {
            List<Expense> expenses = new ArrayList<>(event.getExpenses());
            Expense oldExpense = new Expense();
            oldExpense.setAmount(expense.getAmount());
            oldExpense.setExpenseDate(expense.getDate());
            oldExpense.setPayer(expense.getPayer());
            oldExpense.setPayingParticipants(new ArrayList<>(expense.getPayingParticipants()));
            expenses.remove(expense);
            expense.setExpenseTag(expenseTag.getValue());
            expense.setAmount(Double.parseDouble(howMuch.getText()));
            expense.setName(whatFor.getText());
            expense.setPayer(payer.getValue());
            expense.setPayingParticipants(selectedParticipants());
            if (when.getValue() != null) {
                expense.setExpenseDate(Date.from(
                        when.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
                ));
            }
            service.updateExpense(expense);
            expenses.add(expense);
            event.setExpenses(expenses);
            for (User u : oldExpense.getPayingParticipants()) {
                double debtAmount = oldExpense.getAmount() /
                        (oldExpense.getPayingParticipants().size() + 1);
                Debt debt = new Debt(oldExpense.getPayer(), u, debtAmount, event);
                service.addDebt(debt);
            }
            for (User u : expense.getPayingParticipants()) {
                double debtAmount = expense.getAmount() /
                        (expense.getPayingParticipants().size() + 1);
                Debt debt = new Debt(u, expense.getPayer(), debtAmount, event);
                service.addDebt(debt);
            }
            service.updateAndShow(event);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
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
        if (whatFor.getText().isEmpty()) {
            ((Label) error
                    .getChildren()
                    .get(0))
                    .setText(service
                            .getString("the-expense-should-have-a-title"));
            errorMessage();
            return null;
        }
        p.setName(whatFor.getText());
        try {
            Double d = Double.parseDouble(howMuch.getText());
            if(d < 0)
            {
                ((Label) error.getChildren().get(0))
                    .setText(service.getString("expense-value-negative"));
                errorMessage();
                return null;
            }
            else if(d == 0)
            {
                ((Label) error.getChildren().get(0))
                    .setText(service.getString("expense-value-zero") );
                errorMessage();
                return null;
            }
            p.setAmount(d);
        }
        catch (NumberFormatException n){
            ((Label) error
                    .getChildren()
                    .get(0))
                    .setText(service
                            .getString("the-amount-should-be-a-number"));
            errorMessage();
            return null;
        }
        p.setExpenseTag(expenseTag.getValue());
        if (when.getValue() != null) {
            p.setExpenseDate(Date.from(
                    when.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
            ));
        }

        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.addAll(selectedParticipants());
        if (payingParticipants.size() == 0) {
            ((Label) error.getChildren().get(0))
                    .setText(service
                            .getString("the-expense-should-have-at-least-one-paying-participant"));
            errorMessage();
            return null;
        }
        p.setPayingParticipants(payingParticipants);
        return p;
    }

    /**
     * checks if the all participants button is selected,
     * if it is selected, it returns all participants
     * if not, checks for the selected checkboxes under some participants
     *
     * @return paying participants
     */
    private List<User> selectedParticipants() {
        if (allParticipants.isSelected()) {
            List<User> selected = new ArrayList<>(event.getParticipants());
            selected.remove(payer.getValue());
            return selected;
        } else {
            List<User> selected = new ArrayList<>();
            for (Node n : someParticipantsSelector.getChildren()) {
                if (((CheckBox) n).isSelected()) {
                    String text = ((CheckBox) n).getText();
                    int index = text.indexOf("(id: ");
                    long id = Long.parseLong(text.substring(index + 5, text.length() - 1));
                    selected.add(service.getUserById(id));
                }
            }
            return selected;

        }
    }


    /**
     * @param e key event
     */
    @FXML
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                if (payer.isFocused()) {
                    payer.show();
                    break;
                } else if (expenseTag.isFocused()) {
                    expenseTag.show();
                    break;
                } else if (currency.isFocused()) {
                    currency.show();
                    break;
                } else {
                    ok();

                }
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Setup method
     *
     * @param event   event where the expense exists
     * @param expense expense to add or edit
     */
    public void setup(Event event, Expense expense) {
        error.setVisible(false);
        error.getChildren().get(0).setVisible(false);
        error.getChildren().get(1).setVisible(false);
        this.event = event;
        this.expense = expense;
        setFields();
        expenseTag.getItems().setAll(event.getExpenseTags());
        for (User u : event.getParticipants()) {
            someParticipantsSelector.getChildren()
                    .add(new CheckBox(u.getUsername() + "(id: " + u.getUserID() + ")"));
        }
        if (expense != null) {
            excludePayerFromVBox(expense.getPayer());

            List<Long> ids = expense.getPayingParticipants()
                    .stream().map(q -> q.getUserID()).toList();
            for (Node n : someParticipantsSelector.getChildren()) {
                String text = ((CheckBox) n).getText();
                int index = text.indexOf("(id: ");
                long id = Long.parseLong(text.substring(index + 5, text.length() - 1));
                if (ids.contains(id)) {
                    ((CheckBox) n).setSelected(true);
                }
            }
        }
        payer.setItems(FXCollections.observableList(event.getParticipants()));
        if (expense != null) {
            payer.setValue(expense.getPayer());
            expenseTag.getSelectionModel().select(expense.getExpenseTag());
            okButton.setText(service.getString("edit"));
        } else {
            payer.setValue(event.getParticipants().get(0));
            expenseTag.setValue(event.getExpenseTags().get(0));
            okButton.setText(service.getString("add"));
        }

    }


    /**
     * fills the field with the edited expense's info
     * or with blank if a new expense is added
     */
    public void setFields() {
        if (expense == null)
            clearFields();
        else {
            expenseTag.setValue(expense.getExpenseTag());
            howMuch.setText(String.valueOf(expense.getAmount()));
            currency.setValue(null);
            whatFor.setText(expense.getName());
            if (expense.getDate() != null) {
                when.setValue(Instant.ofEpochMilli(
                                expense.getDate().getTime())
                        .atZone(ZoneId.systemDefault()).toLocalDate());
            } else
                when.setValue(null);

            if (expense.getPayingParticipants().size() == event.getParticipants().size() - 1) {
                allParticipants.setSelected(true);
                someParticipants.setSelected(false);
                allParticipantsPay();
            } else {
                someParticipants.setSelected(true);
                someParticipantsPay();

            }
        }
    }

    /**
     * every time new payer is selected, the list is changed
     *
     * @param actionEvent changing of the payer
     */
    public void handlePayerSelection(ActionEvent actionEvent) {
        updatePayingParticipants();
    }

    /**
     * removes the payer from the checkboxes whenever the payer in the combobox changes
     */
    private void updatePayingParticipants() {
        payer.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (oldValue != null && !isPayerInVBox(oldValue)) {
                        someParticipantsSelector.getChildren()
                                .add(new CheckBox(oldValue.getUsername()
                                        + "(id: " + oldValue.getUserID() + ")"));
                    }
                    if (newValue != null && (oldValue == null || !newValue.equals(oldValue))) {
                        excludePayerFromVBox(newValue);
                    }
                });
    }

    /**
     * closes the error message
     *
     * @param actionEvent when the button is clicked
     */
    public void goBack(ActionEvent actionEvent) {
        error.setVisible(false);
    }
}


package client.scenes;

import client.services.EventInfoService;
import commons.Event;
import commons.Expense;
import commons.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class EventInfoCtrl {
    private Event event;
    private Expense selectedExpense;

    private User selectedParticipant;
    private User selectedExpenseParticipant;
    @FXML
    private AnchorPane noParticipantPane;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField eventTitle;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField description;
    @FXML
    private Label participantsLabel;
    @FXML
    private Label expensesLabel;
    @FXML
    private ComboBox<User> expenseComboBox;
    @FXML
    private ComboBox<User> participantCombobox;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ListView<Expense> expenseList;
    @FXML
    private Button paidByButton;
    @FXML
    private Button includingButton;
    private final EventInfoService service;
    @FXML
    private Button noParticipantErrButton;
    @FXML
    private Button editTitle;
    @FXML
    private Button editDescription;
    @FXML
    private Button invitation;
    @FXML
    private Button statistics;
    @FXML
    private Button expenseTag;
    @FXML
    private Button addParticipant;
    @FXML
    private Label eventCode;

    /**
     * Constructor
     *
     * @param service DI service
     */
    @Inject
    public EventInfoCtrl(EventInfoService service) {
        this.service = service;
    }

    /**
     * makes the combobox's display names of the users
     */
    private StringConverter<Expense> sc = new StringConverter<Expense>() {
        @Override
        public String toString(Expense expense) {
            return "(" + expense.getDate() + ") " + expense.getPayer().getUsername() +
                    " " + service.getString("paid")
                    + " " + expense.getAmount()
                    + " " + service.getString("for")
                    + " " + expense.getName();
        }

        @Override
        public Expense fromString(String string) {
            return null;
        }
    };

    private StringConverter<User> su = new StringConverter<User>() {
        @Override
        public String toString(User user) {
            return user.getUsername(); // Display the username
        }

        @Override
        public User fromString(String string) {
            return null;
        }
    };

    /**
     * intitialization
     * done on a singleton process
     */
    public void initialize() {
        noParticipantPane.setVisible(false);
        disableEditingDesc();
        disableEditingTitle();
        expenseList.setCellFactory(param -> new TextFieldListCell<>(sc));
        participantCombobox.setConverter(su);

        expenseComboBox.setConverter(su);
        ImageView imageView = new ImageView(getClass()
                .getResource("/client/images/EditPencilIcon.png")
                .toExternalForm());
        imageView.setFitWidth(17);
        imageView.setFitHeight(17);
        editTitle.setGraphic(imageView);
        ImageView imageView2 = new ImageView(getClass()
                .getResource("/client/images/EditPencilIcon.png")
                .toExternalForm());
        imageView2.setFitWidth(17);
        imageView2.setFitHeight(17);
        editDescription.setGraphic(imageView2);
        service.setSession();
        service.getServer().regDeleteExpenses(expense -> {
            Platform.runLater(() -> refresh());
        });
        service.getServer().regEditExpenses(editOp -> {
            Platform.runLater(() -> refresh());
        });
        service.getServer().registerForSocketMessages("/updates/events", Event.class, e -> {
            Platform.runLater(() -> {if(e.getEventCode() == event.getEventCode())
                    refresh(); });
        });

    }

    /**
     * Update label text
     *
     * @param event
     */
    public void updateLabelText(Event event) {
        if (event != null || !event.getTitle().isEmpty()) {
            titleLabel.setText(event.getTitle());
            eventTitle.setText(event.getTitle());
            eventCode.setText(Long.toString(event.getEventCode()));
        }
    }

    /**
     * Update label text
     *
     * @param event
     */
    public void updateDesc(Event event) {
        if (event != null || !event.getTitle().isEmpty()) {
            descriptionLabel.setText(event.getDescription());
            description.setText(event.getDescription());
        }
    }

    /**
     * Set event
     *
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }


    /**
     * goes back to the main page
     *
     * @param actionEvent when the button is clicked
     */
    public void back(ActionEvent actionEvent) {
        service.getMainCtrl().showStartPage();
    }

    /**
     * add or edit expense method
     */
    public void addOrEditExpense() {
        if (this.event.getParticipants().isEmpty()) {
            return;
        }
        if (selectedExpense == null) {
            service.getMainCtrl().showAddOrEditExpense(this.event, new Expense());
        }
        service.getMainCtrl().showAddOrEditExpense(this.event, selectedExpense);
    }


    /**
     * adds a new expense to database and event
     */
    public void addExpense() {
        if (this.event.getParticipants().size() < 2) {
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle(service.getString("invalid"));
            confirmation.setHeaderText(service.getString("not-enough-participants"));
            confirmation.setContentText(service
                    .getString("you-must-have-2-participants-to-add-an-expense"));
            confirmation.showAndWait();
        } else {
            selectedExpense = null;
            service.getMainCtrl().showAddOrEditExpense(event, selectedExpense);
        }
    }

    /**
     * On action button for the error pane
     */
    public void noParticipantErr() {
        noParticipantPane.setVisible(false);
    }


    /**
     * edits a selected participant's information
     *
     * @param actionEvent when the button is clicked
     */
    public void editExpense(ActionEvent actionEvent) {
        if (selectedExpense == null) {
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle(service.getString("invalid"));
            confirmation.setHeaderText(service.getString("no-selected-expense"));
            confirmation.setContentText(service.getString("please-select-an-expense-to-edit"));
            confirmation.showAndWait();
        } else {
            service.getMainCtrl().showAddOrEditExpense(event, selectedExpense);
        }
    }

    /**
     * adds a new expense to database and event
     */
    public void addParticipant() {
        selectedParticipant = null;
        service.getMainCtrl().showAddOrEditParticipants(selectedParticipant, event);
    }

    /**
     * edits a selected expense's information
     *
     * @param actionEvent when the button is clicked
     */
    public void editParticipant(ActionEvent actionEvent) {
        if (selectedParticipant == null) {
            //mainCtrl.showAddOrEditParticipants(new User(), event);
        }
        service.getMainCtrl().showAddOrEditParticipants(selectedParticipant, event);
    }

    /**
     * sends invitations
     */
    public void sendInvitations() {
        service.getMainCtrl().showSendInvitations(event);
    }


    /**
     * show statistics
     */
    public void showStatistics() {
        event = service.getEventById(event.getId());
        service.getMainCtrl().showStatistics(event);
    }

    /**
     * adds expnse tag
     */
    public void addExpenseTag() {
        service.getMainCtrl().showExpenseTags(event);
    }

    /**
     * This method is for usability. Checks the pressed key
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {

        switch (e.getCode()) {
            case ENTER:
                if (participantCombobox.isFocused()) {
                    participantCombobox.show();
                    break;
                }
                if (expenseComboBox.isFocused()) {
                    expenseComboBox.show();
                    break;
                }
                break;
            case ESCAPE:
                break;
            default:
                break;
        }

    }

    /**
     * listens for key combinations
     *
     * @param e
     */
    public void keyCombinationPressed(KeyEvent e) {
        KeyCombination addExpenseTagShortcut =
                new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
        KeyCombination addParticipantTagShortcut =
                new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);
        KeyCombination addExpenseShortcut =
                new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);

        if (addExpenseTagShortcut.match(e)) {
            System.out.println(service
                    .getString("combination-pressed") + ": "
                    + addExpenseTagShortcut);
            addExpenseTag();
        }
        if (addParticipantTagShortcut.match(e)) {
            System.out.println(service
                    .getString("combination-pressed") + ": "
                    + addParticipantTagShortcut);
            addParticipant();
        }
        if (addExpenseShortcut.match(e)) {
            System.out.println(service
                    .getString("combination-pressed") + ": "
                    + addExpenseTagShortcut);
            addExpense();
        }
    }

    /**
     * refreshes the data as the page is opened again
     *
     * @param event of the page
     */
    public void setData(Event event) {
        this.event = service.getEventById(event.getId());
        updateDesc(this.event);
        updateLabelText(this.event);
        service.registerForEventUpdates(expense -> {
            this.event = service.getEventById(event.getId());
            /*if (!this.event.getExpenses().contains(expense))
                this.event.addExpense(expense);
            service.updateEvent(this.event);*/
            expenseList.getItems().setAll(this.event.getExpenses());
        });
        expenseList.getItems().setAll(this.event.getExpenses());
        if (this.event.getParticipants() != null && !this.event.getParticipants().isEmpty()) {
            String label = "";

            for (int i = 0; i < this.event.getParticipants().size() - 1; i++) {
                label += this.event.getParticipants().get(i).getUsername() + ", ";
            }
            label += this.event.getParticipants().get(
                    this.event.getParticipants().size() - 1).getUsername();
            participantsLabel.setText(label);
        } else {
            participantsLabel.setText(service.getString("no-available-participants"));
        }
        participantCombobox.getItems().setAll(this.event.getParticipants());
        expenseComboBox.getItems().setAll(this.event.getParticipants());

    }

    /**
     * selects a specific participant from the combobox
     *
     * @param actionEvent selecting of the participant
     */
    public void selectParticipant(ActionEvent actionEvent) {
        selectedParticipant = participantCombobox.getValue();
    }

    /**
     * selects a specific participant from the combobox
     *
     * @param actionEvent selecting of the participant
     */
    public void selectExpense(ActionEvent actionEvent) {
        selectedExpenseParticipant = expenseComboBox.getValue();
    }

    /**
     * deletes the selected participant from the event
     *
     * @param actionEvent when the button is clicked
     */
    public void deleteParticipant(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(service.getString("confirmation-dialog"));
        alert.setContentText(service.getString("are-you-sure-you-want-to-delete-this-participant"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            User temp = selectedParticipant;
            participantCombobox.getItems().remove(temp);
            expenseComboBox.getItems().remove(temp);
            List<User> oldParticipants = event.getParticipants();
            oldParticipants = oldParticipants.stream().filter(q -> !q.equals(temp)).toList();
            event.setParticipants(oldParticipants);
            //service.updateEvent(event);
            service.send("/app/event", event);
            setData(event);
        }

    }


    /**
     * Method to enable editing of the event title
     */
    public void enableEditingTitle() {
        titleLabel.setVisible(false);
        eventTitle.setVisible(true);
        eventTitle.setEditable(true);
    }

    /**
     * Method to disable editing of the event title
     */
    public void disableEditingTitle() {
        titleLabel.setVisible(true);
        eventTitle.setVisible(false);
        eventTitle.setEditable(false);
    }

    /**
     * allows to edit the title of an event
     *
     * @param actionEvent when the button is clicked
     */
    public void editTitle(ActionEvent actionEvent) {
        if (eventTitle.isEditable()) {
            if (eventTitle.getText().isEmpty()) {
                ((Label) noParticipantPane.getChildren().get(0))
                        .setText(service.getString("a-title-is-required-for-an-event"));
                noParticipantPane.setVisible(true);
                disableEditingTitle();
                return;
            }
            String newTitle = eventTitle.getText();
            disableEditingTitle();
            titleLabel.setText(newTitle);
            event.setTitle(newTitle);
            //service.updateEvent(event);
            service.send("/app/event", event);
        }
        else {
            enableEditingTitle();
        }
    }

    /**
     * Method to enable editing of the event description
     */
    public void enableEditingDesc() {
        descriptionLabel.setVisible(false);
        description.setVisible(true);
        description.setEditable(true);
    }

    /**
     * Method to disable editing of the event description
     */
    public void disableEditingDesc() {
        descriptionLabel.setVisible(true);
        description.setVisible(false);
        description.setEditable(false);
    }


    /**
     * allows to edit the description of an event
     *
     * @param actionEvent when the button is clicked
     */
    public void editDescription(ActionEvent actionEvent) {
        if (description.isEditable()) {
            String newDesc = description.getText();
            descriptionLabel.setText(newDesc);
            disableEditingDesc();
            event.setDescription(newDesc);
            //service.updateEvent(event);
            service.getServer().send("/app/event", event);
        }
        else {
            enableEditingDesc();
        }
    }

    /**
     * lists only expenses that includes the selected user as a paying participant
     *
     * @param actionEvent when the button is clicked
     */
    public void includingParticipantList(ActionEvent actionEvent) {
        List<Expense> newList = event.getExpenses().stream()
                .filter(q -> q.getPayingParticipants().contains(selectedExpenseParticipant))
                .toList();
        expenseList.getItems().setAll(newList);
    }

    /**
     * lists only expenses that includes the selected user is the payer of
     *
     * @param actionEvent when the button is clicked
     */
    public void paidByParticipantList(ActionEvent actionEvent) {
        List<Expense> newList = event.getExpenses().stream()
                .filter(q -> q.getPayer().equals(selectedExpenseParticipant)).toList();
        expenseList.getItems().setAll(newList);
    }

    /**
     * lists all the expenses of an event
     *
     * @param actionEvent when the button is clicked
     */
    public void allExpenses(ActionEvent actionEvent) {
        expenseList.getItems().setAll(event.getExpenses());
    }

    /**
     * selects an expense to be edited
     *
     * @param mouseEvent when the expense is clicked on from the list
     */
    public void selectExpenseList(MouseEvent mouseEvent) {
        selectedExpense = expenseList.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() > 1) {
            service.getMainCtrl().showExpenseInfo(event, selectedExpense);
        }
    }

    /**
     * refreshes the page
     */
    public void refresh() {
        Event e = service.getEventById(event.getId());
        setEvent(e);
        setData(e);
    }


    /**
     * opens the settle debts page
     *
     * @param actionEvent when the button is clicked
     */
    public void settleDebts(ActionEvent actionEvent) {
        service.getMainCtrl().showSettleDebts(event);
    }

    /**
     * expense info is shown when the enter button is pressed
     *
     * @param keyEvent when the button is pressed
     */
    public void enterExpense(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && selectedExpense != null) {
            service.getMainCtrl().showExpenseInfo(event, selectedExpense);
        }
    }

}

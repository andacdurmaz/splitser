package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javax.inject.Inject;
import java.util.List;


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
    @FXML
    private Button noParticipantErrButton;
    @FXML
    private Button editTitle;
    @FXML
    private Button editDescription;
    @FXML
    private Button invitation;
    @FXML
    private Button expenseTag;
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * Constructor
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public EventInfoCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * makes the combobox's display names of the users
     */
    public void initialize() {
        noParticipantPane.setVisible(false);
        disableEditingDesc();
        disableEditingTitle();
        expenseList.setCellFactory(param -> new TextFieldListCell<>(new StringConverter<Expense>() {
            @Override
            public String toString(Expense expense) {
                return "(" + expense.getDate() + ") " + expense.getPayer().getUsername() +
                        " paid " + expense.getAmount() + " for " + expense.getName();
            }
            @Override
            public Expense fromString(String string) {
                return null;
            }
        }));
        participantCombobox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getUsername(); // Display the username
            }
            @Override
            public User fromString(String string) {
                return null;
            }
        });
        expenseComboBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getUsername(); // Display the username
            }
            @Override
            public User fromString(String string) {
                return null;
            }
        });
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
        }
    }

    /**
     * Update label text
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
     * @param actionEvent when the button is clicked
     */
    public void back(ActionEvent actionEvent) {
        mainCtrl.showStartPage();
    }

    /**
     * add or edit expense method
     */
    public void addOrEditExpense() {
        if (this.event.getParticipants().isEmpty()) {
            return;
        }
        if (selectedExpense == null) {
            mainCtrl.showAddOrEditExpense(this.event, new Expense());
        }
        mainCtrl.showAddOrEditExpense(this.event, selectedExpense);
    }



    /**
     * adds a new expense to database and event
     * @param actionEvent when the button is clicked
     */
    public void addExpense(ActionEvent actionEvent){
        if(this.event.getParticipants().isEmpty() || this.event.getExpenseTags().isEmpty()) {
            noParticipantPane.setVisible(true);
            noParticipantErrButton.requestFocus();
        } else {
            selectedExpense = null;
            mainCtrl.showAddOrEditExpense(event, selectedExpense);
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
     * @param actionEvent when the button is clicked
     */
    public void editExpense(ActionEvent actionEvent){
        if (selectedExpense == null) {
            //mainCtrl.showAddOrEditParticipants(new User(), event);
        }
        mainCtrl.showAddOrEditExpense(event, selectedExpense);
    }
    /**
     * adds a new expense to database and event
     * @param actionEvent when the button is clicked
     */
    public void addParticipant(ActionEvent actionEvent){
        selectedParticipant = null;
        mainCtrl.showAddOrEditParticipants(selectedParticipant, event);
    }

    /**
     * edits a selected expense's information
     * @param actionEvent when the button is clicked
     */
    public void editParticipant(ActionEvent actionEvent){
        if (selectedParticipant == null) {
            //mainCtrl.showAddOrEditParticipants(new User(), event);
        }
        mainCtrl.showAddOrEditParticipants(selectedParticipant, event);
    }

    /**
     *  sends invitations
     */
    public void sendInvitations() {
        mainCtrl.showSendInvitations(event);
    }


    /**
     * adds expnse tag
     */
    public void addExpenseTag() {
        mainCtrl.showExpenseTags(event);
    }

    /**
     * This method is for usability. Checks the pressed key
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:

                break;
            case ESCAPE:

                break;
            default:
                break;
        }
    }

    /**
     * refreshes the data as the page is opened again
     * @param event of the page
     */
    public void setData(Event event) {
        updateDesc(event);
        updateLabelText(event);
        expenseList.getItems().setAll(event.getExpenses());
        if (event.getParticipants() != null && !event.getParticipants().isEmpty()) {
            String label = "";

            for (int i = 0; i <event.getParticipants().size() - 1; i++) {
                label += event.getParticipants().get(i).getUsername() + ", ";
            }
            label += event.getParticipants().get(event.getParticipants().size() - 1).getUsername();
            participantsLabel.setText(label);
        }
        else {
            participantsLabel.setText("No available participants.");
        }
        participantCombobox.getItems().setAll(event.getParticipants());
        expenseComboBox.getItems().setAll(event.getParticipants());

    }

    /**
     * selects a specific participant from the combobox
     * @param actionEvent selecting of the participant
     */
    public void selectParticipant(ActionEvent actionEvent) {
        selectedParticipant = participantCombobox.getValue();
    }

    /**
     * selects a specific participant from the combobox
     * @param actionEvent selecting of the participant
     */
    public void selectExpense(ActionEvent actionEvent) {
        selectedExpenseParticipant = expenseComboBox.getValue();
    }

    /**
     * deletes the selected participant from the event
     * @param actionEvent when the button is clicked
     */
    public void deleteParticipant(ActionEvent actionEvent) {
        User temp = selectedParticipant;
        participantCombobox.getItems().remove(temp);
        expenseComboBox.getItems().remove(temp);
        List<User> oldParticipants = event.getParticipants();
        oldParticipants = oldParticipants.stream().filter(q -> !q.equals(temp)).toList();
        event.setParticipants(oldParticipants);
        server.updateEvent(event);
        setData(event);
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
     * @param actionEvent when the button is clicked
     */
    public void editTitle(ActionEvent actionEvent) {
        if (eventTitle.isEditable()) {
            String newTitle = eventTitle.getText();
            disableEditingTitle();
            titleLabel.setText(newTitle);
            event.setTitle(newTitle);
            server.updateEvent(event);
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
     * @param actionEvent when the button is clicked
     */
    public void editDescription(ActionEvent actionEvent) {
        if (description.isEditable()) {
            String newDesc = description.getText();
            descriptionLabel.setText(newDesc);
            disableEditingDesc();
            event.setDescription(newDesc);
            server.updateEvent(event);
        }
        else {
            enableEditingDesc();
        }
    }

    /**
     * lists only expenses that includes the selected user as a paying participant
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
     * @param actionEvent when the button is clicked
     */
    public void paidByParticipantList(ActionEvent actionEvent) {
        List<Expense> newList = event.getExpenses().stream()
                .filter(q -> q.getPayer().equals(selectedExpenseParticipant)).toList();
        expenseList.getItems().setAll(newList);
    }

    /**
     * lists all the expenses of an event
     * @param actionEvent when the button is clicked
     */
    public void allExpenses(ActionEvent actionEvent) {
        expenseList.getItems().setAll(event.getExpenses());
    }

    /**
     * selects an expense to be edited
     * @param mouseEvent when the expense is clicked on from the list
     */
    public void selectExpenseList(MouseEvent mouseEvent) {
        selectedExpense = expenseList.getSelectionModel().getSelectedItem();
    }
}

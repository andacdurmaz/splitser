package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.User;
import javafx.event.ActionEvent;
import commons.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import javax.inject.Inject;
import java.util.List;


public class EventInfoCtrl {
    private Event event;
    private Expense selectedExpense;

    private User selectedParticipant;
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
        disableEditingDesc();
        disableEditingTitle();
        participantCombobox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getUsername(); // Display the user name
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });

        expenseComboBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getUsername(); // Display the user name
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });
    }

    /**
     * Update label text
     *
     * @param event
     */
    public void updateLabelText(Event event) {
        if (event != null || event.getTitle().length() != 0) {
            titleLabel.setText(event.getTitle());
            eventTitle.setText(event.getTitle());
        }
    }

    /**
     * Update label text
     * @param event
     */
    public void updateDesc(Event event) {
        if (event != null || event.getTitle().length() != 0) {
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
        if (selectedExpense == null) {
            mainCtrl.showAddOrEditExpense(new Expense());
        }
        mainCtrl.showAddOrEditExpense(selectedExpense);
    }


    /**
     * adds a new participant to database and event
     * @param actionEvent when the button is clicked
     */
    public void addParticipant(ActionEvent actionEvent){
        selectedParticipant = null;
        mainCtrl.showAddOrEditParticipants(selectedParticipant, event);
    }

    /**
     * edits a selected participant's information
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
        if (event.getParticipants() != null && event.getParticipants().size() !=0) {
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
}

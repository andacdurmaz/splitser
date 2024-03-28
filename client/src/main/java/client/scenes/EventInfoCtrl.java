package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.User;
import javafx.event.ActionEvent;
import commons.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class EventInfoCtrl {
    private Event event;
    private Expense selectedExpense;

    private User selectedParticipant;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label eventTitle;
    @FXML
    private Label description;
    @FXML
    private Label participantsLabel;
    @FXML
    private Label expensesLabel;
    @FXML
    private ComboBox<User> expenseComboBox;
    @FXML
    private ComboBox<User> participantCombobox;
    private List<User> participants = new ArrayList<>();

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
     * Update label text
     *
     * @param event
     */
    public void updateLabelText(Event event) {
        if (event != null || event.getTitle().length() != 0)
            eventTitle.setText(event.getTitle());
    }

    /**
     * Update label text
     * @param event
     */
    public void updateDesc(Event event) {
        if (event != null || event.getTitle().length() != 0)
            description.setText(event.getDescription());
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
     * adds participant
     */
    public void addOrEditParticipant(){
        if (selectedParticipant == null) {
            mainCtrl.showAddOrEditParticipants(new User(), event);
        }
        mainCtrl.showAddOrEditParticipants(selectedParticipant, event);

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
}

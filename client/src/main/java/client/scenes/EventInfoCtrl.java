package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class EventInfoCtrl {
    private Event event;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label eventTitle;
    @FXML
    private Label participantsLabel;
    @FXML
    private Label expensesLabel;
    @FXML
    private ComboBox<String> expenseComboBox;
    private List<User> participants = new ArrayList<>();

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * Constructor
     * @param server
     * @param mainCtrl
     */
    @Inject
    public EventInfoCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * initialize method
     * @param event
     */
    public void initialize(Event event) {
        this.event = event;
        eventTitle.setText(event.getTitle());
    }

    /**
     * Update label text
     * @param event
     */
    public void updateLabelText(Event event) {
        if (event != null || event.getTitle().length() != 0)
            eventTitle.setText(event.getTitle());
    }

    /**
     * Set event
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    public void addParticipant(ActionEvent actionEvent) {
        User user = new User("demo", "demo@gmail.com");
        participants.add(user);
        expenseComboBox.getItems().add(user.getUsername());
    }

    public void back(ActionEvent actionEvent) {
        mainCtrl.showStartPage();
    }

}

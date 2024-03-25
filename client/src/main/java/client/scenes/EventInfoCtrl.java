package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;


public class EventInfoCtrl {
    private Event event;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label participantsLabel;
    @FXML
    private Label expensesLabel;


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public EventInfoCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Initialize method, shouldn't be empty
     */
    public void initialize(Event event) {
        this.event = event;
        titleLabel.setText(event.getTitle());
    }

    public void updateLabelText(Event event) {
        titleLabel.setText(event.getTitle());
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}

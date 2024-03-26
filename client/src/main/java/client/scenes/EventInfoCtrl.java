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
    private Label eventTitle;
    @FXML
    private Label participantsLabel;
    @FXML
    private Label expensesLabel;
    @FXML
    private Label description;


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
    public void updateEventTitle(Event event) {
        eventTitle.setText(event.getTitle());
    }

    /**
     * Set event
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }



    /**
     * Update description
     * @param event
     */
    public void updateDescription (Event event){
        description.setText(event.getTitle());
    }


}

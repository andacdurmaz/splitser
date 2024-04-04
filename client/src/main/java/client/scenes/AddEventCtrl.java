package client.scenes;

import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Event;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

import java.util.List;
import java.util.Random;

public class AddEventCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    /**
     * Inject method
     *
     * @param server   server
     * @param mainCtrl mainCtrl
     */
    @Inject
    public AddEventCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * Cancel method, returns from adding an event
     */
    public void cancel() {
        clearFields();
        mainCtrl.showStartPage();
    }

    /**
     * Adds event
     */
    public void ok() {
        Event newEvent = getEvent();
        try {
            Event tmp = server.addEvent(newEvent);
            newEvent.setId(tmp.getId());
            mainCtrl.writeEventToConfigFile(newEvent);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        clearFields();
        mainCtrl.showEventInfo(newEvent);
    }

    /**
     * getEvent method
     *
     * @return specified event
     */
    private Event getEvent() {
        List<Long> eventCodes = server.getEvents().stream().map(q -> q.getEventCode()).toList();
        Event event = new Event(title.getText());
        Random random = new Random();
        long eventCode;
        do {
            eventCode = Math.abs(random.nextLong() % 100000000);
        }
        while (eventCodes.contains(eventCode));


        event.setEventCode(eventCode);
        if (description.getText() != null)
            event.setDescription(description.getText());
        return event;
    }

    /**
     * clears fields
     */
    private void clearFields() {
        title.clear();
        description.clear();
    }

    /**
     * This method is for usability. Checks the pressed key
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }
}

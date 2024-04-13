package client.scenes;

import client.services.AddEventService;
import com.google.inject.Inject;
import commons.Event;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import java.util.List;
import java.util.Random;

public class AddEventCtrl {

    private final AddEventService service;

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private AnchorPane error;

    @FXML
    private Button cancel;

    @FXML
    private Button ok;

    /**
     * Inject method
     *
     * @param service service
     */
    @Inject
    public AddEventCtrl(AddEventService service) {
        this.service = service;

    }

    /**
     * Cancel method, returns from adding an event
     */
    public void cancel() {
        clearFields();
        service.showStartPage();
    }

    /**
     * Adds event
     */
    public void ok() {
        Event newEvent = getEvent();
        if (newEvent == null){
            return;
        }
        try {
            Event tmp = service.addEvent(newEvent);
            newEvent.setId(tmp.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Event Created Successfully!");
            alert.setHeaderText(null);
            alert.setContentText("Successfully created new Event: " + newEvent.getTitle());
            alert.showAndWait();
            service.writeToConfig(newEvent);
        } catch (WebApplicationException e) {
            var alertError = new Alert(Alert.AlertType.ERROR);
            alertError.initModality(Modality.APPLICATION_MODAL);
            alertError.setContentText(e.getMessage());
            alertError.showAndWait();
            return;
        }
        clearFields();
        service.showEventInfo(newEvent);
    }

    /**
     * getEvent method
     *
     * @return specified event
     */
    private Event getEvent() {
        List<Long> eventCodes = service.getEvents().stream().map(q -> q.getEventCode()).toList();
        if (title.getText().isEmpty()) {
            errorMessage();
            return null;
        }

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
     * sends a popup for the title requirement
     */
    private void errorMessage() {
        var alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Missing Event Title!");
        alertError.setHeaderText(null);
        alertError.setContentText("A title is required for the event!");
        alertError.showAndWait();
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

    /**
     * closes the popup
     * @param actionEvent when the button is clicked
     */
    public void goBack(ActionEvent actionEvent) {
        error.setVisible(false);
        error.getChildren().get(0).setVisible(false);
        error.getChildren().get(1).setVisible(false);
        ok.setDisable(false);
        cancel.setDisable(false);

    }
}

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

public class AddEventCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private TextField title;

    @FXML
    private TextField numberOfParticipants;

    @FXML
    private TextField eventCode;
    @FXML
    private TextField description;
    /**
     * Inject method
     * @param server server
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
        mainCtrl.showOverview();
    }

    /**
     * Adds event
     */
    public void ok() {
        try {
            server.addEvent(getEvent());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        mainCtrl.showOverview();
    }

    /**
     * getEvent method
     * @return specified event
     */
    private Event getEvent() {
        return new Event(
                title.getText(),
                Integer.parseInt(numberOfParticipants.getText()),
                Integer.parseInt(eventCode.getText()),
                description.getText());
    }

    /**
     * clears fields
     */
    private void clearFields() {
        title.clear();
        numberOfParticipants.clear();
        eventCode.clear();
        description.clear();
    }

    /**
     * This method is for usability. Checks the pressed key
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

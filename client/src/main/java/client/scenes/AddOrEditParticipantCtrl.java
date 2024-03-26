package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import commons.User;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;

import javax.inject.Inject;
import javafx.scene.input.KeyEvent;

public class AddOrEditParticipantCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private final Event event;
    private  User user;

    @FXML
    private Button ok;

    /**
     * Constructor
     * @param server   serverUtils
     *
     * @param mainCtrl mainCtrl
     * @param event
     */
    @Inject
    public AddOrEditParticipantCtrl(ServerUtils server, MainCtrl mainCtrl, Event event) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.event = event;
    }

    /**
     * Set expense
     * @param user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Cancel add/edit
     */
    public void cancel() {
        clearFields();
        mainCtrl.showEventInfo(event);
    }

    /**
     * Confirm add/edit
     */
    public void ok() {

        try {
            server.addUser(getUser());
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        clearFields();
        mainCtrl.showEventInfo(event);
    }

    /**
     * Get expense
     *
     * @return expense
     */
    private User getUser() {
        var p = new User();
        return p;
    }

    /**
     * Clears fields
     */
    private void clearFields() {

    }

    /**
     * @param e key event
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
            default:
                break;
        }
    }

}


package client.scenes;

import client.utils.ServerUtils;
import commons.*;
import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import javax.inject.Inject;
import javafx.scene.input.KeyEvent;
import java.util.List;

public class AddOrEditParticipantCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Event event;
    private  User user;

    @FXML
    private TextField name;

    @FXML
    private TextField email;
    @FXML
    private TextField iban;

    @FXML
    private TextField bic;


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
     * Set user
     * @param user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set event
     * @param event to set
     */
    public void setEvent(Event event) {
        this.event = event;
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
        if (user == null) {
            noSelectedParticipant();
        }
        else {
            selectedParticipant();
        }

    }

    /**
     * updates a selected participant
     */
    private void selectedParticipant() {
        try {
            List<User> participants = event.getParticipants();
            participants.remove(user);
            user.setUsername(name.getText());
            user.setEmail(email.getText());
            user.setIban(iban.getText());
            user.setBic(bic.getText());
            server.updateUser(user);
            participants.add(user);
            event.setParticipants(participants);
            server.updateEvent(event);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        } catch (EmailFormatException e) {
            throw new RuntimeException(e);
        } catch (IBANFormatException e) {
            throw new RuntimeException(e);
        } catch (BICFormatException e) {
            throw new RuntimeException(e);
        }
        clearFields();
        mainCtrl.showEventInfo(event);
    }

    /**
     * creates a new user and adds it to the database
     */
    private void noSelectedParticipant() {
        user = getUser();
        try {
            User temp = server.addUser(getUser());
            user.setUserID(temp.getUserID());
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        List<User> participants = event.getParticipants();
        if (!participants.contains(user))
            participants.add(user);
        event.setParticipants(participants);
        server.updateEvent(event);
        clearFields();
        mainCtrl.showEventInfo(event);
    }

    /**
     * Get expense
     *
     * @return expense
     */
    private User getUser() {
        var u = new User(name.getText(), email.getText(), iban.getText(), bic.getText());
        return u;
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


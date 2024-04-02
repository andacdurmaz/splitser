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

import java.util.ArrayList;
import java.util.List;

public class AddOrEditParticipantCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Event event;
    private User user;

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
    public void ok() throws EmailFormatException, IBANFormatException, BICFormatException {
        if (user == null) {
            noSelectedParticipant();
        }
        else {
            selectedParticipant();
        }

    }

    /**
     * if edit option is used, the fields contain info from old user
     * @param user the edited user
      */
    public void editFields(User user) {
        if (user == null)
            clearFields();
        else {
            name.setText(user.getUsername());
            email.setText(user.getEmail());
            iban.setText(user.getIban());
            bic.setText(user.getBic());
        }

    }
    /**
     * updates a selected participant
     */
    private void selectedParticipant() {
        try {
            List<User> participants = new ArrayList<>(event.getParticipants());
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
        mainCtrl.showEventInfo(event);
    }

    /**
     * creates a new user and adds it to the database
     */
    private void noSelectedParticipant() throws EmailFormatException,
            IBANFormatException, BICFormatException {
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
        List<User> participants = new ArrayList<>(event.getParticipants());
        if (!participants.contains(user))
            participants.add(user);
        event.setParticipants(participants);
        server.updateEvent(event);
        mainCtrl.showEventInfo(event);
    }

    /**
     * Get expense
     *
     * @return expense
     */
    private User getUser() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User u = new User();
        u.setUsername(name.getText());
        u.setEmail(email.getText());
        u.setIban(iban.getText());
        u.setBic(bic.getText());
        return u;
    }

    /**
     * Clears fields
     */
    private void clearFields() {
        name.setText("");
        email.setText("");
        iban.setText("");
        bic.setText("");
    }

    /**
     * @param e key event
     */
    public void keyPressed(KeyEvent e) throws EmailFormatException,
            IBANFormatException, BICFormatException {
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


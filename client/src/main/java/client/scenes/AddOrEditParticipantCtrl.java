package client.scenes;

import client.utils.ServerUtils;
import commons.*;
import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private AnchorPane wrongEmail;
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
        name.requestFocus();
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
        List<User> participants = new ArrayList<>(event.getParticipants());
        participants.remove(user);
        try {
            user.setUsername(name.getText());
            user.setEmail(email.getText());
            user.setIban(iban.getText());
            user.setBic(bic.getText());
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        } catch (EmailFormatException | BICFormatException e) {
            errorMessage();
            return;
        } catch (IBANFormatException e) {
            errorMessage();
            return;
        }
        server.updateUser(user);
        participants.add(user);
        event.setParticipants(participants);
        server.updateEvent(event);
        mainCtrl.showEventInfo(event);
    }

    /**
     * creates a new user and adds it to the database
     */
    private void noSelectedParticipant() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        user = getUser();
        if (user == null)
            return;
        try {
            User temp = server.addUser(getUser());
            user.setUserID(temp.getUserID());
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }catch (EmailFormatException e) {
            errorMessage();
        } catch (IBANFormatException e) {
            errorMessage();
        } catch (BICFormatException e) {
            errorMessage();
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
        try {
            u.setUsername(name.getText());
            u.setEmail(email.getText());
            u.setIban(iban.getText());
            u.setBic(bic.getText());
            return u;
        }
        catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }catch (EmailFormatException e) {
            ((Label) wrongEmail.getChildren().get(0))
                    .setText("   Invalid e-mail.\nPlease try again.");
            errorMessage();
        } catch (IBANFormatException e) {
            ((Label) wrongEmail.getChildren().get(0))
                    .setText("  Invalid IBAN.\nPlease try again.");
            errorMessage();
        } catch (BICFormatException e) {
            ((Label) wrongEmail.getChildren().get(0))
                    .setText("  Invalid BIC.\nPlease try again.");
            errorMessage();
        }
        return null;
    }

    /**
     * displays error message if the e-mail, IBAN, or BIC is in wrong format
     */
    private void errorMessage() {

        wrongEmail.toFront();
        wrongEmail.setVisible(true);
    }

    /**
     * Clears fields
     */
    private void clearFields() {
        wrongEmail.setVisible(false);
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

    /**
     * closes the pop-up
     * @param actionEvent
     */
    public void goBack(ActionEvent actionEvent) {
        wrongEmail.setVisible(false);
    }
}


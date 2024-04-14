package client.scenes;

import commons.Event;
import commons.User;
import client.services.AddOrEditParticipantService;
import fr.marcwrobel.jbanking.bic.Bic;
import fr.marcwrobel.jbanking.iban.Iban;
import jakarta.ws.rs.WebApplicationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import org.apache.commons.validator.routines.EmailValidator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;



public class AddOrEditParticipantCtrl {
    private final AddOrEditParticipantService service;

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
    @FXML
    private Button errorButton;

    /**
     * Constructor
     * @param service service
     * @param event event
     */
    @Inject
    public AddOrEditParticipantCtrl(AddOrEditParticipantService service, Event event) {
        this.service = service;
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
        service.showEventInfo(event);
    }

    /**
     * Confirm add/edit
     */
    public void ok()  {
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
            wrongEmail.getChildren().get(1).setVisible(false);
        }

    }
    /**
     * updates a selected participant
     */
    private void selectedParticipant() {
        List<User> participants = new ArrayList<>(event.getParticipants());
        User old = user;
        participants.remove(user);
        try {
            if (formatCheck(participants, old)) return;
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        service.updateUser(user);
        participants.add(user);
        event.setParticipants(participants);
        service.updateAndShow(event);
    }

    /**
     * shows a pop-up if the e-mail, bic, or iban has incorrect format
     * @param participants the list of the participants of the event
     * @param old the edited participant
     * @return true if there is a mistake
     */
    private boolean formatCheck(List<User> participants, User old) {
        user.setUsername(name.getText());
        if (email.getText() == null || !EmailValidator.getInstance().isValid(email.getText())) {

            ((Label) wrongEmail.getChildren().get(0))
                    .setText(service.getString("invalid-email-please-try-again"));
            errorMessage();
            participants.add(old);
            user = old;
            return true;
        } else
            user.setEmail(email.getText());
        if(iban.getText() != null && !iban.getText().isEmpty()){
            if (!Iban.isValid(iban.getText())) {
                ((Label) wrongEmail.getChildren().get(0))
                        .setText(service.getString("invalid-iban-please-try-again"));
                errorMessage();
                participants.add(old);
                user = old;
                return true;
            } else
                user.setIban(iban.getText());
        }
        else {
            user.setIban(null);
        }
        if (bic.getText() != null && !bic.getText().isEmpty()) {
            if (!Bic.isValid(bic.getText())) {
                ((Label) wrongEmail.getChildren().get(0))
                        .setText(service.getString("invalid-bic-please-try-again"));
                errorMessage();
                participants.add(old);
                user = old;
                return true;
            } else
                user.setBic(bic.getText());
        }
        else {
            user.setBic(null);
        }
        return false;
    }

    /**
     * creates a new user and adds it to the database
     */
    private void noSelectedParticipant(){
        user = getUser();
        if (user == null)
            return;
        try {
            User temp = service.addUser(getUser());
            user.setUserID(temp.getUserID());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(service.getString("new-user-added-successfully"));
            alert.setHeaderText(null);
            alert.setContentText(service
                    .getString("successfully-added-new-participant-to-the-event") + ": "
                    + temp.getUsername());
            alert.showAndWait();
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
        service.updateAndShow(event);
    }

    /**
     * Get expense
     *
     * @return expense
     */
    private User getUser() {
        User u = new User();
        try {
            u.setUsername(name.getText());
            if (email.getText() == null || email.getText().length() == 0
                    || !EmailValidator.getInstance().isValid(email.getText())) {
                ((Label) wrongEmail.getChildren().get(0))
                        .setText(service.getString("invalid-email-please-try-again"));
                errorMessage();
                return null;
            } else {
                u.setEmail(email.getText());

            }
            if(iban.getText() != null && !iban.getText().isEmpty()){
                if (!Iban.isValid(iban.getText())) {
                    ((Label) wrongEmail.getChildren().get(0))
                            .setText(service.getString("invalid-iban-please-try-again"));
                    errorMessage();
                    return null;
                } else
                    u.setIban(iban.getText());
            } if (bic.getText() != null && !bic.getText().isEmpty()) {
                if (!Bic.isValid(bic.getText())) {
                    ((Label) wrongEmail.getChildren().get(0))
                            .setText(service.getString("invalid-bic-please-try-again"));
                    errorMessage();
                    return null;
                } else
                    u.setBic(bic.getText());
            }

            return u;
        }
        catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }
    }

    /**
     * displays error message if the e-mail, IBAN, or BIC is in wrong format
     */
    private void errorMessage() {
        wrongEmail.toFront();
        wrongEmail.getChildren().get(1).setVisible(true);
        wrongEmail.setVisible(true);
        errorButton.isFocused();
    }

    /**
     * Clears fields
     */
    private void clearFields() {
        wrongEmail.setVisible(false);
        wrongEmail.getChildren().get(1).setVisible(false);
        name.setText("");
        email.setText("");
        iban.setText("");
        bic.setText("");
    }

    /**
     * @param e key event
     */
    public void keyPressed(KeyEvent e)  {
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
     * @param actionEvent actionevent
     */
    public void goBack(ActionEvent actionEvent) {
        wrongEmail.setVisible(false);
    }
}


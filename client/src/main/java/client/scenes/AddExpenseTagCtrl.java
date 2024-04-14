package client.scenes;

import client.services.AddExpenseTagService;
import commons.*;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;

import javax.inject.Inject;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddExpenseTagCtrl {
    private final AddExpenseTagService service;

    private Event event;

    @FXML
    private TextField name;
    @FXML
    private ColorPicker colour;

    @FXML
    private Button ok;

    /**
     * Constructor
     *
     * @param service service
     * @param event   event
     */
    @Inject
    public AddExpenseTagCtrl(AddExpenseTagService service, Event event) {
        this.service = service;
        this.event = event;
    }


    /**
     * Set event
     *
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
    public void ok() {
        Alert alertCon = new Alert(Alert.AlertType.CONFIRMATION);
        alertCon.setTitle(service.getString("confirmation-dialog"));
        alertCon.setContentText(service.getString("are-you-sure-you-want-to-add-this-tag"));
        Optional<ButtonType> result = alertCon.showAndWait();
        if (result.get() == ButtonType.OK) {
            ExpenseTag newExpenseTag = getExpenseTag();
            try {
                ExpenseTag tmp = service.addExpenseTag(newExpenseTag);
                newExpenseTag.setId(tmp.getId());
            } catch (WebApplicationException e) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }
            clearFields();

            List<ExpenseTag> expenseTags = new ArrayList<>(event.getExpenseTags());
            if (!expenseTags.contains(newExpenseTag))
                expenseTags.add(newExpenseTag);
            event.setExpenseTags(expenseTags);

            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle(service.getString("new-expense-tag-added-successfully"));
            confirmation.setHeaderText(null);
            confirmation.setContentText(service.getString("successfully-added-new-expense-tag"));
            Optional<ButtonType> res = confirmation.showAndWait();
            if (res.get() == ButtonType.OK) {
                service.updateAndShow(event);
            }
        }
    }

    private ExpenseTag getExpenseTag() {
        ExpenseTag expenseTag = new ExpenseTag(name.getText(), colour.getValue().toString());
        return expenseTag;
    }


    private void clearFields() {
        name.setText("");
    }

    /**
     * @param e key event
     */
    @FXML
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                if (colour.isFocused()) {
                    showColors();
                    break;
                }
                ok();
                break;
            case ESCAPE:
                cancel();
            default:
                break;
        }
    }

    @FXML
    private void showColors() {
        colour.show();
    }

}


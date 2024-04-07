package client.scenes;

import client.utils.ServerUtils;
import commons.*;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import javax.inject.Inject;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseTagCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Event event;

    @FXML
    private TextField name;
    @FXML
    private ColorPicker colour;

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
    public AddExpenseTagCtrl(ServerUtils server, MainCtrl mainCtrl, Event event) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.event = event;
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
        ExpenseTag newExpenseTag = getExpenseTag();
        try {
            ExpenseTag tmp = server.addExpenseTag(newExpenseTag);
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
        server.updateEvent(event);
        mainCtrl.showEventInfo(event);

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
                if(colour.isFocused()) {
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


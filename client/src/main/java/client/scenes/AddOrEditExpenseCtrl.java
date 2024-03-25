package client.scenes;

import client.utils.ServerUtils;
import commons.Expense;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;

import javax.inject.Inject;
import javafx.scene.input.KeyEvent;

public class AddOrEditExpenseCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @FXML
    private Button ok;

    /**
     * Constructor
     * @param server serverUtils
     * @param mainCtrl mainCtrl
     */
    @Inject
    public AddOrEditExpenseCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Cancel add/edit
     */
    public void cancel() {
        clearFields();
        mainCtrl.showOverview();
    }

    /**
     * Confirm add/edit
     */
    public void ok() {
        try{
            server.addExpense(getExpense());
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
     * Get expense
     * @return expense
     */
    private Expense getExpense() {
        var p = new Expense();
        return p;
    }

    /**
     * Clears fields
     */
    private void clearFields() {

    }

    /**
     * key event listener
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
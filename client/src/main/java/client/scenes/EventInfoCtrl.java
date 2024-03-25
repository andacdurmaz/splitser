package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;


public class EventInfoCtrl {
    private Event event;
    private Expense selectedExpense;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label participantsLabel;
    @FXML
    private Label expensesLabel;


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * Constructor
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public EventInfoCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * initialize method
     *
     * @param event
     */
    public void initialize(Event event) {
        this.event = event;
        titleLabel.setText(event.getTitle());
    }

    /**
     * Update label text
     *
     * @param event
     */
    public void updateLabelText(Event event) {
        titleLabel.setText(event.getTitle());
    }

    /**
     * Set event
     *
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * add or edit expense method
     */
    public void addOrEditExpense() {
        if (selectedExpense == null) {
            mainCtrl.showAddOrEditExpense(new Expense());
        }
        mainCtrl.showAddOrEditExpense(selectedExpense);
    }

    /**
     * This method is for usability. Checks the pressed key
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:

                break;
            case ESCAPE:

                break;
            default:
                break;
        }
    }
}

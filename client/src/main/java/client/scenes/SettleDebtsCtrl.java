package client.scenes;

import client.utils.ServerUtils;
import commons.Debt;
import commons.Event;
import commons.Expense;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SettleDebtsCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private User selectedParticipant;

    @FXML
    private ListView<Debt> debtsListView;

    @FXML
    private Button settleDebtButton;

    @FXML
    private Button backButton;
    @FXML
    private Button allDebts;
    @FXML
    private Button debtsIncludingParticipant;
    @FXML
    private ComboBox<User> participants;

    @FXML
    private Label selectedDebtLabel;

    private List<Debt> debts = new ArrayList<>();
    /**
     * Constructor
     *
     * @param server   serverUtils
     * @param mainCtrl mainCtrl
     */
    @Inject
    public SettleDebtsCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @FXML
    public void initialize() {

    }

    /**
     * Handles events
     */
    @FXML
    private void handleSettleDebt() {
        Debt selectedDebt = debtsListView.getSelectionModel().getSelectedItem();
        if (selectedDebt != null) {
            selectedDebtLabel.setText("Settling debt: " + selectedDebt);
            //extra methods to settle here
        } else {
            selectedDebtLabel.setText("No debt selected.");
        }
    }

    /**
     * back
     */
    @FXML
    private void handleBack() {
        mainCtrl.showEventInfo(this.event);
    }

    public void showAllDebts(ActionEvent actionEvent) {
        debtsListView.getItems().setAll(debts);
    }

    public void showDebtsIncludingParticipant(ActionEvent actionEvent) {
        if (selectedParticipant != null) {
            List<Debt> showedDebts = debts.stream()
                    .filter(q -> q.getPayer().equals(selectedParticipant)).toList();
            debtsListView.getItems().setAll(showedDebts);
        }
        else {
            selectedDebtLabel.setText("No participant selected.");
        }
    }


    public void selectParticipant(ActionEvent actionEvent) {
        selectedParticipant = participants.getValue();
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setData() {
        for (Debt d: server.getDebts()) {
            if (d.getEvent().equals(event)) {
                this.debts.add(d);
            }
        }
        participants.getItems().setAll(event.getParticipants());
        debtsListView.getItems().setAll(debts);
        debtsListView.setCellFactory(listView -> new DebtListCell());
        settleDebtButton.setOnAction(event -> handleSettleDebt());
        backButton.setOnAction(event -> handleBack());
    }
}

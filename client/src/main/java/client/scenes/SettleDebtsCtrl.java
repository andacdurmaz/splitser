package client.scenes;

import client.utils.ServerUtils;
import commons.Debt;
import commons.Event;
import commons.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SettleDebtsCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;

    @FXML
    private ListView<Debt> debtsListView;

    @FXML
    private Button settleDebtButton;

    @FXML
    private Button backButton;

    @FXML
    private Label selectedDebtLabel;
    /**
     * Constructor
     *
     * @param server   serverUtils
     * @param mainCtrl mainCtrl
     * @param event    event of expense
     */
    @Inject
    public SettleDebtsCtrl(ServerUtils server, MainCtrl mainCtrl, Event event) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.event = event;
        initialize();
    }

    @FXML
    private void initialize() {
        List<Debt> temp = new ArrayList<>();
        for(Expense e : this.event.getExpenses()) {
            temp.addAll(e.getDebts());
        }
        debtsListView.getItems().addAll(temp);
        debtsListView.setCellFactory(listView -> new DebtListCell());
        settleDebtButton.setOnAction(event -> handleSettleDebt());
        backButton.setOnAction(event -> handleBack());
    }

    /**
     * Handles events
     */
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
}

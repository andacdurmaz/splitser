package client.scenes;

import client.services.SettleDebtsService;
import commons.Debt;
import commons.Event;
import commons.Expense;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SettleDebtsCtrl {
    private Event event;
    private final SettleDebtsService service;

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
     * @param event    event of expense
     * @param service service
     */
    @Inject
    public SettleDebtsCtrl(Event event, SettleDebtsService service){
        this.event = event;
        this.service = service;
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
            selectedDebtLabel.setText(service.getString("settling-debt") + selectedDebt);
            //extra methods to settle here
        } else {
            selectedDebtLabel.setText(service.getString("no-debt-selected"));
        }
    }

    /**
     * back
     */
    @FXML
    private void handleBack() {
        service.getMainCtrl().showEventInfo(this.event);
    }
}

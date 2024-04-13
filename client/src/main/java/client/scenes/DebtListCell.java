package client.scenes;

import commons.Debt;
import javafx.scene.control.ListCell;

public class DebtListCell extends ListCell<Debt> {
    /**
     * Update debt method for listview
     * @param debt debt to update
     * @param empty is empty
     */
    @Override
    protected void updateItem(Debt debt, boolean empty) {
        super.updateItem(debt, empty);
        if (empty || debt == null) {
            setText(null);
        } else {
            String payeeName = debt.getPayee().getUsername();
            String payerName = debt.getPayer().getUsername();
            Double amount = debt.getAmount();
            setText(payerName + " owes " + payeeName + " " + amount + ".");
        }
    }
}


package client.scenes;

import client.services.SettleDebtsService;
import commons.Debt;
import commons.Event;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.StringConverter;

import javax.inject.Inject;
import java.util.*;
import java.util.List;

public class SettleDebtsCtrl {
    private Event event;
    private final SettleDebtsService service;
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

    private Map<User, Double> participantDebts = new HashMap<>();

    private List<Debt> debts = new ArrayList<>();
    /**
     * Constructor
     *
     * @param event    event of expense
     * @param service service
     */
    @Inject
    public SettleDebtsCtrl(Event event, SettleDebtsService service) {
        this.event = event;
        this.service = service;
    }


    /**
     * determines who must pay how much to each person
     * @param participants mappings for each participant's total debt
     * @return the ids of the payers, payees, and the amount
     */
    public List<String> resolveDebts(Map<User, Double> participants) {
        List<String> paymentInstructions = new ArrayList<>();

        List<User> debtors = new ArrayList<>();
        List<User> creditors = new ArrayList<>();

        for (User u : participants.keySet()) {
            if (participants.get(u) > 0) {
                debtors.add(u);
            } else if (participants.get(u) < 0) {
                creditors.add(u);
            }
        }

        debtors.sort(Comparator.comparingDouble(p -> Math.abs(participants.get(p))));
        creditors.sort(Comparator.comparingDouble(p -> Math.abs(participants.get(p))));

        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            User debtor = debtors.get(debtors.size() - 1);
            User creditor = creditors.get(creditors.size() - 1);

            double amount = Math.min(Math.abs(participants.get(debtor)),
                    Math.abs(participants.get(creditor)));

            String instruction = debtor.getUserID() + " " + creditor.getUserID() + " " + amount;
            paymentInstructions.add(instruction);

            participants.put(debtor, participants.get(debtor) - amount);
            participants.put(creditor, participants.get(creditor)+ amount);

            removeDebts(participants, debtor, debtors, creditor, creditors);
        }

        return paymentInstructions;

    }

    /**
     * removes a debtor or creditor if they have no more debts
     * @param participants the mappings of total debts
     * @param debtor the payer
     * @param debtors the list of the payers
     * @param creditor the payee
     * @param creditors the list of the payees
     */
    private static void removeDebts(Map<User, Double> participants,
                                    User debtor, List<User> debtors,
                                    User creditor, List<User> creditors) {
        if (Math.abs(participants.get(debtor)) < 0.001) {
            debtors.remove(debtor);
        }
        if (Math.abs(participants.get(creditor)) < 0.001) {
            creditors.remove(creditor);
        }
    }


    /**
     * Leads the selected participant to payment instructions
     */
    @FXML
    private void handleSettleDebt() {
        List<String> list = resolveDebts(participantDebts);
        if (selectedParticipant != null) {
            service.send("/app/event", event);
            service.getMainCtrl().showUserDebts(list, event, selectedParticipant);
        }
    }

    /**
     * maps how much each user has to pay in total
     */
    private void settleDebtUser() {
        for (User u: event.getParticipants()) {
            double totalDebt = 0.0;
            List<Debt> payerDebts = service.getServer().getDebts().stream()
                    .filter(q -> q.getPayer().equals(u)).toList();
            for (Debt d: payerDebts) {
                totalDebt += d.getAmount();
            }
            List<Debt> payeeDebts =  service.getServer().getDebts().stream()
                    .filter(q -> q.getPayee().equals(u)).toList();
            for (Debt d: payeeDebts) {
                totalDebt -= d.getAmount();
            }
            participantDebts.put(u, totalDebt);
        }
    }

    /**
     * back
     */
    @FXML
    private void handleBack() {
        service.getMainCtrl().showEventInfo(this.event);
    }

    /**
     * shows all debts of the event
     * @param actionEvent when the button is clicked
     */
    public void showAllDebts(ActionEvent actionEvent) {
        debtsListView.getItems().setAll(debts);
    }

    /**
     * shows the debts that the selected participants is a payer of
     * @param actionEvent when the button is clicked
     */
    public void showDebtsIncludingParticipant(ActionEvent actionEvent) {
        if (selectedParticipant != null) {
            List<Debt> showedDebts = debts.stream()
                    .filter(q -> q.getPayer().equals(selectedParticipant)).toList();
            debtsListView.getItems().setAll(showedDebts);
        }
        else {
            selectedDebtLabel.setText(service.getString("no-participant-selected"));
        }
    }


    /**
     * allows choosing a participant from the combobox
     * @param actionEvent when the combobox is clicked
     */
    public void selectParticipant(ActionEvent actionEvent) {
        selectedParticipant = participants.getValue();
    }

    /**
     * sets the event of the page
     * @param event of the page
     */
    public void setEvent(Event event) {
        this.event = event;
    }


    /**
     * initializes the data on the page
     */
    public void setData() {
        participants.setConverter(new StringConverter<>() {
            @Override
            public String toString(User user) {
                if (user != null)
                    return user.getUsername();
                return null;
            }
            @Override
            public User fromString(String string) {
                return null;
            }
        });

        for (Debt d: service.getServer().getDebts()) {
            if (d.getEvent().equals(event)) {
                this.debts.add(d);
            }
        }
        settleDebtUser();
        List<User> openDebts =  new ArrayList<>();
        for (User u: participantDebts.keySet()) {
            if (participantDebts.get(u) > 0) {
                openDebts.add(u);
            }
        }
        participants.getItems().setAll(openDebts);
        debtsListView.getItems().setAll(debts);
        debtsListView.setCellFactory(listView -> new DebtListCell());
        settleDebtButton.setOnAction(event -> handleSettleDebt());
        backButton.setOnAction(event -> handleBack());
    }

    /**
     * removes a participant from the combobox
     * @param user the removed participant
     */
    public void removeOpenDebt(User user) {
        participants.getItems().remove(user);
    }
}

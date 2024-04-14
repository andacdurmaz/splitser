/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.services.AdminOverviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import commons.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class AdminOverviewCtrl implements Initializable {

    private final AdminOverviewService service;
    private ObservableList<Event> data;
    private String optionTitle;
    private String optionDate;
    private String optionActivity;


    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> colEventId;
    @FXML
    private TableColumn<Event, String> colEventName;
    @FXML
    private TableColumn<Event, String> colEventDescription;
    @FXML
    private ComboBox<String> sortMenu;
    @FXML
    private Button adminAddEventButton;
    @FXML
    private Button back;

    /**
     * Constructor for AdminOverview
     * @param service service
     */
    @Inject
    public AdminOverviewCtrl(AdminOverviewService service) {
        this.service = service;
    }
    /**
     * Initialize method
     *
     * @param location  URL
     *                  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources ResourceBundle
     *                  The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        optionTitle = service.getString("title");
        optionDate = service.getString("creation-date");
        optionActivity = service.getString("last-activity");

        colEventId.setCellValueFactory(q ->
                new SimpleStringProperty(String.valueOf(q.getValue().getId())));
        colEventName.setCellValueFactory(q ->
                new SimpleStringProperty(q.getValue().getTitle()));
        colEventDescription.setCellValueFactory(q ->
                new SimpleStringProperty(q.getValue().getDescription()));
        sortMenu.getItems().addAll(optionTitle, optionDate, optionActivity);
        sortMenu.setOnAction(sortEvent);
        table.setOnMouseClicked(getEvent);
    }

    /**
     * A method which is called when the user clicks on the 'adminAddEventButton' button.
     * This method asks for a file and then creates a new event from that.
     */
    public void adminAddEvent(){
        FileChooser.ExtensionFilter onlyJson =
                new FileChooser.ExtensionFilter("JSON Files", "*.json");
        FileChooser.ExtensionFilter allFiles =
                new FileChooser.ExtensionFilter("All Files", "*.*");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your JSON file");
        fileChooser.getExtensionFilters().addAll(onlyJson,allFiles);
        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        try {
            String selectedJson = Files.readString(selectedFile.toPath());

            ObjectMapper objectMapper = new ObjectMapper();

            Event newEvent = objectMapper.readValue(selectedJson, Event.class);

            ArrayList<ExpenseTag> newExpenseTags = new ArrayList<>();
            for(ExpenseTag expenseTag: newEvent.getExpenseTags()){
                ExpenseTag newExpenseTag = new ExpenseTag(expenseTag.getName(),
                        expenseTag.getColour());
                ExpenseTag newExpenseTag2 = service.addExpenseTag(newExpenseTag);
                newExpenseTags.add(newExpenseTag2);
            }
            ArrayList<Expense> newExpenses = createNewExpenses(newEvent,newExpenseTags);
            newEvent.setExpenses(newExpenses);
            ArrayList<User> newParticipants = createNewParticipants(newEvent);
            newEvent.setParticipants(newParticipants);
            List<Long> eventCodes = service.getEvents()
                    .stream().map(q -> q.getEventCode()).toList();
            Random random = new Random();
            long eventCode;
            do {
                eventCode = Math.abs(random.nextLong() % 100000000);
            }
            while (eventCodes.contains(eventCode));

            newEvent.setEventCode(eventCode);
            newEvent.setExpenseTags(newExpenseTags);

            service.addEvent(newEvent);
            System.out.println("Event added successfully");
            refresh();
        }catch (IOException ex) {
            System.out.println("There was a problem with adding a event (Admin)");
            ex.printStackTrace();
        }

    }

    /**
     * Method which creates new expenses from an existing event
     * @param newEvent the original event
     * @return returns the created expenses
     * @param newExpenseTags the new expense tags
     */
    public ArrayList<Expense> createNewExpenses(Event newEvent,
                                                ArrayList<ExpenseTag> newExpenseTags) {
        ArrayList<Expense> newExpenses = new ArrayList<>();
        for (Expense expense : newEvent.getExpenses()) {

            User newPayer = new User(expense.getPayer().getUsername(),
                    expense.getPayer().getEmail(), expense.getPayer().getIban(),
                    expense.getPayer().getBic());
            User newPayingParticipant = service.addUser(newPayer);

            ArrayList<User> newPayingParticipants = new ArrayList<>();

            ExpenseTag oldTag = expense.getExpenseTag();
//            ExpenseTag newTag = new ExpenseTag();
            for(ExpenseTag expenseTag: newExpenseTags){
                if(expenseTag.equals(oldTag)){
                    oldTag = expenseTag;
                    break;
                }
            }

            for(User payingParticipant : expense.getPayingParticipants()){
                User newPayingParticipant2 = new User(payingParticipant.getUsername(),
                        payingParticipant.getEmail(),
                        payingParticipant.getIban(), payingParticipant.getBic());
                User newPayingParticipant3 = service.addUser(newPayingParticipant2);
                newPayingParticipants.add(newPayingParticipant3);
            }

            Expense expense2 = new Expense(expense.getName(), expense.getAmount(),
                    newPayingParticipant, newPayingParticipants, expense.getDate());
            expense2.setExpenseTag(oldTag);

            Expense expense3 = service.addExpense(expense2);
            newExpenses.add(expense3);
            for (User u : expense2.getPayingParticipants()) {
                double debtAmount = expense2.getAmount()/(expense2.getPayingParticipants().size()+1);
                Debt debt = new Debt(u, expense2.getPayer(), debtAmount, newEvent);
                service.addDebt(debt);
                List<Debt> debts = new ArrayList<>(u.getDebts());
                debts.add(debt);
                u.setDebts(debts);
                service.updateUser(u);
            }
        }
        return newExpenses;
    }

    private List<Debt> createNewDebt(User newUser, User oldUser) {
        List<Debt> newDebts = new ArrayList<>();
        for(Debt debt : oldUser.getDebts()){
            User payer = newUser;
            User payee = debt.getPayee();

            User newPayer = new User(payer.getUsername(), payer.getEmail(),
                    payer.getIban(), payer.getBic());
            User newPayer2 = service.addUser(newPayer);

            User newPayee = new User(payee.getUsername(), payee.getEmail(),
                    payee.getIban(), payee.getBic());
            User newPayee2 = service.addUser(newPayee);

            Debt newDebt = new Debt();
            newDebt.setAmount(debt.getAmount());
            newDebt.setPayer(newPayer2);
            newDebt.setPayee(newPayee2);

            Debt addedDebt = service.addDebt(newDebt);

            newDebts.add(addedDebt);
        }
        return newDebts;
    }

    /**
     * Method which creates new participants from an existing event
     * @param newEvent the original event
     * @return returns the created participants
     */
    public ArrayList<User> createNewParticipants(Event newEvent) {
        ArrayList<User> participants = new ArrayList<>();
        List<User> oldParticipants = newEvent.getParticipants();
        for(User user : oldParticipants){
            User newUser = new User(user.getUsername(),
                    user.getEmail(), user.getIban(), user.getBic());
            user.setDebts(createNewDebt(newUser,user));
            User newUser2 = service.addUser(newUser);
            participants.add(newUser2);
        }
        return participants;
    }

    /**
     * Refreshes the page
     */
    public void refresh() {
        var events = service.getEvents();
        data = FXCollections.observableList(events);
        table.setItems(data);
        sortEvent.handle(new ActionEvent());
    }

    /**
     * Method to go back to startpage
     */
    public void back() {
        service.showStartPage();
    }

    private EventHandler<ActionEvent> sortEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            String option = sortMenu.getValue();
            List<Event> events = table.getItems();
            if (optionTitle.equals(option)){
                System.out.println(optionTitle);
                Collections.sort(events, eventTitleComparator);
            } else if (optionDate.equals(option)) {
                System.out.println(optionDate);
                Collections.sort(events, eventCreationDateComparator);
            } else if (optionActivity.equals(option)) {
                System.out.println(optionActivity);
                Collections.sort(events, eventLastActivityComparator);
            }
            table.setItems(FXCollections.observableList(events));
        }
    };

    private EventHandler<MouseEvent> getEvent = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent e) {
            Event selectedEvent;
            if (e.getSource() == table) {
                selectedEvent = table.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
            if (selectedEvent != null) {
                service.showAdminEventInfo(selectedEvent);
            }
        }
    };

    private static Comparator<Event> eventTitleComparator = new Comparator<Event>() {
        public int compare(Event s1, Event s2) {
            String eventTitle1 = s1.getTitle().toUpperCase();
            String eventTitle2 = s2.getTitle().toUpperCase();

            return eventTitle1.compareTo(eventTitle2);
        }};
    private static Comparator<Event> eventCreationDateComparator = new Comparator<Event>() {
        public int compare(Event s1, Event s2) {
            Long eventId1 = s1.getId();
            Long eventId2 = s2.getId();

            return eventId2.compareTo(eventId1);
        }};

    private static Comparator<Event> eventLastActivityComparator = new Comparator<Event>() {
        public int compare(Event s1, Event s2) {
            Long lastExpenseId1;
            Long lastExpenseId2;
            if(s1.getExpenses().isEmpty() && !s2.getExpenses().isEmpty())
                return 1;
            else if(!s1.getExpenses().isEmpty() && s2.getExpenses().isEmpty())
                return -1;
            else if(!s1.getExpenses().isEmpty() && !s2.getExpenses().isEmpty()){
                lastExpenseId1 = s1.getExpenses().getLast().getId();
                lastExpenseId2 = s2.getExpenses().getLast().getId();

                return lastExpenseId2.compareTo(lastExpenseId1);}
            else
                return 0;
        }};

}
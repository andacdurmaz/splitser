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
import commons.Event;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> colEventId;
    @FXML
    private TableColumn<Event, String> colEventName;
    @FXML
    private TableColumn<Event, String> colEventDescription;
    @FXML
    private ChoiceBox<String> sortMenu;
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
        colEventId.setCellValueFactory(q ->
                new SimpleStringProperty(String.valueOf(q.getValue().getId())));
        colEventName.setCellValueFactory(q ->
                new SimpleStringProperty(q.getValue().getTitle()));
        colEventDescription.setCellValueFactory(q ->
                new SimpleStringProperty(q.getValue().getDescription()));
        sortMenu.getItems().addAll("Option 1", "Option 2", "Option 3");
        sortMenu.setValue("Option 1");
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
        Stage stage = (Stage) adminAddEventButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        try {
            String selectedJson = Files.readString(selectedFile.toPath());

            ObjectMapper objectMapper = new ObjectMapper();

            Event newEvent = objectMapper.readValue(selectedJson, Event.class);
            service.addEvent(newEvent);
            refresh();
        }catch (IOException ex) {
            System.out.println("There was a problem with adding a event (Admin)");
        }

    }
    /**
     * Refreshes the page
     */
    public void refresh() {
        var events = service.getEvents();
        data = FXCollections.observableList(events);
        table.setItems(data);
    }

    private EventHandler<ActionEvent> sortEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            String option = sortMenu.getValue();
            List<Event> events = table.getItems();
            switch(option){
                case("Option 1"):
                    System.out.println("option 1 selected");
                    Collections.sort(events, eventTitleComparator);
                    break;
                case("Option 2"):
                    System.out.println("option 2 selected");
                    Collections.sort(events, eventCreationDateComparator);
                    break;
                case("Option 3"):
                    System.out.println("option 3 selected");
                    Collections.sort(events, eventLastActivityComparator);
                    break;

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

            return eventId1.compareTo(eventId2);
        }};

    private static Comparator<Event> eventLastActivityComparator = new Comparator<Event>() {
        public int compare(Event s1, Event s2) {
            Long lastExpenseId1 = s1.getExpenses().getLast().getId();
            Long lastExpenseId2 = s2.getExpenses().getLast().getId();

            return lastExpenseId1.compareTo(lastExpenseId2);
        }};
    @FXML
    private void back() {
        service.showStartPage();
    }
}
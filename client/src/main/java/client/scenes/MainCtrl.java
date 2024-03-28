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

import client.Main;
import client.utils.ServerUtils;
import org.json.JSONArray;
import org.json.JSONObject;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import commons.Event;
import commons.Expense;
import commons.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;


public class MainCtrl {

    private Stage primaryStage;
    private StartPageCtrl startPageCtrl;
    private Scene startPage;


    private AddEventCtrl addCtrl;
    private Scene add;
    private EventInfoCtrl eventInfoCtrl;
    private Scene eventInfo;
    private AddOrEditExpenseCtrl addOrEditExpenseCtrl;

    private Scene addOrEditExpense;

    private AddOrEditParticipantCtrl addOrEditParticipantCtrl;

    private Scene addOrEditParticipant;
    private AdminOverviewCtrl adminOverviewCtrl;
    private Scene adminOverview;
    private AdminEventInfoCtrl adminEventInfoCtrl;

    private Scene adminEventInfo;

    /**
     * Initialize mainCtrl
     *
     * @param startPage                      start page
     * @param primaryStage                   stage
     * @param addOrEditExpenseCtrlParentPair
     * @param add                            add
     * @param eventInfo                      eventInfo
     * @param addOrEditParticipantCtrParentPair  addOrEditParticipantCtrlParentPair
     */
    public void initialize(Stage primaryStage,
                           Pair<StartPageCtrl, Parent> startPage,
                           Pair<AddOrEditExpenseCtrl, Parent> addOrEditExpenseCtrlParentPair,
                           Pair<AddEventCtrl, Parent> add,
                           Pair<EventInfoCtrl, Parent> eventInfo,
                           Pair<AddOrEditParticipantCtrl,Parent>
                                   addOrEditParticipantCtrParentPair) {
        this.primaryStage = primaryStage;

        this.startPageCtrl = startPage.getKey();
        this.startPage = new Scene(startPage.getValue());

        this.addOrEditExpenseCtrl = addOrEditExpenseCtrlParentPair.getKey();
        this.addOrEditExpense = new Scene(addOrEditExpenseCtrlParentPair.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.eventInfoCtrl = eventInfo.getKey();
        this.eventInfo = new Scene(eventInfo.getValue());

        this.addOrEditParticipantCtrl = addOrEditParticipantCtrParentPair.getKey();
        this.addOrEditParticipant = new Scene(addOrEditParticipantCtrParentPair.getValue());

        showStartPage();
        primaryStage.show();

    }

    /**
     * Initialize mainCtrl
     *
     * @param adminOverview  Admin overview
     * @param adminEventInfo Admin event info
     */
    public void adminInitialize(Pair<AdminOverviewCtrl,
            Parent> adminOverview, Pair<AdminEventInfoCtrl, Parent> adminEventInfo) {
        this.adminOverviewCtrl = adminOverview.getKey();
        this.adminOverview = new Scene(adminOverview.getValue());

        this.adminEventInfoCtrl = adminEventInfo.getKey();
        this.adminEventInfo = new Scene(adminEventInfo.getValue());


    }

    /**
     * Shows start page
     */
    public void showStartPage() {
        primaryStage.setTitle("Home");
        primaryStage.setScene(startPage);
        startPageCtrl.refresh();
    }


    /**
     * Shows addEvent
     */
    public void showAdd() {
        primaryStage.setTitle("Events: Adding Event");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Shows EventInfo
     *
     * @param event event to be shown
     */
    public void showEventInfo(Event event) {
        primaryStage.setTitle(event.getTitle());
        eventInfoCtrl.setEvent(event);
        eventInfoCtrl.updateLabelText(event);
        primaryStage.setScene(eventInfo);
        eventInfo.setOnKeyPressed(e -> eventInfoCtrl.keyPressed(e));
    }



    /**
     * Shows add or edit expense page
     *
     * @param expense
     */
    public void showAddOrEditExpense(Expense expense) {
        primaryStage.setTitle("Add/Edit expense");
        addOrEditExpenseCtrl.setExpense(expense);
        primaryStage.setScene(addOrEditExpense);
        addOrEditExpense.setOnKeyPressed(e -> addOrEditExpenseCtrl.keyPressed(e));
    }


    /**
     * shows participants
     * @param user
     */
    public void showAddOrEditParticipants(User user) {
        primaryStage.setTitle("Add/Edit participant");
        addOrEditParticipantCtrl.setUser(user);
        primaryStage.setScene(addOrEditParticipant);
        addOrEditParticipant.setOnKeyPressed(e -> addOrEditParticipantCtrl.keyPressed(e));
    }

    /**
     * Shows AdminOverview
     */
    public void showAdminOverview() {
        primaryStage.setTitle("Admin: Overview");
        primaryStage.setScene(adminOverview);
        adminOverviewCtrl.refresh();
    }

    /**
     * Shows AdminEventInfo
     */
    public void showAdminEventInfo() {
        primaryStage.setTitle("Admin: Event info");
        primaryStage.setScene(adminEventInfo);
        adminOverviewCtrl.refresh();
    }

    /**
     * This method gives the AdminEventInfoCtrl the event, which is clicked on
     *
     * @param event the event which is clicked on in the admin overview
     */
    public void setAdminEvent(Event event) {
        adminEventInfoCtrl.setEvent(event);
    }


    /**
     * creates the login page
     * public method so that
     * any page can have the button
     * and functionality
     */
    public void login() {
        var loginScreen = Main.FXML.load(LoginCtrl.class,
                "client", "scenes", "Login.fxml");

        LoginCtrl loginCtrl = loginScreen.getKey();
        Scene loginScreenScene = new Scene(loginScreen.getValue());
        loginCtrl.returnToMenu();
        primaryStage.setTitle("Login");
        primaryStage.setScene(loginScreenScene);
    }

    /**
     * Decoupled joined events method, to test the getJoinedMethodsProvidingPath method
     * @return list of joined events
     * @throws IOException if the file is not found
     */
    public List<Event> getJoinedEvents() throws IOException {
        return getJoinedEventsProvidingPath("src/main/resources/config.json");
    }

    /**
     * gets the events that the user has joined from the CONFIG file
     * @return list of events
     * @param path path to the file
     * @throws FileNotFoundException if the file is not found
     */
    public List<Event> getJoinedEventsProvidingPath(String path) throws IOException {
        List<Event> list = new ArrayList<>();
        ServerUtils serverUtils = new ServerUtils();

        String jsonString = readConfigFile(path);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("User");
        JSONArray eventsArray = userObject.getJSONArray("Events");

        for (int i = 0; i < eventsArray.length(); i++) {
            JSONObject eventObject = eventsArray.getJSONObject(i);
            long eventId = eventObject.getLong("id");
            list.add(serverUtils.getEventById(eventId));
        }
        return list;
    }


    /**
     * reads the config file
     * @param filePath path to the file
     * @return the string representation of the file
     * @throws IOException if the file is not found
     */
    public String readConfigFile(String filePath) throws IOException {
        Path path = Path.of(filePath);
        String string = Files.readString(path);
        return string;
    }

}
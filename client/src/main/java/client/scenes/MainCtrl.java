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
import java.util.Locale;
import java.util.ResourceBundle;


public class MainCtrl {

    private Stage primaryStage;
    private StartPageCtrl startPageCtrl;
    private Scene startPage;

    private EventOverviewCtrl eventOverviewCtrl;
    private Scene overview;

    private AddEventCtrl addCtrl;
    private Scene add;
    private EventInfoCtrl eventInfoCtrl;
    private Scene eventInfo;
    private AddOrEditExpenseCtrl addOrEditExpenseCtrl;


    private Scene addOrEditExpense;

    private AddOrEditParticipantCtrl addOrEditParticipantCtrl;


    private Scene addOrEditParticipant;
    private InvitationCtrl invitationCtrl;

    private Scene invitationOverview;
    private AdminOverviewCtrl adminOverviewCtrl;
    private Scene adminOverview;
    private AdminEventInfoCtrl adminEventInfoCtrl;

    private Scene adminEventInfo;
    private ResourceBundle bundle;
    private Locale locale = new Locale("en");



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

        getConfigLocale();
        showStartPage();
        primaryStage.show();

    }

    /**
     * Method which checks the language in config file
     */
    public void getConfigLocale(){
        setLocale("en");
    }

    /**
     * Method which sets the language
     * @param language the language
     */
    public void setLocale(String language){
        this.locale = new Locale(language);
        this.bundle = ResourceBundle.getBundle("locales.resource", locale);

    }

    /**
     * initializes the eventOverview page
     * @param overview the control page of the EventOverview
     */
    public void overviewInitialize(Pair<EventOverviewCtrl, Parent> overview) {
        this.eventOverviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());
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
<<<<<<< HEAD
     * Getter for language
     * @return returns the locale
     */
    public Locale getLocale() {
        return locale;

    }
    /**
     * Initialize invitations
     * @param invitationOverview
     */
    public void invitationsInitialize(
            Pair<InvitationCtrl, Parent> invitationOverview){
        this.invitationCtrl = invitationOverview.getKey();
        this.invitationOverview = new Scene(invitationOverview.getValue());

    }

    /**
     * Shows start page
     */
    public void showStartPage() {
        primaryStage.setTitle("Home");
        startPageCtrl.removeErrorMessage();
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
        eventInfoCtrl.setData(event);
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
     * @param user that will be added/edited
     * @param event where the change will happen
     */
    public void showAddOrEditParticipants(User user, Event event) {
        primaryStage.setTitle("Add/Edit participant");
        addOrEditParticipantCtrl.setUser(user);
        addOrEditParticipantCtrl.setEvent(event);
        primaryStage.setScene(addOrEditParticipant);
        addOrEditParticipant.setOnKeyPressed(e -> addOrEditParticipantCtrl.keyPressed(e));
    }


    /**
     * Shows SendInvitations
     * @param event
     */
    public void showSendInvitations(Event event) {
        invitationCtrl.setEvent(event);
        invitationCtrl.setData(event);
        primaryStage.setScene(invitationOverview);
    }


    /**
     * Shows AdminOverview
     */
    public void showAdminOverview() {
        var adminOverview = Main.FXML.load(AdminOverviewCtrl.class,bundle, "client",
                "scenes", "AdminOverview.fxml");
        AdminOverviewCtrl adminOverviewCtrl = adminOverview.getKey();
        Scene adminOverviewScene = new Scene(adminOverview.getValue());
        primaryStage.setTitle("Admin: Overview");
        primaryStage.setScene(adminOverviewScene);
        adminOverviewCtrl.refresh();
    }

    /**
     * Shows AdminEventInfo
     * @param event event which need to be displayed
     */
    public void showAdminEventInfo(Event event) {
        var adminEventInfo = Main.FXML.load(AdminEventInfoCtrl.class,bundle, "client",
                "scenes", "AdminEventInfo.fxml");
        AdminEventInfoCtrl adminEventInfoCtrl = adminEventInfo.getKey();
        Scene adminOverviewScene = new Scene(adminEventInfo.getValue());
        primaryStage.setTitle("Admin: Event info");
        primaryStage.setScene(adminOverviewScene);
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
     * gets the events that the user has joined from the CONFIG file
     * @return list of events
     * @param path path to the file
     * @throws FileNotFoundException if the file is not found
     */
    public List<Long> getJoinedEventsIDProvidingPath(String path) throws IOException {
        List<Long> list = new ArrayList<>();
        ServerUtils serverUtils = new ServerUtils();

        String jsonString = readConfigFile(path);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("User");
        JSONArray eventsArray = userObject.getJSONArray("Events");

        for (int i = 0; i < eventsArray.length(); i++) {
            JSONObject eventObject = eventsArray.getJSONObject(i);
            long eventId = eventObject.getLong("id");
            list.add(eventId);
        }
        return list;
    }

    /**
     * gets the events that the user has joined from the CONFIG file
     * @return list of events
     * @throws IOException if the file is not found
     */
    public List<Event> getJoinedEvents() throws IOException {
        return getJoinedEventsProvidingPath("src/main/resources/CONFIG.json");
    }


    /**
     * interacts with the server to get the events that the user has joined
     * @param path  path to the file
     * @return  list of events
     * @throws IOException  if the file is not found
     */
    public List<Event> getJoinedEventsProvidingPath(String path) throws IOException {
        List<Long> eventIds = getJoinedEventsIDProvidingPath(path);
        List<Event> events = new ArrayList<>();
        ServerUtils serverUtils = new ServerUtils();

        for (int i = 0; i < eventIds.size(); i++) {
            events.add(serverUtils.getEventById(eventIds.get(i)));
        }
        return events;
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

    /**
     * shows the languageSwitch pages
     * @param c a char from previous page
     */
    public void showLanguageSwitch(char c) {
        var languageSwitch = Main.FXML.load(LanguageSwitchCtrl.class,bundle, "client",
                "scenes", "LanguageSwitch.fxml");
        LanguageSwitchCtrl languageSwitchCtrl = languageSwitch.getKey();
        Scene languageSwitchScene = new Scene(languageSwitch.getValue());

        languageSwitchCtrl.setReturn(c);
//        Stage popup = new Stage();
        primaryStage.setTitle("Language switch");
        primaryStage.setScene(languageSwitchScene);
//        popup.show();
    }
}
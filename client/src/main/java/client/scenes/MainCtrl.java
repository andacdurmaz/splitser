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
import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import org.json.JSONArray;
import org.json.JSONObject;


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
    private static final String CONFIG_PATH = "src/main/resources/CONFIG.json";

    private Stage primaryStage;
    private Stage popupStage;
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
    private AddExpenseTagCtrl addExpenseTagCtrl;
    private Scene addExpenseTag;

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
     * @param primaryStage                      stage
     */
    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;

        getConfigLocale();
        showStartPage();
        primaryStage.show();

    }


    /**
     * Method which checks the language in config file
     */
    public void getConfigLocale() {
        setLocale(getLanguage());
    }

    /**
     * Method which sets the language
     *
     * @param language the language
     */
    public void setLocale(String language) {
        this.locale = new Locale(language);
        this.bundle = ResourceBundle.getBundle("locales.resource", locale);
        writeLanguageToConfigFile(language);
    }

    /**
     * initializes the eventOverview page
     *
     * @param overview the control page of the EventOverview
     */
    public void overviewInitialize(Pair<EventOverviewCtrl, Parent> overview) {
        this.eventOverviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());
    }

    /**
     * Getter for language
     *
     * @return returns the locale
     */
    public Locale getLocale() {
        return locale;

    }

    /**
     * Getter for bundle
     * @return returns the bundle
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Shows start page
     */
    public void showStartPage() {
        var startPage = Main.FXML.load(StartPageCtrl.class, bundle, "client",
                "scenes", "StartPage.fxml");
        StartPageCtrl startPageCtrl = startPage.getKey();
        Scene startPageScene = new Scene(startPage.getValue());
        primaryStage.setTitle("Home");
        startPageCtrl.removeErrorMessage();
        primaryStage.setScene(startPageScene);
        startPageCtrl.refresh();
    }


    /**
     * Shows addEvent
     */
    public void showAdd() {
        var addEvent = Main.FXML.load(AddEventCtrl.class, bundle, "client",
                "scenes", "AddEvent.fxml");
        AddEventCtrl addEventCtrl = addEvent.getKey();
        Scene addEventCtrlScene = new Scene(addEvent.getValue());
        primaryStage.setTitle("Events: Adding Event");
        primaryStage.setScene(addEventCtrlScene);
        addEventCtrlScene.setOnKeyPressed(e -> addEventCtrl.keyPressed(e));
    }

    /**
     * Shows EventInfo
     *
     * @param event event to be shown
     */
    public void showEventInfo(Event event) {
        var eventInfo = Main.FXML.load(EventInfoCtrl.class, bundle, "client",
                "scenes", "EventInfo.fxml");
        EventInfoCtrl eventInfoCtrl = eventInfo.getKey();
        Scene eventInfoScene = new Scene(eventInfo.getValue());
        primaryStage.setTitle("Event Info");
        eventInfoCtrl.setEvent(event);
        eventInfoCtrl.setData(event);
        primaryStage.setScene(eventInfoScene);
        eventInfoScene.setOnKeyPressed(e -> eventInfoCtrl.keyPressed(e));
    }


    /**
     * Shows add or edit expense page
     *
     * @param event   event of the expense
     * @param expense expense to add or edit
     */
    public void showAddOrEditExpense(Event event, Expense expense) {
        var addOrEditExpense = Main.FXML.load(AddOrEditExpenseCtrl.class, bundle, "client",
                "scenes", "AddOrEditExpense.fxml");
        AddOrEditExpenseCtrl addOrEditExpenseCtrl = addOrEditExpense.getKey();
        Scene addOrEditExpenseScene = new Scene(addOrEditExpense.getValue());
        primaryStage.setTitle("Add/Edit expense");
        addOrEditExpenseCtrl.initialize();
        addOrEditExpenseCtrl.setup(event, expense);
        primaryStage.setScene(addOrEditExpenseScene);
        addOrEditExpenseScene.setOnKeyPressed(e -> addOrEditExpenseCtrl.keyPressed(e));
    }


    /**
     * shows participants
     *
     * @param user  that will be added/edited
     * @param event where the change will happen
     */
    public void showAddOrEditParticipants(User user, Event event) {
        var addOrEditParticipants = Main.FXML.load(AddOrEditParticipantCtrl.class, bundle, "client",
                "scenes", "AddOrEditParticipant.fxml");
        AddOrEditParticipantCtrl addOrEditParticipantCtrl = addOrEditParticipants.getKey();
        Scene addOrEditParticipantsScene = new Scene(addOrEditParticipants.getValue());
        primaryStage.setTitle("Add/Edit participant");
        addOrEditParticipantCtrl.setUser(user);
        addOrEditParticipantCtrl.setEvent(event);
        addOrEditParticipantCtrl.editFields(user);
        primaryStage.setScene(addOrEditParticipantsScene);
        addOrEditParticipantsScene.setOnKeyPressed(e -> {
            try {
                addOrEditParticipantCtrl.keyPressed(e);
            } catch (EmailFormatException ex) {
                throw new RuntimeException(ex);
            } catch (IBANFormatException ex) {
                throw new RuntimeException(ex);
            } catch (BICFormatException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    /**
     * Shows SendInvitations
     *
     * @param event
     */
    public void showSendInvitations(Event event) {
        var invitationOverview = Main.FXML.load(InvitationCtrl.class, bundle, "client",
                "scenes", "Invitation.fxml");
        InvitationCtrl invitationCtrl = invitationOverview.getKey();
        Scene invitationOverviewScene = new Scene(invitationOverview.getValue());
        invitationCtrl.setEvent(event);
        invitationCtrl.setData(event);
        primaryStage.setScene(invitationOverviewScene);
    }

    /**
     * Shows expense tags
     * @param event
     */

    public void showExpenseTags(Event event) {
        var addExpenseTags = Main.FXML.load(AddExpenseTagCtrl.class, bundle, "client",
                "scenes", "AddExpenseTag.fxml");
        AddExpenseTagCtrl addExpenseTagCtrl = addExpenseTags.getKey();
        Scene addExpenseTagsScene = new Scene(addExpenseTags.getValue());
        addExpenseTagCtrl.setEvent(event);
        primaryStage.setScene(addExpenseTagsScene);
    }

    /**
     * Shows AdminOverview
     */
    public void showAdminOverview() {
        var adminOverview = Main.FXML.load(AdminOverviewCtrl.class, bundle, "client",
                "scenes", "AdminOverview.fxml");
        AdminOverviewCtrl adminOverviewCtrl = adminOverview.getKey();
        Scene adminOverviewScene = new Scene(adminOverview.getValue());
        primaryStage.setTitle("Admin: Overview");
        primaryStage.setScene(adminOverviewScene);
        adminOverviewCtrl.refresh();
    }

    /**
     * Shows AdminEventInfo
     *
     * @param event event which need to be displayed
     */
    public void showAdminEventInfo(Event event) {
        var adminEventInfo = Main.FXML.load(AdminEventInfoCtrl.class, bundle, "client",
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
     *
     * @param path path to the file
     * @return list of events
     */
    public List<Long> getJoinedEventsIDProvidingPath(String path) {
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
     *
     * @return list of events
     */
    public List<Event> getJoinedEvents() {
        return getJoinedEventsProvidingPath(CONFIG_PATH);
    }

    /**
     * checks if the event is in the config file
     *
     * @param event event to be checked
     * @return true if the event is in the config file
     */
    public boolean isEventInConfig(Event event) {
        List<Long> eventIds = getJoinedEventsIDProvidingPath(CONFIG_PATH);
        return eventIds.contains(event.getId());
    }


    /**
     * interacts with the server to get the events that the user has joined
     *
     * @param path path to the file
     * @return list of events
     */
    public List<Event> getJoinedEventsProvidingPath(String path)  {
        List<Long> eventIds = getJoinedEventsIDProvidingPath(path);
        List<Event> events = new ArrayList<>();
        ServerUtils serverUtils = new ServerUtils();

        for (int i = 0; i < eventIds.size(); i++) {
            events.add(serverUtils.getEventById(eventIds.get(i)));
        }
        return events;
    }

    /**
     * gets the language from the CONFIG file
     * @return the language
     */
    public String getLanguage() {
        return getLanguageProvidingPath(CONFIG_PATH);
    }

    /**
     * gets the language from the CONFIG file by path
     * @param path path to the file
     * @return  the language
     */
    public String getLanguageProvidingPath(String path) {
        String jsonString = readConfigFile(path);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("User");
        return userObject.getString("Language");
    }

    /**
     * gets the currency from the CONFIG file
     * @return the currency
     */
    public String getCurrency()  {
        return getCurrencyProvidingPath(CONFIG_PATH);
    }

    /**
     * gets the currency from the CONFIG file by path
     * @param path path to the file
     * @return  the currency
     */
    public String getCurrencyProvidingPath(String path) {
        String jsonString = readConfigFile(path);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("User");
        return userObject.getString("Currency");
    }

    /**
     * reads the config file
     *
     * @param filePath path to the file
     * @return the string representation of the file
     */
    public String readConfigFile(String filePath) {
        Path path = Path.of(filePath);
        try {
            String string = Files.readString(path);
            return string;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writes to the config file
     * @param event event to be written (IN JSON FORMAT)
     */
    public void writeEventToConfigFile(Event event)  {
        writeEventToConfigFileByPath(CONFIG_PATH, event);
    }

    /**
     * writes to the config file by path
     * @param filePath path to the file
     * @param event event to be written (IN JSON FORMAT)
     */
    public void writeEventToConfigFileByPath(String filePath, Event event) {
        // Read the JSON file
        JSONObject jsonObject = new JSONObject(readConfigFile(filePath));
        // Get the User object
        JSONObject userObject = jsonObject.getJSONObject("User");
        // Get the Events array
        JSONArray eventsArray = new JSONArray();

        // Get the existing events
        JSONArray existingEvents = userObject.getJSONArray("Events");

        // Add the existing events to the new array
        for (int i = 0; i < existingEvents.length(); i++) {
            eventsArray.put(existingEvents.getJSONObject(i));
        }

        // Add the new event to the array
        JSONObject newEvent = new JSONObject(event);

        // Add all events back to the array
        eventsArray.put(newEvent);
        // Add the array back to the user object
        userObject.put("Events", eventsArray);

        // write to file
        Path path = Path.of(filePath);
        try {
            Files.writeString(path, jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writes the language to the config file
     * @param language language to be written
     */
    public void writeLanguageToConfigFile(String language) {
        writeLanguageToConfigFileByPath(CONFIG_PATH, language);
    }

    /**
     * writes the language to the config file by path
     * @param filePath path to the file
     * @param language language to be written
     */
    public void writeLanguageToConfigFileByPath(String filePath, String language) {
        JSONObject jsonObject = new JSONObject(readConfigFile(filePath));
        JSONObject userObject = jsonObject.getJSONObject("User");
        userObject.put("Language", language);

        Path path = Path.of(filePath);
        try {
            Files.writeString(path, jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * writes the currency to the config file
     * @param currency currency to be written
     */
    public void writeCurrencyToConfigFile(String currency)  {
        writeCurrencyToConfigFileByPath(CONFIG_PATH, currency);
    }

    /**
     * writes the currency to the config file by path
     * @param filePath path to the file
     * @param currency currency to be written
     */
    public void writeCurrencyToConfigFileByPath(String filePath, String currency) {
        JSONObject jsonObject = new JSONObject(readConfigFile(filePath));
        JSONObject userObject = jsonObject.getJSONObject("User");
        userObject.put("Currency", currency);

        Path path = Path.of(filePath);
        try {
            Files.writeString(path, jsonObject.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * shows the languageSwitch pages
     *
     * @param c a char from previous page
     */
    public void showLanguageSwitch(char c) {
        var languageSwitch = Main.FXML.load(LanguageSwitchCtrl.class, bundle, "client",
                "scenes", "LanguageSwitch.fxml");
        LanguageSwitchCtrl languageSwitchCtrl = languageSwitch.getKey();
        Scene languageSwitchScene = new Scene(languageSwitch.getValue());

        languageSwitchCtrl.setReturn(c);
        Stage popup = new Stage();
        popup.setTitle("Language switch");
        popup.setScene(languageSwitchScene);
        popup.show();
    }
}
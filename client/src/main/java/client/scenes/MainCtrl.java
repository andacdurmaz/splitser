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
import client.services.ConfigFileService;
import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import commons.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.json.JSONObject;

import java.util.List;
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

    private StatisticsCtrl statisticsCtrl;

    private Scene statistics;
    private AdminOverviewCtrl adminOverviewCtrl;
    private Scene adminOverview;
    private AdminEventInfoCtrl adminEventInfoCtrl;

    private Scene adminEventInfo;
    private ResourceBundle bundle;
    private Locale locale = new Locale("en");
    private KeyCombination ctrlT = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
    private  KeyCombination esc = new KeyCodeCombination(KeyCode.ESCAPE);


    /**
     * Initialize mainCtrl
     *
     * @param primaryStage                      stage
     */
    public void initialize(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Stage introStage = new Stage();
        ServerUtils.setServer(getServerAddress());

        getLanguage2();
        showIntroPage(introStage);
        introStage.showAndWait();

        getConfigLocale();
        showStartPage();
        primaryStage.show();

    }

    /**
     * gets the language from the CONFIG file
     */
    public void getLanguage2() {
        this.locale = new Locale(getLanguageProvidingPath2(CONFIG_PATH));
        this.bundle = ResourceBundle.getBundle("locales.resource", locale);
    }

    /**
     * gets the language from the CONFIG file by path
     * @param path path to the file
     * @return  the language
     */
    public String getLanguageProvidingPath2(String path) {
        String jsonString = readConfigFile(path);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject userObject = jsonObject.getJSONObject("User");
        return userObject.getString("Language");
    }

    /**
     * gets the server address from the CONFIG file
     * @return the server address
     */
    public String getServerAddress() {
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.getServerAddress();
    }

    /**
     * writes the server address to the CONFIG file
     * @param serverAddress server address
     */
    public void writeServerAddressToConfigFile(String serverAddress){
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        service.writeServerAddressToConfigFile(serverAddress);
    }




    /**
     * Method which checks the language in config file
     */
    public void getConfigLocale() {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        String l = service.getLanguage();
        setLocale(l);
    }

    /**
     * Method which sets the language
     *
     * @param language the language
     */
    public void setLocale(String language) {
        this.locale = new Locale(language);
        this.bundle = ResourceBundle.getBundle("locales.resource", locale);
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        service.writeLanguageToConfigFile(language);
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
        primaryStage.setTitle(getBundle().getString("home"));
        startPageCtrl.removeErrorMessage();
        primaryStage.setScene(startPageScene);
        startPageScene.setOnKeyPressed(startPageCtrl::keyPressed);
        startPageCtrl.refresh();
    }

    /**
     * shows intro page
     * @param introStage stage
     */
    public void showIntroPage(Stage introStage) {
        var introPage = Main.FXML.load(IntroPageCtrl.class, bundle, "client",
                "scenes", "IntroPage.fxml");
        IntroPageCtrl introPageCtrl = introPage.getKey();
        Scene introPageScene = new Scene(introPage.getValue());
        introStage.setTitle("Intro");
        introStage.setScene(introPageScene);
        introPageScene.setOnKeyPressed(introPageCtrl::keyPressed);
    }


    /**
     * Shows addEvent
     */
    public void showAdd() {
        var addEvent = Main.FXML.load(AddEventCtrl.class, bundle, "client",
                "scenes", "AddEvent.fxml");
        AddEventCtrl addEventCtrl = addEvent.getKey();
        Scene addEventCtrlScene = new Scene(addEvent.getValue());
        addEventCtrl.goBack(null);
        primaryStage.setTitle(getBundle().getString("events-adding-event"));
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
        primaryStage.setTitle(getBundle().getString("event-info"));
        eventInfoCtrl.setEvent(event);
        eventInfoCtrl.setData(event);
        primaryStage.setScene(eventInfoScene);
        eventInfoScene.setOnKeyPressed(e -> eventInfoCtrl.keyPressed(e));
        eventInfoScene.setOnKeyPressed(e -> eventInfoCtrl.keyCombinationPressed(e));
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
        primaryStage.setTitle(getBundle().getString("add/edit-expense"));
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
        primaryStage.setTitle(getBundle().getString("add/edit-participant"));
        addOrEditParticipantCtrl.setUser(user);
        addOrEditParticipantCtrl.setEvent(event);
        addOrEditParticipantCtrl.editFields(user);
        primaryStage.setScene(addOrEditParticipantsScene);
        addOrEditParticipantsScene.setOnKeyPressed(e -> {
            addOrEditParticipantCtrl.keyPressed(e);
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
     * shows statistics
     * @param event
     */
    public void showStatistics(Event event){
        var statistics = Main.FXML.load(StatisticsCtrl.class, bundle, "client", "scenes",
                "Statistics.fxml");
        StatisticsCtrl statisticsCtrl = statistics.getKey();
        Scene statisticsOverview = new Scene(statistics.getValue());
        statisticsCtrl.setEvent(event);
        statisticsCtrl.setData(event);
        primaryStage.setScene(statisticsOverview);
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
        primaryStage.setTitle(getBundle().getString("admin-overview"));
        primaryStage.setScene(adminOverviewScene);
        adminOverviewCtrl.refresh();
        adminOverviewScene.getAccelerators().put(ctrlT, adminOverviewCtrl::adminAddEvent);
        adminOverviewScene.getAccelerators().put(esc, adminOverviewCtrl::back);
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
        Scene adminEventInfoScene = new Scene(adminEventInfo.getValue());
        primaryStage.setTitle(getBundle().getString("admin-event-info"));
        primaryStage.setScene(adminEventInfoScene);
        adminEventInfoCtrl.setEvent(event);
        adminEventInfoScene.getAccelerators().put(esc, adminEventInfoCtrl::backToAdminOverview);
    }

    /**
     * creates the login page
     * public method so that
     * any page can have the button
     * and functionality
     */
    public void login() {
        var loginScreen = Main.FXML.load(LoginCtrl.class, bundle,
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

        ConfigFileService service = new ConfigFileService(new ServerUtils());
        return service.getJoinedEventsIDProvidingPath(path);
    }

    /**
     * gets the events that the user has joined from the CONFIG file
     *
     * @return list of events
     */
    public List<Event> getJoinedEvents() {
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.getJoinedEvents();
    }

    /**
     * checks if the event is in the config file
     *
     * @param event event to be checked
     * @return true if the event is in the config file
     */
    public boolean isEventInConfig(Event event) {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        List<Event> eventIds = service.getJoinedEvents();
        return eventIds.contains(event);
    }


    /**
     * removes the event from the config file
     * @param event event to be removed
     * @return true if the event is removed
     */
    public boolean deleteEventFromConfig(Event event){

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.deleteEventFromConfig(event);
    }

    /**
     * removes the event from the config file by id
     * @param id id of the event
     * @return true if the event is removed
     */
    public boolean deleteEventFromConfigByID(long id) {
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.deleteEventFromConfigByID(id);
    }

    /**
     * removes all events from the config file
     * @return true if the events are removed
     */
    public boolean deleteAllEventsFromConfig() {
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.deleteAllEventsFromConfig();
    }

    /**
     * removes the event from the config file by path
     * @param path path to the file
     * @param event event to be removed
     * @return true if the event is removed
     */
    public boolean deleteEventFromConfigProvidingPath(String path, Event event) {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.deleteEventFromConfigProvidingPath(path, event);
    }

    /**
     * interacts with the server to get the events that the user has joined
     *
     * @param path path to the file
     * @return list of events
     */
    public List<Event> getJoinedEventsProvidingPath(String path)  {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.getJoinedEventsProvidingPath(path);
    }



    /**
     * gets the language from the CONFIG file
     * @return the language
     */
    public String getLanguage() {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.getLanguage();
    }

    /**
     * gets the language from the CONFIG file by path
     * @param path path to the file
     * @return  the language
     */
    public String getLanguageProvidingPath(String path) {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.getLanguageProvidingPath(path);
    }

    /**
     * gets the currency from the CONFIG file
     * @return the currency
     */
    public String getCurrency()  {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.getCurrency();
    }

    /**
     * gets the currency from the CONFIG file by path
     * @param path path to the file
     * @return  the currency
     */
    public String getCurrencyProvidingPath(String path) {
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.getCurrencyProvidingPath(path);
    }

    /**
     * reads the config file
     *
     * @param filePath path to the file
     * @return the string representation of the file
     */
    public String readConfigFile(String filePath) {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        return service.readConfigFile(filePath);
    }

    /**
     * writes to the config file
     * @param event event to be written (IN JSON FORMAT)
     */
    public void writeEventToConfigFile(Event event)  {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        service.writeEventToConfigFile(event);
    }

    /**
     * writes to the config file by path
     * @param filePath path to the file
     * @param event event to be written (IN JSON FORMAT)
     */
    public void writeEventToConfigFileByPath(String filePath, Event event) {
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        service.writeEventToConfigFileByPath(filePath, event);
    }

    /**
     * writes the language to the config file
     * @param language language to be written
     */
    public void writeLanguageToConfigFile(String language) {
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        service.writeLanguageToConfigFile(language);
    }

    /**
     * writes the language to the config file by path
     * @param filePath path to the file
     * @param language language to be written
     */
    public void writeLanguageToConfigFileByPath(String filePath, String language) {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        service.writeLanguageToConfigFileByPath(filePath, language);
    }

    /**
     * writes the currency to the config file
     * @param currency currency to be written
     */
    public void writeCurrencyToConfigFile(String currency)  {
        ConfigFileService service =new ConfigFileService(new ServerUtils());
        service.writeCurrencyToConfigFile(currency);
    }

    /**
     * writes the currency to the config file by path
     * @param filePath path to the file
     * @param currency currency to be written
     */
    public void writeCurrencyToConfigFileByPath(String filePath, String currency) {

        ConfigFileService service =new ConfigFileService(new ServerUtils());
        service.writeCurrencyToConfigFileByPath(filePath,currency);
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
        languageSwitchScene.getAccelerators().put(esc, languageSwitchCtrl::backButton);

        languageSwitchCtrl.setReturn(c);
        Stage popup = new Stage();
        popup.setTitle(getBundle().getString("language-switch"));
        popup.setScene(languageSwitchScene);
        popup.show();
    }

    /**
     * shows the expense info page
     * @param event the event of the expense
     * @param selectedExpense the expense of the page
     */
    public void showExpenseInfo(Event event, Expense selectedExpense) {
        var expenseInfo = Main.FXML.load(ExpenseInfoCtrl.class, bundle, "client",
                "scenes", "ExpenseInfo.fxml");
        ExpenseInfoCtrl expenseInfoCtrl = expenseInfo.getKey();
        Scene expenseInfoScene = new Scene(expenseInfo.getValue());
        primaryStage.setTitle(getBundle().getString("expense-info"));
        expenseInfoCtrl.setEvent(event);
        expenseInfoCtrl.setExpense(selectedExpense);
        expenseInfoCtrl.setData();
        primaryStage.setScene(expenseInfoScene);
    }

    /**
     * shows the page with the debts of an event
     * @param event of the page
     */
    public void showSettleDebts(Event event) {
        var settleDebts = Main.FXML.load(SettleDebtsCtrl.class, bundle, "client",
            "scenes", "SettleDebts.fxml");
        SettleDebtsCtrl settleDebtsCtrl = settleDebts.getKey();
        Scene settleDebtsScene = new Scene(settleDebts.getValue());
        settleDebtsCtrl.setEvent(event);
        settleDebtsCtrl.setData();
        primaryStage.setTitle("Settle Debts");
        primaryStage.setScene(settleDebtsScene);
    }

    /**
     * shows the UserDebts page
     * @param list of the payments
     * @param event of the debts
     * @param user that will make the payment
     */
    public void showUserDebts(List<String> list, Event event, User user) {
        var userDebts = Main.FXML.load(UserDebtCtrl.class, bundle, "client",
                "scenes", "UserDebt.fxml");
        UserDebtCtrl userDebtCtrl = userDebts.getKey();
        Scene userDebtScene = new Scene(userDebts.getValue());
        userDebtCtrl.setData(list, event, user);
        primaryStage.setTitle("User Debts");
        primaryStage.setScene(userDebtScene);
    }

    /**
     * opens the settle debts page but removing a participant from the open debtors list
     * @param event of the settle debts page
     * @param user the removes user
     */
    public void removeOpenDebt(Event event, User user) {
        var settleDebts = Main.FXML.load(SettleDebtsCtrl.class, bundle, "client",
                "scenes", "SettleDebts.fxml");
        SettleDebtsCtrl settleDebtsCtrl = settleDebts.getKey();
        Scene settleDebtsScene = new Scene(settleDebts.getValue());
        settleDebtsCtrl.setEvent(event);
        settleDebtsCtrl.setData();
        settleDebtsCtrl.removeOpenDebt(user);
        primaryStage.setTitle("Settle Debts");
        primaryStage.setScene(settleDebtsScene);
    }
}

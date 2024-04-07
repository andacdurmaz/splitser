package client.scenes;

import client.services.EventOverviewService;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Event;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EventOverviewCtrl implements Initializable {

    private ObservableList<Event> data;
    @FXML
    private Label emptyLabel;
    @FXML
    private ListView<Event> eventsList;
    @FXML
    private Button loginButton;
    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> event;
    @FXML
    private TableColumn<Event, String> eventCode;
    @FXML
    private TableColumn<Event, String> description;

    /**
     * Constructor
     *
     * @param service DI service
     */
    private final EventOverviewService service;

    /**
     * @param service DI service
     */
    @Inject
    public EventOverviewCtrl(EventOverviewService service) {
        this.service = service;
        service.getServer().setSession();
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
        event.setCellValueFactory(q ->
                new SimpleStringProperty(q.getValue().getTitle()));
        eventCode.setCellValueFactory(q ->
                new SimpleStringProperty(String.valueOf(q.getValue().getEventCode())));
        description.setCellValueFactory(q ->
                new SimpleStringProperty(q.getValue().getDescription()));
//         Listen for changes to the items in the ListView,
//         if there are events make the label invisible

        // Listen for changes to the items in the ListView,
        // if there are events make the label invisible
        table.setOnMouseClicked(getEvent);
        service.getServer().registerForSocketMessages("/updates/events", Event.class, e -> {
            Platform.runLater(() -> refresh());
        });
    }

    /**
     * Check what is clicked by the mouse, if it is from the tableview,
     * go to the clicked event, if it's neither do nothing.
     *
     * @param event event clicked by mouse
     */
    private EventHandler<MouseEvent> getEvent = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent e) {
            Event selectedEvent;
            if (e.getSource() == table) {
                selectedEvent = table.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
            if (selectedEvent != null) {
                service.showEventInfo(selectedEvent);
            }
        }
    };

    /**
     * If the ListView is empty, make the label visible. If it isn't make it invisible
     */
    private void updateLabelVisibility() {
        emptyLabel.setVisible(eventsList.getItems().isEmpty());
    }

    /**
     * Refreshes the page
     */
    public void refresh() {
        var events = service.getEvents();
        data = FXCollections.observableList(events);
        TableView<Event> table = new TableView<>();
        table.setItems(data);
    }

    /**
     * deprecated
     */
    public void login(){
        loginButton.setOnAction(event1 ->  service.login());
    }

    /**
     * Add event method
     */
    public void addEvent() {
        service.showAdd();
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public ServerUtils getServer() {
        return service.getServer();
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public MainCtrl getMainCtrl() {
        return service.getMainCtrl();
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public ObservableList<Event> getData() {
        return data;
    }

    /**
     * Set method
     *
     * @param data placeholder
     */
    public void setData(ObservableList<Event> data) {
        this.data = data;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public Label getEmptyLabel() {
        return emptyLabel;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @param emptyLabel label for description
     */
    public void setEmptyLabel(Label emptyLabel) {
        this.emptyLabel = emptyLabel;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public ListView<Event> getEventsList() {
        return eventsList;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @param eventsList list of events
     */
    public void setEventsList(ListView<Event> eventsList) {
        this.eventsList = eventsList;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public TableView<Event> getTable() {
        return table;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @param table tableView
     */
    public void setTable(TableView<Event> table) {
        this.table = table;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public TableColumn<Event, String> getEvent() {
        return event;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @param event event from tableView
     */
    public void setEvent(TableColumn<Event, String> event) {
        this.event = event;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public TableColumn<Event, String> getEventCode() {
        return eventCode;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @param eventCode eventCode of event from tableView
     */
    public void setEventCode(TableColumn<Event, String> eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public TableColumn<Event, String> getDescription() {
        return description;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @param description description of event from tableView
     */
    public void setDescription(TableColumn<Event, String> description) {
        this.description = description;
    }
}

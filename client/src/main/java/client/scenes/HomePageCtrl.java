package client.scenes;

import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Event;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<Event> data;
    @FXML
    private Label emptyLabel;
    @FXML
    private ListView<Event> EventsList;
    @FXML
    private TableView<Event> table;
    @FXML
    private TableColumn<Event, String> Event;
    @FXML
    private TableColumn<Event, String> EventCode;
    @FXML
    private TableColumn<Event, String> Description;

    /**
     * Constructor
     *
     * @param server   server
     * @param mainCtrl main controller
     */
    @Inject
    public HomePageCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
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
        Event.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        EventCode.setCellValueFactory(q -> new SimpleStringProperty(String.valueOf(q.getValue().getEventCode())));
        Description.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getDescription()));
        // Listen for changes to the items in the ListView, if there are events make the label invisible
        EventsList.getItems().addListener((InvalidationListener) observable -> updateLabelVisibility());
        EventsList.setOnMouseClicked(this::onEventClicked);
        table.setOnMouseClicked(this::onEventClicked);
    }

    /**
     * Check what is clicked by the mouse, if it is from the listview or tableview,
     * go to the clicked event, if it's neither do nothing.
     *
     * @param event event clicked by mouse
     */
    private void onEventClicked(MouseEvent event) {
        Event selectedEvent;
        if (event.getSource() == EventsList) {
            selectedEvent = EventsList.getSelectionModel().getSelectedItem();
        } else if (event.getSource() == table) {
            selectedEvent = table.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        if (selectedEvent != null) {
            mainCtrl.showEventInfo(selectedEvent);
        }
    }

    /**
     * If the ListView is empty, make the label visible. If it isn't make it invisible
     */
    private void updateLabelVisibility() {
        emptyLabel.setVisible(EventsList.getItems().isEmpty());
    }

    /**
     * Refreshes the page
     */
    public void refresh() {
        var events = server.getEvents();
        data = FXCollections.observableList(events);
        table.setItems(data);
    }

    /**
     * Add event method
     */
    public void addEvent() {
        mainCtrl.showAdd();
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public ServerUtils getServer() {
        return server;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
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
     * Adding javadoc for checkstyle
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
        return EventsList;
    }

    /**
     * Adding javadoc for checkstyle
     */
    public void setEventsList(ListView<Event> eventsList) {
        this.EventsList = eventsList;
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
        return Event;
    }

    /**
     * Adding javadoc for checkstyle
     */
    public void setEvent(TableColumn<Event, String> event) {
        this.Event = event;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public TableColumn<Event, String> getEventCode() {
        return EventCode;
    }

    /**
     * Adding javadoc for checkstyle
     */
    public void setEventCode(TableColumn<Event, String> eventCode) {
        this.EventCode = eventCode;
    }

    /**
     * Adding javadoc for checkstyle
     *
     * @return placeholder
     */
    public TableColumn<Event, String> getDescription() {
        return Description;
    }

    /**
     * Adding javadoc for checkstyle
     */
    public void setDescription(TableColumn<Event, String> description) {
        this.Description = description;
    }
}

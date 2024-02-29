package client.scenes;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Event;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePage implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<Event> data;
    @FXML
    private Label emptyLabel;
    @FXML
    private ListView<String> EventsList;
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
     * @param server
     * @param mainCtrl
     */
    @Inject
    public HomePage(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Initializing starting page
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Event.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        EventCode.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getEventCode()));
        Description.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getDescription()));
        // Listen for changes to the items in the TableView
        EventsList.getItems().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateLabelVisibility();
            }
        });
    }

    /**
     *     If the ListView is empty, make the label visible. If it isn't make it invisible
     */

    private void updateLabelVisibility() {
        emptyLabel.setVisible(EventsList.getItems().isEmpty());
    }
    public void refresh() {
        var events = server.getEvents();
        data = FXCollections.observableList(events);
        table.setItems(data);
    }
    public void addEvent() {
        mainCtrl.showAdd();
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public ObservableList<Event> getData() {
        return data;
    }

    public void setData(ObservableList<Event> data) {
        this.data = data;
    }

    public Label getEmptyLabel() {
        return emptyLabel;
    }

    public void setEmptyLabel(Label emptyLabel) {
        this.emptyLabel = emptyLabel;
    }

    public ListView<String> getEventsList() {
        return EventsList;
    }

    public void setEventsList(ListView<String> eventsList) {
        EventsList = eventsList;
    }

    public TableView<Event> getTable() {
        return table;
    }

    public void setTable(TableView<Event> table) {
        this.table = table;
    }

    public TableColumn<Event, String> getEvent() {
        return Event;
    }

    public void setEvent(TableColumn<Event, String> event) {
        Event = event;
    }

    public TableColumn<Event, String> getEventCode() {
        return EventCode;
    }

    public void setEventCode(TableColumn<Event, String> eventCode) {
        EventCode = eventCode;
    }

    public TableColumn<Event, String> getDescription() {
        return Description;
    }

    public void setDescription(TableColumn<Event, String> description) {
        Description = description;
    }
}

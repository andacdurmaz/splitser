package client.scenes;
import com.google.inject.Inject;
import client.utils.ServerUtils;
import commons.Quote;
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
    private ObservableList<Quote> data;
    @FXML
    private Label emptyLabel;
    @FXML
    private ListView<String> EventsList;
    @FXML
    private TableView<Quote> table;
    @FXML
    private TableColumn<Quote, String> Event;
    @FXML
    private TableColumn<Quote, String> CreatedBy;
    @FXML
    private TableColumn<Quote, String> CreationDate;

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
        Event.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().person.firstName));
        CreatedBy.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().person.lastName));
        CreationDate.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().quote));
        // Listen for changes to the items in the TableView
        EventsList.getItems().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateLabelVisibility();
            }
        });
    }

    // for now the implementation of initialization is false, but I'm not sure how to fix it.
    // I will come back to this. The lambda expression uses attributes from person like firstName that shouldn't
    // exist
    //

    /**
     *     If the ListView is empty, make the label visible. If it isn't make it invisible
     */

    private void updateLabelVisibility() {
        emptyLabel.setVisible(EventsList.getItems().isEmpty());
    }
    public void refresh() {
        var events = server.getQuotes();
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

    public ObservableList<Quote> getData() {
        return data;
    }

    public void setData(ObservableList<Quote> data) {
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

    public TableView<Quote> getTable() {
        return table;
    }

    public void setTable(TableView<Quote> table) {
        this.table = table;
    }

    public TableColumn<Quote, String> getEvent() {
        return Event;
    }

    public void setEvent(TableColumn<Quote, String> event) {
        Event = event;
    }

    public TableColumn<Quote, String> getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(TableColumn<Quote, String> createdBy) {
        CreatedBy = createdBy;
    }

    public TableColumn<Quote, String> getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(TableColumn<Quote, String> creationDate) {
        CreationDate = creationDate;
    }
}

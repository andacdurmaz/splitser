package client.scenes;
import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Quote;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HomePage {
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

    @Inject
    public HomePage(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * Initializing starting page
     */
    public void initialize() {
        updateLabelVisibility();

        // Add an event listener to see changes in the List "EventsList" to see if it is empty or not.
        // If it is empty make the emptyLabel visible again
        //
    }

    /**
     *     If the ListView is empty, make the label visible; otherwise, make it invisible
      */
    private void updateLabelVisibility() {
        emptyLabel.setVisible(EventsList.getItems().isEmpty());
    }

}

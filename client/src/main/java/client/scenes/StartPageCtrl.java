package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;




public class StartPageCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<Event> data;

    /**
     * constructor for the starting page
     * @param server
     * @param mainCtrl
     */
    @Inject
    public StartPageCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * refreshes the data of the page
     */
    public void refresh() {
        var events = server.getEvents();
        data = FXCollections.observableList(events);
    }

    /**
     * when admin login text is clicked on, the login page opens
     * @param mouseEvent the clicking of the text
     */
    public void login(javafx.scene.input.MouseEvent mouseEvent) {
        mainCtrl.login();
    }
    /**
     * when create event text is clicked on, the addEvent page opens
     * @param mouseEvent the clicking of the text
     */
    public void createEvent(javafx.scene.input.MouseEvent mouseEvent) {
        mainCtrl.showAdd();
    }

    /**
     * when the Join Event button is clicked on, the client joins an event
     * @param actionEvent the clicking of the button
     */
    public void joinEvent(ActionEvent actionEvent) {
    }
}

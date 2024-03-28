package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class StartPageCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<Event> data;

    @FXML
    private TextField eventid;
    @FXML
    private ImageView flagDisplay;

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

    public void imageset(){
        if (mainCtrl.locale.getLanguage().equals("en")){
            Image newImage = new Image(getClass().getResourceAsStream("/client/images/englishIcon.png"));
            flagDisplay.setImage(newImage);
        }
        else if (mainCtrl.locale.getLanguage().equals("nl")){
            Image newImage = new Image(getClass().getResourceAsStream("/client/images/dutchIcon.png"));
            flagDisplay.setImage(newImage);
        }
    }

    /**
     * refreshes the data of the page
     */
    public void refresh() {
        var events = server.getEvents();
        data = FXCollections.observableList(events);
        imageset();
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
        Optional<Event> event = server.getEvents().stream()
                .filter(q -> q.getEventCode() == Long.parseLong(eventid.getText()))
                .findFirst();
        if (event.isPresent())
            mainCtrl.showEventInfo(event.get());
//        else
    }

    public void languageSwitch(){
        mainCtrl.showLanguageSwitch('s');
    }
}

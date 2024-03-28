package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.util.Optional;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class StartPageCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<Event> data;

    @FXML
    private TextField eventid;
    @FXML
    private ImageView flagDisplay;
    @FXML
    private Label noCode;


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
     * Sets the image of the liveLanguage button
     */
    public void imageset(){
        if (mainCtrl.getLocale().getLanguage().equals("en")){
            Image newImage = new Image(getClass()
                    .getResourceAsStream("/client/images/englishIcon.png"));
            flagDisplay.setImage(newImage);
        }
        else if (mainCtrl.getLocale().getLanguage().equals("nl")){
            Image newImage = new Image(getClass()
                    .getResourceAsStream("/client/images/dutchIcon.png"));
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
     * makes the error message invisible
     */
    public void removeErrorMessage() {
        noCode.setVisible(false);
    }
    /**
     * when login button is clicked on, the login page opens
     * @param actionEvent the clicking of the button
     */
    public void login(ActionEvent actionEvent) {
        mainCtrl.login();

    }
    /**
     * when create  button is clicked on, the addEvent page opens
     * @param mouseEvent the clicking of the button
     */
    public void createEvent(ActionEvent mouseEvent) {
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
        else
            noCode.setVisible(true);
    }


    /**
     * Method to show the languageSwitch
     */
    public void languageSwitch(){
        mainCtrl.showLanguageSwitch('s');
    }

}

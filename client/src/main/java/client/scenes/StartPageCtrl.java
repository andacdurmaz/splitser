package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.util.StringConverter;


public class StartPageCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private ObservableList<Event> data;

    @FXML
    private TextField eventid;
    @FXML
    private ImageView flagDisplay;
    @FXML
    private Label noCode;
    @FXML
    private Label badFormat;
    @FXML
    private ListView<Event> joinedEvents;
    @FXML
    private Button deleteEvent;
    @FXML
    private TextField serverAddress;
    @FXML
    private Button serverAddressButton;


    /**
     * constructor for the starting page
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public StartPageCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * gets all joined events of the user by its config file
     */
    public void getJoinedEvents() throws IOException {
        List<Event> events = mainCtrl.getJoinedEvents();
        data = FXCollections.observableList(mainCtrl.getJoinedEvents());
        joinedEvents.setItems(data);
    }

    /**
     * when an event is clicked on, the event info page opens
     */
    private EventHandler<MouseEvent> joinJoinedEvents = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Event event;
            if(mouseEvent.getSource() == joinedEvents && mouseEvent.getClickCount() == 2){
                event = joinedEvents.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
            if(event != null){
                mainCtrl.showEventInfo(event);
            }
        }
    };

    /**
     * initializes the page
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        joinedEvents.setCellFactory(param -> new TextFieldListCell<>(new StringConverter<Event>() {
            @Override
            public String toString(Event object) {
                return object.getTitle();
            }

            @Override
            public Event fromString(String string) {
                return null;
            }
        }));

        List<Event> events = mainCtrl.getJoinedEvents();
        joinedEvents.getItems().setAll(events);

        try {
            getJoinedEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageset();
        joinedEvents.setOnMouseClicked(joinJoinedEvents);
    }

    /**
     * Sets the image of the liveLanguage button
     */
    public void imageset(){
        Image newImage;
        switch(mainCtrl.getLocale().getLanguage()){
            case("en"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/englishIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case("nl"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/dutchIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case("tr"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/turkishIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case("de"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/germanyIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case("fr"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/franceIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case("zh"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/chinaIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case("es"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/spainIcon.png"));
                flagDisplay.setImage(newImage);
                break;
        }
    }

    /**
     * refreshes the data of the page
     */
    public void refresh()  {
        var events = server.getEvents();
        events = FXCollections.observableList(events);
        try {
            getJoinedEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageset();
    }

    /**
     * makes the error message invisible
     */
    public void removeErrorMessage() {
        noCode.setVisible(false);
        badFormat.setVisible(false);
    }

    /**
     * when login button is clicked on, the login page opens
     *
     * @param actionEvent the clicking of the button
     */
    public void login(ActionEvent actionEvent) {
        mainCtrl.login();

    }

    /**
     * when create  button is clicked on, the addEvent page opens
     *
     * @param mouseEvent the clicking of the button
     */
    public void createEvent(ActionEvent mouseEvent) {
        mainCtrl.showAdd();
    }

    /**
     * when the Join Event button is clicked on, the client joins an event
     *
     * @param actionEvent the clicking of the button
     */
    public void joinEvent(ActionEvent actionEvent) {
        try {
            Long errorCatch = Long.parseLong(eventid.getText());
        } catch (NumberFormatException nfe) {
            if(noCode.isVisible()) noCode.setVisible(false);
            badFormat.setVisible(true);
            PauseTransition pt = new PauseTransition(Duration.seconds(3.0));
            pt.setOnFinished(e -> {
                badFormat.setVisible(false);
            });
            pt.play();
            return;
        }
        Optional<Event> event = server.getEvents().stream()
                .filter(q -> q.getEventCode() == Long.parseLong(eventid.getText()))
                .findFirst();
        if (event.isPresent()) {
            mainCtrl.showEventInfo(event.get());
            if(!mainCtrl.isEventInConfig(event.get())){
                mainCtrl.writeEventToConfigFile(event.get());
            }
        }
        else {
            if(badFormat.isVisible()) {
                badFormat.setVisible(false);
            }
            noCode.setVisible(true);
            PauseTransition pt = new PauseTransition(Duration.seconds(3.0));
            pt.setOnFinished(e -> {
                noCode.setVisible(false);
            });
            pt.play();
        }
    }


    /**
     * if the delete button is clicked on, the event is deleted from the config file
     */
    public void deleteEvent() {
        Event event = joinedEvents.getSelectionModel().getSelectedItem();
        mainCtrl.deleteEventFromConfig(event);
        try {
            getJoinedEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to show the languageSwitch
     */
    public void languageSwitch(){
        mainCtrl.showLanguageSwitch('s');
    }

    /**
     * Method to process the server address
     * @param actionEvent the clicking of the button
     */
    public void processServer(ActionEvent actionEvent){
        String address = serverAddress.getText();
        if(server.setServerAddress(address)){
            serverAddressButton.setText("\u2713");
            serverAddressButton.setStyle("-fx-background-color:  green");
            PauseTransition pt = new PauseTransition(Duration.seconds(3.0));
            pt.setOnFinished(e -> {
                serverAddressButton.setText("\u2192");
                serverAddressButton.setStyle("-fx-background-color:  fd7f20");
                mainCtrl.writeServerAddressToConfigFile(address);

            });
            pt.play();
        }
        else{
            serverAddressButton.setText("\u274C");
            serverAddressButton.setStyle("-fx-background-color:  D11A2A");
            PauseTransition pt = new PauseTransition(Duration.seconds(3.0));
            pt.setOnFinished(e -> {
                serverAddressButton.setText("\u2192");
                serverAddressButton.setStyle("-fx-background-color:  fd7f20");
            });
            pt.play();
        }
    }

    /**
     * This method is for usability. Checks the pressed key
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                if(serverAddress.isFocused()){
                    processServer(null);
                }
                if(eventid.isFocused()){
                    joinEvent(null);
                }
                if(deleteEvent.isFocused()){
                    deleteEvent();
                }
            case ESCAPE:
                break;
            default:
                break;
        }
    }

}

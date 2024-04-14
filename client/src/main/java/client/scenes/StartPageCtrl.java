package client.scenes;

import client.services.StartPageService;
import com.google.inject.Inject;
import commons.Event;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class StartPageCtrl implements Initializable {
    private final StartPageService service;
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


    /**
     * constructor for the starting page
     *
     * @param service service
     */
    @Inject
    public StartPageCtrl(StartPageService service) {
        this.service = service;
    }

    /**
     * gets all joined events of the user by its config file
     */
    public void getJoinedEvents() throws IOException {
        List<Event> events = service.getJoinedEvents();
        data = FXCollections.observableList(service.getJoinedEvents());
        joinedEvents.setItems(data);
    }

    /**
     * when an event is clicked on, the event info page opens
     */
    private EventHandler<MouseEvent> joinJoinedEvents = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent mouseEvent) {
            Event event;
            if (mouseEvent.getSource() == joinedEvents && mouseEvent.getClickCount() == 2) {
                event = joinedEvents.getSelectionModel().getSelectedItem();
            } else {
                return;
            }
            if (event != null) {
                service.showEventInfo(event);
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
                if (object != null)
                    return object.getTitle();
                return "null";
            }

            @Override
            public Event fromString(String string) {
                return null;
            }
        }));

        List<Event> events = service.getJoinedEvents();
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
    public void imageset() {
        Image newImage;
        switch (service.getLang()) {
            case ("en"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/englishIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case ("nl"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/dutchIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case ("tr"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/turkishIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case ("de"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/germanyIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case ("fr"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/franceIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case ("zh"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/chinaIcon.png"));
                flagDisplay.setImage(newImage);
                break;
            case ("es"):
                newImage = new Image(getClass()
                        .getResourceAsStream("/client/images/spainIcon.png"));
                flagDisplay.setImage(newImage);
                break;
        }
    }

    /**
     * refreshes the data of the page
     */
    public void refresh() {
        var events = service.getEvents();
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
        service.login();

    }

    /**
     * when create  button is clicked on, the addEvent page opens
     */

    public void createEvent() {
        service.showAdd();
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
            if (noCode.isVisible()) noCode.setVisible(false);
            badFormat.setVisible(true);
            PauseTransition pt = new PauseTransition(Duration.seconds(3.0));
            pt.setOnFinished(e -> {
                badFormat.setVisible(false);
            });
            pt.play();
            return;
        }
        Optional<Event> event = service.getEvents().stream()
                .filter(q -> q.getEventCode() == Long.parseLong(eventid.getText()))
                .findFirst();
        if (event.isPresent()) {
            service.showEventInfo(event.get());
            if (!service.isEventInConfig(event.get())) {
                service.writeToConfig(event.get());
            }
        } else {
            if (badFormat.isVisible()) {
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(service.getString("confirmation-dialog"));
        alert.setContentText(service.getString("are-you-sure-you-want-to-delete-this-event"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Event event = joinedEvents.getSelectionModel().getSelectedItem();
            service.deleteFromConfig(event);
            try {
                getJoinedEvents();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle(service.getString("removed-event"));
            confirmation.setHeaderText(null);
            confirmation.setContentText(service.getString("successfully-removed-event"));
            confirmation.showAndWait();
        }
    }

    /**
     * Method to show the languageSwitch
     */
    public void languageSwitch() {
        service.showLanguageSwitch('s');
    }

    /**
     * gets current key pressed
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        KeyCombination addEventShortCut =
                new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);

        if (addEventShortCut.match(e)) {
            System.out.println(service.getString("combination-pressed") + ": " + addEventShortCut);
            createEvent();
        }
    }

}

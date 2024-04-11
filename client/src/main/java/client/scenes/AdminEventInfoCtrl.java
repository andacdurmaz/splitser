package client.scenes;

import client.utils.ServerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import commons.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class AdminEventInfoCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private Event currentEvent;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label eventCodeLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button adminDownloadButton;

    /**
     * Constructor for AdminEventInfo
     * @param server
     * @param mainCtrl
     */
    @Inject
    public AdminEventInfoCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
    /**
     * Initialize method
     */
    public void initialize() {
        adminDownloadButton.setOnAction(event);
    }

    private EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Directory");
            Stage stage = (Stage) adminDownloadButton.getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(stage);
            File file = new File(selectedDirectory,
                    currentEvent.getTitle() + "_Event_"+ currentEvent.getId() + ".json");

            try {
                ObjectMapper mapper = new ObjectMapper();
                currentEvent.setId(0);
                mapper.writeValue(file, currentEvent);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(mainCtrl.getBundle().getString("download"));
                alert.setHeaderText(mainCtrl.getBundle().getString("download_successful"));
                alert.setContentText(mainCtrl.getBundle()
                        .getString("download_successful_at") + file.getAbsolutePath());

                alert.showAndWait();

                System.out.println("File created successfully at: " + file.getAbsolutePath());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(mainCtrl.getBundle().getString("download"));
                alert.setContentText(mainCtrl.getBundle().getString("download_failed"));

                alert.showAndWait();
            }
        }
    };

    /**
     * set event method
     * @param event event to set
     */
    public void setEvent(Event event) {
        titleLabel.setText(event.getTitle());
        eventCodeLabel.setText(String.valueOf(event.getEventCode()));
        descriptionLabel.setText(event.getDescription());
        this.currentEvent = event;
    }

    /**
     * Delete event method
     */
    public void deleteEvent() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(mainCtrl.getBundle().getString("are-you-sure"));
        alert.setHeaderText(mainCtrl.getBundle().getString("you-are-about-to-delete-a-event"));
        alert.setContentText(mainCtrl.getBundle().getString("are-you-sure"));

        ButtonType buttonTypeOK = new ButtonType(mainCtrl.getBundle().getString("yes"));
        ButtonType buttonTypeCancel = new ButtonType(mainCtrl.getBundle().getString("no"));
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK){
            String eventTitle = currentEvent.getTitle();
            server.deleteEvent(currentEvent);
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle(mainCtrl.getBundle().getString("event-deleted-successfully"));
            message.setHeaderText("'"+ eventTitle +"' " +mainCtrl.getBundle()
                    .getString("event-deleted-successfully"));
            message.show();
            mainCtrl.showAdminOverview();
        }
    }

    /**
     * Method to return to AdminOverview
     */
    public void backToAdminOverview(){
        mainCtrl.showAdminOverview();
    }
}
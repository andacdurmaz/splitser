package client.scenes;

import client.services.AdminEventInfoService;
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
    private final AdminEventInfoService service;

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
     * @param service service
     */
    @Inject
    public AdminEventInfoCtrl(AdminEventInfoService service) {
        this.service = service;
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
            directoryChooser.setTitle(service.getString("select-directory"));
            Stage stage = (Stage) adminDownloadButton.getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(stage);
            File file = new File(selectedDirectory,
                    currentEvent.getTitle() + "_Event_"+ currentEvent.getId() + ".json");

            try {
                ObjectMapper mapper = new ObjectMapper();
                currentEvent.setId(0);
                mapper.writeValue(file, currentEvent);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(service.getString("download"));
                alert.setHeaderText(service.getString("download_successful"));
                alert.setContentText(service
                        .getString("download_successful_at") + file.getAbsolutePath());

                alert.showAndWait();

                System.out.println(service.
                        getString("file-created-successfully-at") + ": " +
                        file.getAbsolutePath());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(service.getString("download"));
                alert.setContentText(service.getString("download_failed"));

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
        alert.setTitle(service.getString("are-you-sure"));
        alert.setHeaderText(service.getString("you-are-about-to-delete-a-event"));
        alert.setContentText(service.getString("are-you-sure"));

        ButtonType buttonTypeOK = new ButtonType(service.getString("yes"));
        ButtonType buttonTypeCancel = new ButtonType(service.getString("no"));
        alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOK){
            System.out.println(currentEvent.getId());
            String eventTitle = currentEvent.getTitle();
            service.deleteEvent(currentEvent);
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle(service.getString("event-deleted-successfully"));
            message.setHeaderText("'"+ eventTitle +"' " +service
                    .getString("event-deleted-successfully"));
            message.show();
            service.showAdminOverview();
        }
    }

    /**
     * Method to return to AdminOverview
     */
    public void backToAdminOverview(){
        service.showAdminOverview();
    }
}
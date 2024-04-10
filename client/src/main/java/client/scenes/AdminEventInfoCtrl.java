package client.scenes;

import client.services.AdminEventInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import commons.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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
            directoryChooser.setTitle("Select Directory");
            Stage stage = (Stage) adminDownloadButton.getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(stage);
            File file = new File(selectedDirectory,
                    currentEvent.getTitle() + "_Event_"+ currentEvent.getId() + ".json");

            try {
                ObjectMapper mapper = new ObjectMapper();
                currentEvent.setId(0);
                mapper.writeValue(file, currentEvent);

                System.out.println("File created successfully at: " + file.getAbsolutePath());
                adminDownloadButton.setText("Downloaded");
            } catch (IOException ex) {
                adminDownloadButton.setText("Download failed");
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
        service.deleteEvent(currentEvent);
        service.showAdminOverview();
    }

    /**
     * Method to return to AdminOverview
     */
    public void backToAdminOverview(){
        service.showAdminOverview();
    }
}
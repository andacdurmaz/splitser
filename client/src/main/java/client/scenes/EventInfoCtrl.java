package client.scenes;

import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class EventInfoCtrl {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label eventCodeLabel;
    @FXML
    private Label descriptionLabel;

    public void initialize() {

    }

    public void setEvent(Event event) {
        titleLabel.setText(event.getTitle());
        eventCodeLabel.setText(String.valueOf(event.getEventCode()));
        descriptionLabel.setText(event.getDescription());
    }
}

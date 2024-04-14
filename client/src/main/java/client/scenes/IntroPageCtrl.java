package client.scenes;

import client.services.IntroPageService;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class IntroPageCtrl implements Initializable {
    private final IntroPageService service;

    @FXML
    private Button startButton;
    @FXML
    private TextField serverAddress;
    @FXML
    private Button serverAddressButton;

    /**
     * constructor for the starting page
     * @param service service
     */
    @Inject
    public IntroPageCtrl(IntroPageService service){
        this.service = service;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverAddress.setText(service.getMainCtrl().getServerAddress());
    }


    /**
     * Method to handle the start button
     */
    public void start() {
        ServerUtils server = new ServerUtils();
        if (!server.setServerAddress(serverAddress.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(service
                    .getString("server-not-found"));
            alert.setHeaderText(service
                    .getString("the-server-you-wanted-is-unavailable"));
            alert.setContentText(service
                    .getString("please-check-the-server-address-and-try-again"));
            alert.show();
        } else {
            Stage stage = (Stage) startButton.getScene().getWindow();
            // do what you have to do
            stage.close();
        }

    }

    /**
     * Method to process the server address
     *
     * @param actionEvent the clicking of the button
     */
    public void processServer(ActionEvent actionEvent) {
        ServerUtils server = new ServerUtils();
        String address = serverAddress.getText();

        Alert conf = new Alert(Alert.AlertType.CONFIRMATION);
        conf.setTitle(service.getString("confirmation-dialog"));
        conf.setContentText(service
                .getString("are-you-sure-you-want-to-connect-to-the-server")
                + " " + address + " ?");
        Optional<ButtonType> result = conf.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (server.setServerAddress(address)) {
                serverAddressButton.setText("\u2713");
                serverAddressButton.setStyle("-fx-background-color:  green");
                PauseTransition pt = new PauseTransition(Duration.seconds(3.0));
                pt.setOnFinished(e -> {
                    serverAddressButton.setText("\u2192");
                    serverAddressButton.setStyle("-fx-background-color:  fd7f20");
                    service.getMainCtrl().writeServerAddressToConfigFile(address);

                });
                pt.play();
                service.getMainCtrl().deleteAllEventsFromConfig();
            } else {
                serverAddressButton.setText("\u274C");
                serverAddressButton.setStyle("-fx-background-color:  D11A2A");
                PauseTransition pt = new PauseTransition(Duration.seconds(3.0));
                pt.setOnFinished(e -> {
                    serverAddressButton.setText("\u2192");
                    serverAddressButton.setStyle("-fx-background-color:  fd7f20");
                });
                pt.play();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(service
                        .getString("server-not-found"));
                alert.setHeaderText(service
                        .getString("the-server-you-wanted-is-unavailable"));
                alert.setContentText(service
                        .getString("please-check-the-server-address-and-try-again"));
                alert.show();
            }
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
                if (serverAddress.isFocused()) {
                    processServer(null);
                }
            case ESCAPE:
                break;
            default:
                break;
        }
    }


}
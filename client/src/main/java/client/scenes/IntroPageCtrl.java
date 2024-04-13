package client.scenes;

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
    private final MainCtrl mainCtrl;

    @FXML
    private Button startButton;
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
    public IntroPageCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
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
        serverAddress.setText(mainCtrl.getServerAddress());
    }


    /**
     * Method to handle the start button
     */
    public void start() {
        ServerUtils server = new ServerUtils();
        if (!server.setServerAddress(serverAddress.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server not found");
            alert.setHeaderText("The server you wanted is unavailable");
            alert.setContentText("Please check the server address and try again");
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
        conf.setTitle("Confirmation Dialog");
        conf.setContentText("Are you sure you want to connect to the server : " + address + " ?");
        Optional<ButtonType> result = conf.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (server.setServerAddress(address)) {
                serverAddressButton.setText("\u2713");
                serverAddressButton.setStyle("-fx-background-color:  green");
                PauseTransition pt = new PauseTransition(Duration.seconds(3.0));
                pt.setOnFinished(e -> {
                    serverAddressButton.setText("\u2192");
                    serverAddressButton.setStyle("-fx-background-color:  fd7f20");
                    mainCtrl.writeServerAddressToConfigFile(address);

                });
                pt.play();
                mainCtrl.deleteAllEventsFromConfig();
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
                alert.setTitle("Server not found");
                alert.setHeaderText("The server you wanted is unavailable");
                alert.setContentText("Please check the server address and try again");
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
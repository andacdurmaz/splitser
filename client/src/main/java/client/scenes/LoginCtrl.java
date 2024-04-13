package client.scenes;

import client.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginCtrl implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField passwordAddressField;

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;
    @FXML
    private Label error;
    private LoginService service;



    /**
     * @param service constructor
     */
    @Inject
    public LoginCtrl(LoginService service) {
        this.service = service;
    }

    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //root.getStylesheets().add("client/css/main.css");

        passwordAddressField.setOnKeyPressed(action -> {
            if (action.getCode().equals(KeyCode.ENTER)){
                if(!passwordAddressField.getText().equals(""))
                    connect();
            }
        });
    }

    /**
     * attempts to login to the server
     * with the inputted text
     */
    public void connect() {
        String password = passwordAddressField.getText();
        if(service.connect(password))
            service.setSession();
        else
            error.setText(service.getString("incorrect-password"));
    }

    /**
     * returns to main menu
     */
    public void returnToMenu(){
        backButton.setOnAction(event -> service.returnToMenu());
    }

    /**
     * deprecated
     */
    public void login(){
        service.login();
    }


}

package client.scenes;

import client.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginCtrl implements Initializable {
    @FXML
    private AnchorPane root;


    @FXML
    private TextField AdminNameField;
    @FXML
    private TextField PasswordAddressField;

    @FXML
    private Button backButton;

    @FXML
    private Button loginButton;
    LoginService service;


    @Inject
    public LoginCtrl(LoginService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.getStylesheets().add("client/css/main.css");

        PasswordAddressField.setOnKeyPressed(action -> {
            if (action.getCode().equals(KeyCode.ENTER)){
                if(!AdminNameField.getText().equals("") && !PasswordAddressField.getText().equals(""))
                    connect();
            }
        });
    }

    private void connect() {
        String admin = AdminNameField.getText();
        String password = PasswordAddressField.getText();
        if(!service.connect(admin, password)){
            return;
        }
        service.setSession();
    }

    public void returnToMenu(){
        backButton.setOnAction(event -> service.returnToMenu());
    }

    public void login(){
        service.login();
    }


}

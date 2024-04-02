/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LanguageSwitchCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private char returningPage;
    @FXML
    private ImageView englishImage;
    @FXML
    private ImageView dutchImage;
    @FXML
    private ImageView turkishImage;
    @FXML
    private Button downloadThemplate;

    /**
     * Constructor for AdminOverview
     * @param server
     * @param mainCtrl
     */
    @Inject
    public LanguageSwitchCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
    /**
     * Initialize method
     *
     * @param location  URL
     *                  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources ResourceBundle
     *                  The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image englishFlag = new Image(getClass()
                .getResourceAsStream("/client/images/englishIcon.png"));
        englishImage.setImage(englishFlag);
        Image dutchFlag = new Image(getClass()
                .getResourceAsStream("/client/images/dutchIcon.png"));
        dutchImage.setImage(dutchFlag);
        Image turkishFlag = new Image(getClass()
                .getResourceAsStream("/client/images/turkishIcon.png"));
        turkishImage.setImage(turkishFlag);
    }

    /**
     * Set the return page
     * @param c a char which indicated which page
     */
    public void setReturn(char c){
        returningPage = c;
    }

    /***
     * Method to go back to previous page
     */
    public void backButton(){
        if (returningPage=='o')
            System.out.println("overview");
        else
            mainCtrl.showStartPage();
    }

    /**
     * Method to set the language to english
     */
    public void setEnglish(){
        mainCtrl.setLocale("en");
        backButton();
    }

    /**
     * Method to set language to dutch
     */
    public void setDutch(){
        mainCtrl.setLocale("nl");
        backButton();
    }

    /**
     * Method to set language to turkish
     */
    public void setTurkish(){
        mainCtrl.setLocale("tr");
        backButton();
    }

    public void downloadThemplate(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        Stage stage = (Stage) downloadThemplate.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        File file = new File(selectedDirectory, "resource_THEMPLATE.properties");

        try {

            Enumeration<String> bundleSet =ResourceBundle.getBundle("locales.resource", new Locale("en")).getKeys();

            Properties properties = new Properties();

            while (bundleSet.hasMoreElements()) {
                String key = bundleSet.nextElement();
                properties.put(key, "PUT-SOMETHING-HERE");
            }

            System.out.println(bundleSet);
            properties.store(new FileOutputStream(file), "THEMPLATE FILE OF SPLITTY");

            System.out.println("File created successfully at: " + file.getAbsolutePath());
            downloadThemplate.setText(mainCtrl.getBundle().getString("download_successful"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
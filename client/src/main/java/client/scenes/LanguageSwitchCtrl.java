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

import client.services.LanguageSwitchService;
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

    private final LanguageSwitchService service;

    private char returningPage;
    @FXML
    private ImageView englishImage;
    @FXML
    private ImageView dutchImage;
    @FXML
    private ImageView turkishImage;
    @FXML
    private ImageView spanishImage;
    @FXML
    private ImageView frenchImage;
    @FXML
    private ImageView chineseImage;
    @FXML
    private ImageView germanImage;
    @FXML
    private Button downloadTemplate;

    /**
     * Constructor for AdminOverview
     * @param service service
     */
    @Inject
    public LanguageSwitchCtrl(LanguageSwitchService service) {
        this.service = service;
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
        Image germanFlag = new Image(getClass()
                .getResourceAsStream("/client/images/germanyIcon.png"));
        germanImage.setImage(germanFlag);
        Image frenchFlag = new Image(getClass()
                .getResourceAsStream("/client/images/franceIcon.png"));
        frenchImage.setImage(frenchFlag);
        Image chineseFlag = new Image(getClass()
                .getResourceAsStream("/client/images/chinaIcon.png"));
        chineseImage.setImage(chineseFlag);
        Image spanishFlag = new Image(getClass()
                .getResourceAsStream("/client/images/spainIcon.png"));
        spanishImage.setImage(spanishFlag);
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
        Stage stage = (Stage) englishImage.getScene().getWindow();
        stage.close();
        if (returningPage=='o')
            System.out.println(service.getString("overview"));
        else
            service.showStartPage();
    }

    /**
     * Method to set the language to english
     */
    public void setEnglish(){
        service.setLocale("en");
        backButton();
    }

    /**
     * Method to set language to dutch
     */
    public void setDutch(){
        service.setLocale("nl");
        backButton();
    }

    /**
     * Method to set language to turkish
     */
    public void setTurkish(){
        service.setLocale("tr");
        backButton();
    }
    /**
     * Method to set language to french
     */
    public void setFrench(){
        service.setLocale("fr");
        backButton();
    }
    /**
     * Method to set language to spanish
     */
    public void setSpanish(){
        service.setLocale("es");
        backButton();
    }
    /**
     * Method to set language to chinese
     */
    public void setChinese(){
        service.setLocale("zh");
        backButton();
    }
    /**
     * Method to set language to german
     */
    public void setGerman(){
        service.setLocale("de");
        backButton();
    }

    /**
     * Method to download the language template
     * @param e the action event
     */
    public void downloadTemplate(ActionEvent e) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        Stage stage = (Stage) downloadTemplate.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        File file = new File(selectedDirectory, "resource_TEMPLATE.properties");

        try {

            Enumeration<String> bundleSet =
                    ResourceBundle.getBundle("locales.resource", new Locale("en")).getKeys();

            Properties properties = new Properties();

            while (bundleSet.hasMoreElements()) {
                String key = bundleSet.nextElement();
                properties.put(key, "PUT-SOMETHING-HERE");
            }

            System.out.println(bundleSet);
            properties.store(new FileOutputStream(file), "TEMPLATE FILE OF SPLITTY");

            System.out.println(service
                    .getString("file-created-successfully-at")
                    + ": " + file.getAbsolutePath());
            downloadTemplate.setText(service.getString("download_successful"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class LanguageSwitchCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private char returningPage;
    @FXML
    private ImageView englishImage;
    @FXML
    private ImageView dutchImage;

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
}
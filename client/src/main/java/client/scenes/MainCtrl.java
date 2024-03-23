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

import commons.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private HomePageCtrl homePageCtrl;
    private Scene overview;

    private AddEventCtrl addCtrl;
    private Scene add;
    private EventInfoCtrl eventInfoCtrl;
    private Scene eventInfo;
    private AdminOverviewCtrl adminOverviewCtrl;
    private Scene adminOverview;
    private AdminEventInfoCtrl adminEventInfoCtrl;
    private Scene adminEventInfo;

    /**
     * Initialize mainCtrl
     *
     * @param primaryStage stage
     * @param overview     ow
     * @param add          add
     * @param eventInfo    eventInfo
     */
    public void initialize(Stage primaryStage, Pair<HomePageCtrl,
            Parent> overview, Pair<AddEventCtrl, Parent> add,
                           Pair<EventInfoCtrl, Parent> eventInfo) {
        this.primaryStage = primaryStage;

        this.homePageCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.eventInfoCtrl = eventInfo.getKey();
        this.eventInfo = new Scene(eventInfo.getValue());

        showOverview();
        primaryStage.show();
    }

    /**
     * Initialize mainCtrl
     * @param adminOverview Admin overview
     * @param adminEventInfo Admin event info
     */
    public void adminInitilize(Pair<AdminOverviewCtrl,
            Parent> adminOverview, Pair<AdminEventInfoCtrl, Parent> adminEventInfo) {
        this.adminOverviewCtrl = adminOverview.getKey();
        this.adminOverview = new Scene(adminOverview.getValue());

        this.adminEventInfoCtrl = adminEventInfo.getKey();
        this.adminEventInfo = new Scene(adminEventInfo.getValue());
    }

    /**
     * Shows Homepage
     */
    public void showOverview() {
        primaryStage.setTitle("Events: Overview");
        primaryStage.setScene(overview);
        homePageCtrl.refresh();
    }

    /**
     * Shows addEvent
     */
    public void showAdd() {
        primaryStage.setTitle("Events: Adding Event");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**
     * Shows EventInfo
     *
     * @param event event to be shown
     */
    public void showEventInfo(Event event) {
        primaryStage.setTitle(event.getTitle());
        primaryStage.setScene(eventInfo);
        eventInfoCtrl.setEvent(event);
    }

    /**
     * Shows AdminOverview
     */
    public void showAdminOverview() {
        primaryStage.setTitle("Admin: Overview");
        primaryStage.setScene(adminOverview);
        adminOverviewCtrl.refresh();
    }

    /**
     * Shows AdminEventInfo
     */
    public void showAdminEventInfo() {
        primaryStage.setTitle("Admin: Event info");
        primaryStage.setScene(adminEventInfo);
        adminOverviewCtrl.refresh();
    }

    /**
     * This method gives the AdminEventInfoCtrl the event, which is clicked on
     * @param event the event which is clicked on in the admin overview
     */
    public void setAdminEvent(Event event){
        adminEventInfoCtrl.setEvent(event);
    }

}
package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;

public class StatisticsService {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public StatisticsService(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * @param e Event
     */
    public void showEventInfo(Event e) {
        mainCtrl.showEventInfo(e);
    }

}

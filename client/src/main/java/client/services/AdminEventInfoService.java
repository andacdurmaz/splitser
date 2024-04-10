package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AdminEventInfoService {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public AdminEventInfoService(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }



    /**
     * @param e Event
     */
    public void showEventInfo(Event e) {
        mainCtrl.showEventInfo(e);
    }

    /**
     *
     */
    public void login(){
        mainCtrl.login();
    }

    /**
     * @return all events
     */
    public List<Event> getEvents(){
        return server.getEvents();
    }

    /**
     *
     */
    public void showAdminOverview(){
        mainCtrl.showAdminOverview();
    }

    /**
     * @param e Event
     */
    public void deleteEvent(Event e){
        server.deleteEvent(e);
    }

}

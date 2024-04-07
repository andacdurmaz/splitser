package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventOverviewService {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;



    /**
     * @param server server instance
     * @param mainCtrl main ctrl instance
     */
    @Inject
    public EventOverviewService(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * @return get server instance
     */
    public ServerUtils getServer(){
        return this.server;
    }

    /**
     * @return  mainctrl instance
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * @param selectedEvent event whose info we want
     */
    public void showEventInfo(Event selectedEvent) {
        mainCtrl.showEventInfo(selectedEvent);
    }

    /**
     *
     */
    public void showAdd() {
        mainCtrl.showAdd();
    }

    /**
     *
     */
    public void login() {
        mainCtrl.login();
    }

    /**
     * @return all server events
     */
    public List<Event> getEvents(){
        return server.getEvents();
    }

    /**
     * @param id id of event
     * @return  event
     */
    public Event getEventById(long id){
        return server.getEventById(id);
    }
}

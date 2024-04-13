package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddEventService {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public AddEventService(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * @param e event to add
     * @return event
     */
    public Event addEvent(Event e){
        Event newEvent = server.addEvent(e);
        return newEvent;
    }

    /**
     * @param e Event
     */
    public void writeToConfig(Event e){
        mainCtrl.writeEventToConfigFile(e);
    }

    /**
     *
     */
    public void showStartPage(){
        mainCtrl.showStartPage();
    }

    /**
     * @param e Event
     */
    public void showEventInfo(Event e){
        mainCtrl.showEventInfo(e);
    }

    /**
     * @return all events
     */
    public List<Event> getEvents(){
        return server.getEvents();
    }

    /**
     * gets the main controller
     * @return main controller
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * @param s String
     * @return string
     */
    public String getString(String s){
        return mainCtrl.getBundle().getString(s);
    }
}

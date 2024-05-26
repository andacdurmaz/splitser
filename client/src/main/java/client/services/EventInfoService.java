package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;

@Service
public class EventInfoService {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    /**
     * @return mainctrl instance
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * Constructor
     *
     * @param server server connection
     * @param mainCtrl  mainctrl instance
     */
    @Inject
    public EventInfoService(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * @return get server instance
     */
    public ServerUtils getServer(){
        return this.server;
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
     * Register a consumer (function) to execute when new event is added to the db
     * @param consumer
     */
    public void registerForEventUpdates(Consumer<Expense> consumer) {
        server.registerForEventUpdates(consumer);
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

    /**
     * @param event update event method
     */
    public void updateEvent(Event event){
        server.updateEvent(event);
    }

    /**
     * starts a new websocket session
     */
    public void setSession(){
        server.setSession();
    }

    /**
     * @param e event to update
     * @param s address to send update
     * sends event for client comm
     */
    public void send(String s, Event e){
        server.send(s, e);
    }
    /**
     * @param s String
     * @return string
     */
    public String getString(String s){
        return mainCtrl.getBundle().getString(s);
    }
}

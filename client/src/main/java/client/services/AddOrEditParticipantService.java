package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import commons.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AddOrEditParticipantService {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public AddOrEditParticipantService(ServerUtils server, MainCtrl mainCtrl) {
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
     * @param e Event
     */
    public void updateAndShow(Event e){
        //server.updateEvent(e);
        server.send("/app/event", e);
        mainCtrl.showEventInfo(e);
    }


    /**
     * @param u user
     */
    public void updateUser(User u){
        server.updateUser(u);
    }

    /**
     * @param u user to add
     * @return user
     */
    public User addUser(User u){
        User user = server.addUser(u);
        return user;
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

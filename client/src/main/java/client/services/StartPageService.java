package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartPageService {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public StartPageService(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    /**
     * @return joined events
     */
    public List<Event> getJoinedEvents() {
        return mainCtrl.getJoinedEvents();
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
     *
     */
    public void showAdd(){
        mainCtrl.showAdd();
    }

    /**
     * @return current language
     */
    public String getLang() {
        return mainCtrl.getLocale().getLanguage();
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
    public void writeToConfig(Event e){
        mainCtrl.writeEventToConfigFile(e);
    }

    /**
     * @param e event to check
     * @return if event is in config
     */
    public boolean isEventInConfig(Event e) {
        return mainCtrl.isEventInConfig(e);
    }

    /**
     * @param e event to delete
     */
    public void deleteFromConfig(Event e){
        mainCtrl.deleteEventFromConfig(e);
    }

    /**
     * @param s 's'
     */
    public void showLanguageSwitch(Character s){
        mainCtrl.showLanguageSwitch(s);
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

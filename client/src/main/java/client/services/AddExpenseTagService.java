package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import commons.ExpenseTag;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AddExpenseTagService {


    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    @Inject
    public AddExpenseTagService(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
     * @param e Event
     */
    public void updateAndShow(Event e){
        server.updateEvent(e);
        mainCtrl.showEventInfo(e);
    }

    /**
     * @param e Event
     */
    public ExpenseTag addExpenseTag(ExpenseTag e){
        ExpenseTag expenseTag = server.addExpenseTag(e);
        return expenseTag;
    }

}

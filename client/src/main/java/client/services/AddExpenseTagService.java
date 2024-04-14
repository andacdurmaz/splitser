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

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public AddExpenseTagService(ServerUtils server, MainCtrl mainCtrl) {
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
     * @param e Expensetag
     * @return e
     */
    public ExpenseTag addExpenseTag(ExpenseTag e){
        ExpenseTag expenseTag = server.addExpenseTag(e);
        return expenseTag;
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

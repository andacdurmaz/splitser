package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import commons.User;
import commons.Debt;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AddOrEditExpenseService {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public AddOrEditExpenseService(ServerUtils server, MainCtrl mainCtrl) {
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
        server.updateEvent(e);
        server.send("/app/event", e);
        mainCtrl.showEventInfo(e);
    }

    /**
     * @param e Expense
     * @return the added expense
     */
    public Expense addExpense(Expense e){
        Expense expense = server.addExpense(e);
        return expense;
    }

    /**
     * @param d Debt
     */
    public void addDebt(Debt d){
        server.addDebt(d);
    }

    /**
     * @param e Expense
     */
    public void updateExpense(Expense e){
        server.updateExpense(e);
        server.send("/app/expenses/edit", e);
    }

    /**
     * @param u User
     */
    public void updateUser(User u){
        server.updateUser(u);
    }

    /**
     * @param id id of user
     * @return selected user
     */
    public User getUserById(Long id){
        User u = server.getUserById(id);
        return u;
    }

    /**
     * @param s String
     * @return string
     */
    public String getString(String s){
        return mainCtrl.getBundle().getString(s);
    }

    /**
     * gets the main controller
     * @return main controller
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * getter method for the server utils
     * @return the server utils
     */
    public ServerUtils getServer() {
        return server;

    }
}

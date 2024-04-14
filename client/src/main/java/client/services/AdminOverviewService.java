package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class AdminOverviewService {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public AdminOverviewService(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * @return all events
     */
    public List<Event> getEvents(){
        return server.getEvents();
    }

    /**
     * @param e event to add
     * @return event
     */
    public Event addEvent(Event e){
        e.setId(0);
        Event newEvent = server.addEvent(e);
        return newEvent;
    }

    /**
     * @param e Eeent
     */
    public void showAdminEventInfo(Event e){
        mainCtrl.showAdminEventInfo(e);
    }


    /**
     *
     */
    public void showStartPage(){
        mainCtrl.showStartPage();
    }


    /**
     * @param s String
     * @return string
     */
    public String getString(String s){
        return mainCtrl.getBundle().getString(s);
    }

    /**
     *  Creates a user
     * @param newPayer User
     * @return User
     */
    public User addUser(User newPayer) {
        newPayer.setUserID(0);
        return server.addUser(newPayer);
    }

    /**
     * Creates an expense
     * @param expense2 Expense
     * @return Expense
     */
    public Expense addExpense(Expense expense2) {
        expense2.setId(0);
        return server.addExpense(expense2);
    }

    /**
     * Creates an expense tag
     * @param newExpenseTag ExpenseTag
     * @return ExpenseTag
     */
    public ExpenseTag addExpenseTag(ExpenseTag newExpenseTag) {
        newExpenseTag.setId(0);
        return server.addExpenseTag(newExpenseTag);
    }

    /**
     * Creates a debt
     * @param debt Debt
     * @return Debt
     */
    public Debt addDebt(Debt debt) {
        debt.setId(0);
        return server.addDebt(debt);
    }

    /**
     * Updates a user
     * @param payingParticipant User
     */
    public void updateUser(User payingParticipant) {
        server.updateUser(payingParticipant);
    }

    /**
     * gets the main controller
     * @return main controller
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }
}

package client.scenes;

import client.Main;
import client.services.AdminOverviewService;
import client.utils.ServerUtils;
import com.google.inject.Stage;
import commons.Event;
import commons.Expense;
import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class AdminOverviewTest{
    private MainCtrl sut;
    private AdminOverviewCtrl adminOverviewCtrl;

    /**
     * BeforeEach method
     */
    @BeforeEach
    public void setup() {
        sut = new MainCtrl();
        adminOverviewCtrl = new AdminOverviewCtrl(new AdminOverviewService( new ServerUtils(), sut));
    }

    @Test
    public void createNewExpenseWithoutExpenses(){
        User user1 = new User("User1", "User1@tu.nl");
        User user2 = new User("User2", "User2@tu.nl");
        ArrayList<User> payingParticipant = new ArrayList<>(List.of(user2));
        LocalDate date = LocalDate.of(2024, 3, 3);
        Event event = new Event("Event",2,"Description");
        ArrayList<Expense> expenses = new ArrayList<>(List.of());
        event.setExpenses(expenses);

        assertEquals(expenses, adminOverviewCtrl.createNewExpenses(event, new ArrayList<>()));
    }

    @Test
    public void createNewParticipantsWithoutParticipants(){
        User user1 = new User("User1", "User1@tu.nl");
        User user2 = new User("User2", "User2@tu.nl");
        ArrayList<User> participants = new ArrayList<>(List.of());
        ArrayList<User> payingParticipant = new ArrayList<>(List.of(user2));
        Date date = new Date(2024, 3, 3);
        Expense expense = new Expense("ExpenseName", 4, user1, payingParticipant,date);
        Event event = new Event("Event",2,"Description");
        ArrayList<Expense> expenses = new ArrayList<>(List.of(expense));
        event.setExpenses(expenses);

        assertEquals(participants, adminOverviewCtrl.createNewParticipants(event));
    }

}

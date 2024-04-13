package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class AddOrEditExpenseServiceTest {
    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;
    private AddOrEditExpenseService service;

    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new AddOrEditExpenseService(server, mainCtrl);
    }


    @Test
    void getEvents() {
        List<Event> events = new ArrayList<>();
        Mockito.when(service.getEvents()).thenReturn(events);
        assertEquals(service.getEvents().size(), 0);

    }

    @Test
    void showEventInfo() {
        service.showEventInfo(new Event("Test"));
    }

    @Test
    void login() {
        service.login();
    }


    @Test
    void update() {
        service.updateAndShow(new Event("Test"));
    }


    @Test
    void addExpense() {

        Expense e = new Expense("Test", 1.0);
        service.addExpense(e);
    }

    @Test
    void updateExpense() {
        Expense e = new Expense("Test", 1.0);
        service.updateExpense(e);
    }

    @Test
    void getByID() {
        User u = new User("Mike", "ox@long.com");
        Mockito.when(server.getUserById(1)).thenReturn(u);
        assertEquals(service.getUserById(Long.valueOf(1)).getEmail(), "ox@long.com");
    }

    @Test
    void getString() {
        ResourceBundle bundle = Mockito.mock(ResourceBundle.class);
        Mockito.when(mainCtrl.getBundle()).thenReturn(new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if(key.equals("add-expense-tag"))
                    return "Add Expense Tag";
                return null;
            }

            @Override
            public Enumeration<String> getKeys() {
                return null;
            }
        });
        String s = service.getString("add-expense-tag");
        assertEquals(s, "Add Expense Tag" );
    }


}
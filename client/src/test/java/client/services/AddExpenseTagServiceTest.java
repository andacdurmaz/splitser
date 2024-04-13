package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import commons.ExpenseTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddExpenseTagServiceTest {

    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;
    private AddExpenseTagService service;

    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new AddExpenseTagService(server, mainCtrl);
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
    void addTag() {
        ExpenseTag et = new ExpenseTag("Test", "");
        Mockito.when(service.addExpenseTag(et)).thenReturn(new ExpenseTag(et.getName(), ""));
        String name = service.addExpenseTag(et).getName();
        assertEquals(name, "Test");

    }
}
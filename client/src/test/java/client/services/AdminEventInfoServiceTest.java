package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class AdminEventInfoServiceTest {

    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;

    private AdminEventInfoService service;
    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new AdminEventInfoService(server, mainCtrl);
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
    void getEvents() {
        List<Event> events = new ArrayList<>();
        Mockito.when(server.getEvents()).thenReturn(events);
        assertEquals(service.getEvents().size(), 0);

    }


    @Test
    void showAdminOverview() {
        service.showAdminOverview();
    }

    @Test
    void deleteEvent() {
        service.deleteEvent(new Event("Test(("));
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
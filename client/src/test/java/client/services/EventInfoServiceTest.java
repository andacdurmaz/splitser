package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventInfoServiceTest {

    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;
    private EventInfoService service;


    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new EventInfoService(server, mainCtrl);
    }
    @Test
    void getMainCtrl() {
        assertNotEquals(service.getMainCtrl(), null);
    }

    @Test
    void getServer() {

        assertNotEquals(service.getServer(), null);
    }

    @Test
    void showEventInfo() {
        service.showEventInfo(new Event("Test"));
    }

    @Test
    void showAdd() {
        service.showAdd();
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
    void getEventById() {

        Event e = new Event("1");
        Mockito.when(service.getEventById(e.getId())).thenReturn(e);
        assertEquals(service.getEventById(e.getId()).getEventCode(), e.getEventCode());
    }

    @Test
    void updateEvent() {
        Event e = new Event("1");
        service.updateEvent(e);

        Mockito.when(service.getEventById(e.getId())).thenReturn(e);
        assertEquals(service.getEventById(e.getId()).getEventCode(), e.getEventCode());

    }

    @Test
    void setSession() {
        service.setSession();
    }
}
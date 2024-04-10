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

class AdminOverviewServiceTest {

    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;
    private AdminOverviewService service;


    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new AdminOverviewService(server, mainCtrl);
    }

    @Test
    void getEvents() {
        List<Event> events = new ArrayList<>();
        Mockito.when(server.getEvents()).thenReturn(events);
        assertEquals(service.getEvents().size(), 0);

    }

    @Test
    void addEvent() {
        Event e = new Event("1");
        Mockito.when(server.addEvent(e)).thenReturn(e);
        Event newEvent = service.addEvent(e);
        assertEquals(newEvent.getEventCode(), e.getEventCode());
    }

    @Test
    void showAdminEventInfo() {
        service.showAdminEventInfo(new Event("Test"));
    }

    @Test
    void showStartPage() {
        service.showStartPage();
    }
}
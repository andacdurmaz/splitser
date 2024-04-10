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
}
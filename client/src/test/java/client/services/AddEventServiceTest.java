package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AddEventServiceTest {

    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;
    private AddEventService service;

    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new AddEventService(server, mainCtrl);
    }

    @Test
    void addEvent() {
        Event e = new Event("1");
        Mockito.when(server.addEvent(e)).thenReturn(e);
        Event newEvent = service.addEvent(e);
        assertEquals(newEvent.getEventCode(), e.getEventCode());
    }

    @Test
    void writeToConfig() {
        service.writeToConfig(new Event("1"));
    }

    @Test
    void showStartPage() {
        service.showStartPage();
    }

    @Test
    void showEventInfo() {
        service.showEventInfo(new Event("1"));
    }

    @Test
    void getEvents() {
        ArrayList<Event> ae = new ArrayList<Event>();
        ae.add(new Event("Mike"));
        Mockito.when(server.getEvents()).thenReturn(ae );
        ArrayList<Event> newe = new ArrayList<>();
        newe.addAll(service.getEvents());
        assertEquals(newe.get(0).getEventCode(), ae.get(0).getEventCode());
    }
}
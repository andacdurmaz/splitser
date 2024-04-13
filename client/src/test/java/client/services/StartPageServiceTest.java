package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class StartPageServiceTest {


    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;
    private StartPageService service;

    @BeforeEach
    void setUp() {
        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new StartPageService(server, mainCtrl);
    }

    @Test
    void getJoinedEvents() {

        ArrayList<Event> ae = new ArrayList<Event>();
        ae.add(new Event("Mike" ));
        Mockito.when(service.getJoinedEvents()).thenReturn(ae );
        ArrayList<Event> newe = new ArrayList<>();
        newe.addAll(service.getJoinedEvents());
        assertEquals(newe.get(0).getEventCode(), ae.get(0).getEventCode());
    }



    @Test
    void showAdd() {
        service.showAdd();
    }

    @Test
    void getLang() {
        Mockito.when(mainCtrl.getLocale()).thenReturn(new Locale("en"));
        assertEquals(service.getLang(), "en");
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

    @Test
    void writeToConfig() {
        service.writeToConfig(new Event("Test"));
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
    void isEventInConfig() {
        Event e = new Event("Mike");
        Mockito.when(mainCtrl.isEventInConfig(e)).thenReturn(true);
        assertEquals(service.isEventInConfig(e), true);
    }

    @Test
    void deleteFromConfig() {
        service.deleteFromConfig(new Event("Test"));
    }

    @Test
    void showLanguageSwitch() {
        service.showLanguageSwitch('s');
    }
}
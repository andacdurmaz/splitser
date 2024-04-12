package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AddOrEditParticipantServiceTest {

    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;

    private AddOrEditParticipantService service;
    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new AddOrEditParticipantService(server, mainCtrl);
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
    void update() {
        service.updateAndShow(new Event("Test"));
    }

    @Test
    void updateUser() {

        User u = new User("Mike", "ox@long.com");
        Mockito.when(server.addUser(u)).thenReturn(u);
        User user = service.addUser(u);
        assertEquals(user.getEmail(), "ox@long.com");

        u.setEmail( "ox@long.com");
        service.updateUser(u);
    }

    @Test
    void addUser() {
        User u = new User("Mike", "ox@long.com");
        Mockito.when(server.addUser(u)).thenReturn(u);
        User user = service.addUser(u);
        assertEquals(user.getEmail(), "ox@long.com");
    }
}
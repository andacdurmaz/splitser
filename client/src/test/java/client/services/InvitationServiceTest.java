package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class InvitationServiceTest {


    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;
    private InvitationService service;
    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new InvitationService(server, mainCtrl);
    }


    @Test
    void showEventInfo() {
        service.showEventInfo(new Event("Test"));
    }
}
package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


class LoginServiceTest {
   @Mock
    private ServerUtils server;
   @Mock
    private MainCtrl mainCtrl;
    private LoginService service;

    @BeforeEach
    @Inject
    void setup() {
        this.server = Mockito.mock(ServerUtils.class);
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        service = new LoginService(server, mainCtrl);
    }


    @Test
    void connectSuccess() {
        Mockito.when(server.checkCredentials("777")).thenReturn(200);
        boolean b = service.connect("777");
        Assertions.assertEquals(b, true);
    }

    @Test
    void connectFail() {
        Mockito.when(server.checkCredentials("42")).thenReturn(400);
        boolean b = service.connect("42");
        Assertions.assertEquals(b, false);
    }

    @Test
    void connectBad() {
        Mockito.when(server.checkCredentials("E")).thenReturn(400);
        boolean b = service.connect("E");
        Assertions.assertEquals(b, false);
    }
}
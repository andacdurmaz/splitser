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

    private ServerUtilsImpl server;
   @Mock
    private MainCtrl mainCtrl;
    private LoginService service;

    @BeforeEach
    @Inject
    void setup() {
        this.server = new ServerUtilsImpl();
        this.mainCtrl = Mockito.mock(MainCtrl.class);
        service = new LoginService(server, mainCtrl);
    }


    @Test
    void connectSuccess() {
        boolean b = service.connect("777");
        Assertions.assertEquals(b, true);
    }

    @Test
    void connectFail() {
        boolean b = service.connect("42");
        Assertions.assertEquals(b, false);
    }

    @Test
    void connectBad() {
        boolean b = service.connect("E");
        Assertions.assertEquals(b, false);
    }

    @Test
    void setSession() {
        service.setSession();
    }

    @Test
    void Menu() {
        service.returnToMenu();
    }

    @Test
    void login() {
        service.login();
    }
}
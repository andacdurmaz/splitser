package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Enumeration;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class LanguageSwitchServiceTest {

    @Mock
    private MainCtrl mainCtrl;
    @Mock
    private ServerUtils server;
    private LanguageSwitchService service;
    @BeforeEach
    void setUp() {

        mainCtrl = Mockito.mock(MainCtrl.class);
        server = Mockito.mock(ServerUtils.class);
        service = new LanguageSwitchService(server, mainCtrl);
    }

    @Test
    void showStartPage() {
        service.showStartPage();
    }

    @Test
    void setLocale() {
        service.setLocale("en");
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
package client.scenes;

import client.Main;
import client.services.AdminOverviewService;
import client.utils.ServerUtils;
import com.google.inject.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URL;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class AdminOverviewTest{
    private MainCtrl sut;
    private AdminOverviewCtrl adminOverviewCtrl;

    /**
     * BeforeEach method
     */
    @BeforeEach
    public void setup() {
        sut = new MainCtrl();
        adminOverviewCtrl = new AdminOverviewCtrl(new AdminOverviewService( new ServerUtils(), sut));
    }

}

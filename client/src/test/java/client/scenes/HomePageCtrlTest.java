package client.scenes;
import client.utils.ServerUtils;
//import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
//import java.net.URL;
//import java.util.ResourceBundle;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;

public class HomePageCtrlTest {

    @Mock
    private ListView<String> eventsListMock;
    @Mock
    private Label emptyLabelMock;
    @Mock
    private ObservableList<String> observableListMock;
    @Mock
    private ServerUtils serverMock;

    @Mock
    private MainCtrl mainCtrlMock;

    @InjectMocks
    private HomePageCtrl homePageCtrlMock;

//    @BeforeEach
//    public void setUp() {
//        URL mockUrl = mock(URL.class);
//        ResourceBundle mockResource = mock(ResourceBundle.class);
//        homePageCtrlMock.initialize(mockUrl, mockResource);
//    }
//
//    /**
//     * test for constructor
//     */
//    @Test
//    public void constructorTest() {
//        assertSame(serverMock, homePageCtrlMock.getServer());
//        assertSame(mainCtrlMock, homePageCtrlMock.getMainCtrl());
//    }
//
//    /**
//     * test to see if the description of creating or adding an event is visible
//     */
//    @Test
//    public void updateLabelVisibilityEmptyTest() {
//        homePageCtrlMock.initialize(null,null);
//        assertTrue(emptyLabelMock.isVisible());
//    }
//
//    /**
//     * test to see if the description is invisible due to an existence of event
//     */
//    @Test
//    public void updateLabelVisibilityNotEmptyTest() {
//        ObservableList<String> nonEmptyList = FXCollections.observableArrayList("Item 1");
//        eventsListMock.setItems(nonEmptyList);
//        homePageCtrlMock.initialize(null,null);
//        assertFalse(emptyLabelMock.isVisible());
//    }

}


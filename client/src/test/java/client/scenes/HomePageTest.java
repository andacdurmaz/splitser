package client.scenes;
import client.utils.ServerUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class HomePageTest{

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
    private HomePage homePageMock;

    @BeforeEach
    public void setUp() {
    }

    /**
     * test for constructor
     */
    @Test
    public void constructorTest() {
        assertSame(serverMock, homePageMock.getServer());
        assertSame(mainCtrlMock, homePageMock.getMainCtrl());
    }

    /**
     * test to see if the description of creating or adding an event is visible
     */
    @Test
    public void updateLabelVisibilityEmptyTest() {
        homePageMock.initialize(null,null);
        assertTrue(emptyLabelMock.isVisible());
    }

    /**
     * test to see if the description is invisible due to an existence of event
     */
    @Test
    public void updateLabelVisibilityNotEmptyTest() {
        ObservableList<String> nonEmptyList = FXCollections.observableArrayList("Item 1");
        eventsListMock.setItems(nonEmptyList);
        homePageMock.initialize(null,null);
        assertFalse(emptyLabelMock.isVisible());
    }

}

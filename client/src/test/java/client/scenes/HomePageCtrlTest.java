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
    private EventOverviewCtrl eventOverviewCtrlMock;


}


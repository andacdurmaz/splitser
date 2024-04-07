package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventOverviewService {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    @Inject
    public EventOverviewService(ServerUtils server, MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;
    }

    public ServerUtils getServer(){
        return this.server;
    }

    public void showEventInfo(Event selectedEvent) {
        mainCtrl.showEventInfo(selectedEvent);
    }

    public void showAdd() {
        mainCtrl.showAdd();
    }

    public void login() {
        mainCtrl.login();
    }

    public List<Event> getEvents(){
        return server.getEvents();
    }
}


package client.scenes;
import client.services.InvitationService;
import com.google.inject.Inject;
import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;



public class InvitationCtrl {

    private final InvitationService service;

    private Event event;

    @FXML
    private Label eventTitle;
    @FXML
    private Label eventCode;


    /**
     * Adding javadoc for checkstyle
     *
     * @param service service
     * @param event event
     */
    @Inject
    public InvitationCtrl(InvitationService service, Event event) {
        this.service = service;
        this.event = event;

    }


    /**
     * set the event title label
     * @param event
     */
    public void updateEventTitle(Event event) {
        if (event != null || event.getTitle().length() != 0)
            eventTitle.setText(event.getTitle());
    }

    /**
     * set the event code label
     * @param event
     */
    public void updateEventCode(Event event) {
        if (event != null || event.getTitle().length() != 0)
        {
            String code = Long.toString(event.getEventCode());
            eventCode.setText(code);
        }

    }

    /**
     * set the event title and code
     * @param event
     */
    public void setData(Event event) {
        updateEventTitle(event);
        updateEventCode(event);
    }


    /**
     * set the event
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     *  return back to event info
     */
    public void cancel() {
//        clearFields();
        service.showEventInfo(event);
    }


}
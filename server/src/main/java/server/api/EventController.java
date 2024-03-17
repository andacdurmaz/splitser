package server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {

    /**
     * The event service
     */
    private final EventService eventService;

    /**
     * Constructor for the event controller
     * @param eventService
     */
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Method to get all events
     * @return all events
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllEvents() {
        return eventService.getAllEvents().toString();
    }

    /**
     * Method to get an event by its id
     * @param id of the event
     * @return the event
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEventById(@PathVariable long id) {
        return eventService.getEventById(id).toString();
    }

    /**
     * Method to get the title of an event by its id
     * @param id of the event
     * @return the title of the event
     */
    @RequestMapping(value = "/{id}/title", method = RequestMethod.GET)
    public String getEventTitleById(@PathVariable long id) {
        return eventService.getEventTitleById(id);
    }

    /**
     * Method to get the creator of an event by its id
     * @param id of the event
     * @return the creator of the event
     */
    @RequestMapping(value = "/{id}/creator", method = RequestMethod.GET)
    public String getCreatorById(@PathVariable long id) {
        return eventService.getCreatorById(id).toString();
    }

    /**
     * Method to get the expenses of an event by its id
     * @param id of the event
     * @return the expenses of the event
     */
    @RequestMapping(value = "/{id}/expenses", method = RequestMethod.GET)
    public String getExpensesByEventId(@PathVariable long id) {
        return eventService.getExpensesByEventId(id).toString();
    }

    /**
     * Method to get the description of an event by its id
     * @param id of the event
     * @return the description of the event
     */
    @RequestMapping(value = "/{id}/description", method = RequestMethod.GET)
    public String getDescriptionByEventId(@PathVariable long id) {
        return eventService.getDescriptionByEventId(id);
    }
}

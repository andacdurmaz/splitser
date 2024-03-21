package server.api;

import commons.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.EventService;

@RestController
@RequestMapping("/api/event")
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
    @GetMapping(value = "/all")
    public String getAllEvents() {
        return eventService.getAllEvents().toString();
    }

    /**
     * Method to get an event by its id
     * @param id of the event
     * @return the event
     */
    @GetMapping(value = "/{id}")
    public String getEventById(@PathVariable long id) {
        return eventService.getEventById(id).toString();
    }

    /**
     * Method to get the title of an event by its id
     * @param id of the event
     * @return the title of the event
     */
    @GetMapping(value = "/{id}/title")
    public String getEventTitleById(@PathVariable long id) {
        return eventService.getEventTitleById(id);
    }

    /**
     * Method to get the creator of an event by its id
     * @param id of the event
     * @return the creator of the event
     */
    @GetMapping(value = "/{id}/creator")
    public String getCreatorById(@PathVariable long id) {
        return eventService.getCreatorById(id).toString();
    }

    /**
     * Method to get the expenses of an event by its id
     * @param id of the event
     * @return the expenses of the event
     */
    @GetMapping(value = "/{id}/expenses")
    public String getExpensesByEventId(@PathVariable long id) {
        return eventService.getExpensesByEventId(id).toString();
    }

    /**
     * Method to get the description of an event by its id
     * @param id of the event
     * @return the description of the event
     */
    @GetMapping(value = "/{id}/description")
    public String getDescriptionByEventId(@PathVariable long id) {
        return eventService.getDescriptionByEventId(id);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Event> addEvent(@RequestBody Event e) {
        return eventService.addEvent(e);
    }
}

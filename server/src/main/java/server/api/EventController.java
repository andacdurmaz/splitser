package server.api;

import commons.Event;
import commons.Expense;
import commons.User;
import commons.exceptions.NoSuchEventException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.service.EventService;
import java.util.List;

@RestController
@RequestMapping("/api/events")
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
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Method to get an event by its id
     * @param id of the event
     * @return the event
     */
    @GetMapping(value = "/{id}")
    public Event getEventById(@PathVariable long id) {
        return eventService.getEventById(id);
    }

    /**
     * Method to get the title of an event by its id
     * @param id of the event
     * @return the title of the event
     */
    @GetMapping(value = "/{id}/title")
    public ResponseEntity<String> getEventTitleById(@PathVariable long id) {
        return eventService.getEventTitleById(id);
    }

    /**
     * Method to get the creator of an event by its id
     * @param id of the event
     * @return the creator of the event
     */
    @GetMapping(value = "/{id}/creator")
    public User getCreatorById(@PathVariable long id) {
        return eventService.getCreatorById(id);
    }

    /**
     * Method to get the expenses of an event by its id
     * @param id of the event
     * @return the expenses of the event
     */
    @GetMapping(value = "/{id}/expenses")
    public List<Expense> getExpensesByEventId(@PathVariable long id) {
        return eventService.getExpensesByEventId(id);
    }

    /**
     * Method to get the description of an event by its id
     * @param id of the event
     * @return the description of the event
     */
    @GetMapping(value = "/{id}/description")
    public ResponseEntity<String> getDescriptionByEventId(@PathVariable long id) {
        return eventService.getDescriptionByEventId(id);
    }

    /**
     * Method to add an event
     * @param e the event to add
     * @return the added event
     */
    @PostMapping(value = "/add")
    public ResponseEntity<Event> addEvent(@RequestBody Event e) {
        return eventService.addEvent(e);
    }


    /**
     * Updates the name of an existing event with the given id
     * to be renamed to the given newName
     * @param id event id
     * @param newName the new name of the event
     * @return event with the changed name or bad request
     */
    @PutMapping("/{id}/name")
    public ResponseEntity<Event> updateEventName(@PathVariable("id") long id,
                                                     @RequestParam("name") String newName) {
        try {
            Event newNamedEvent = eventService.updateEventName(id, newName);
            return ResponseEntity.ok(newNamedEvent);
        } catch (NoSuchEventException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}

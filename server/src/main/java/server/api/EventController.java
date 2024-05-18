package server.api;

import commons.Event;
import commons.Expense;
import commons.ExpenseTag;
import commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
     *
     * @param eventService g
     */
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Method to get all events
     *
     * @return all events
     */
    @GetMapping(value = "/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Method to get an event by its id
     *
     * @param id of the event
     * @return the event
     */
    @GetMapping(value = "/{id}")
    public Event getEventById(@PathVariable long id) {
        if(eventService.existsById(id))
            return eventService.getEventById(id);
        return new Event("MISSING");
    }

    /**
     * Method to get the title of an event by its id
     *
     * @param id of the event
     * @return the title of the event
     */
    @GetMapping(value = "/{id}/title")
    public ResponseEntity<String> getEventTitleById(@PathVariable long id) {
        return eventService.getEventTitleById(id);
    }

    /**
     * Method to get the creator of an event by its id
     *
     * @param id of the event
     * @return the creator of the event
     */
    @GetMapping(value = "/{id}/creator")
    public User getCreatorById(@PathVariable long id) {
        return eventService.getCreatorById(id);
    }

    /**
     * Method to get the expenses of an event by its id
     *
     * @param id of the event
     * @return the expenses of the event
     */
    @GetMapping(value = "/{id}/expenses")
    public List<Expense> getExpensesByEventId(@PathVariable long id) {
        return eventService.getExpensesByEventId(id);
    }


    /**
     * Method to get the participants of an event by its id
     *
     * @param id of the event
     * @return the participants of the event
     */
    @GetMapping(value = "/{id}/participants")
    public List<User> getParticipantsByEventId(@PathVariable long id) {
        return eventService.getParticipantsByEventId(id);
    }

    /**
     * Method to get the expense tags of an event by its id
     * @param id
     * @return the expense tags of the event
     */
    @GetMapping(value = "/{id}/tags")
    public List<ExpenseTag> getExpenseTagsByEventId(@PathVariable long id) {
        return eventService.getExpenseTagsByEventId(id);
    }

    /**
     * Method to get the description of an event by its id
     *
     * @param id of the event
     * @return the description of the event
     */
    @GetMapping(value = "/{id}/description")
    public ResponseEntity<String> getDescriptionByEventId(@PathVariable long id) {
        return eventService.getDescriptionByEventId(id);
    }




    /**
     * Method to add an event
     *
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
     *
     * @param id    event id
     * @param event event to update
     * @return the updated event or bad request
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") long id,
                                             @RequestBody Event event) {
        if (!eventService.existsById(id)) {
            return ResponseEntity.status(404).build();
        }

        eventService.updateEvent(event);
        return ResponseEntity.ok(event);
    }

    /**
     * Deletes a event by id.
     * @param id the events id
     * @return the response entity
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") long id) {
        return eventService.deleteEvent(id);
    }

    /**
     * Gets the share per person for this event,
     * divides the total sum of expenses by the number of participants
     * @param id id of event
     * @return double share per person
     */
    @GetMapping("/sharePerPerson")
    public Double getSharePerPerson(@PathVariable("id") long id) {
        return eventService.sharePerPerson(id);
    }
    /**
     *
     * @param e new version of updated event
     * @return a notification to clients so that they refresh
     */
    @MessageMapping("/event")
    @SendTo("/updates/events")
    public Event eventUpdate(Event e){
        Event newEvent = updateEvent(e.getId(), e).getBody();
        return newEvent;
    }
}

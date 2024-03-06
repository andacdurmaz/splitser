package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllEvents() {
        return eventService.getAllEvents().toString();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEventById(@PathVariable long id) {
        return eventService.getEventById(id).toString();
    }

    @RequestMapping(value = "/{id}/title", method = RequestMethod.GET)
    public String getEventTitleById(@PathVariable long id) {
        return eventService.getEventTitleById(id);
    }

    @RequestMapping(value = "/{id}/creator", method = RequestMethod.GET)
    public String getCreatorById(@PathVariable long id) {
        return eventService.getCreatorById(id).toString();
    }

    @RequestMapping(value = "/{id}/expenses", method = RequestMethod.GET)
    public String getExpensesByEventId(@PathVariable long id) {
        return eventService.getExpensesByEventId(id).toString();
    }

    @RequestMapping(value = "/{id}/description", method = RequestMethod.GET)
    public String getDescriptionByEventId(@PathVariable long id) {
        return eventService.getDescriptionByEventId(id);
    }
}

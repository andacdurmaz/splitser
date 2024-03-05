package server.database;

import commons.Event;
import commons.Expense;
import commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    /**
     * The repository for the event
     */
    private final EventRepository eventRepository;

    /**
     * Constructor for the event service
     * @param eventRepository
     */

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Method to get all events from the database
     * @return the list of events
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Method to get an event by its id
     * @param id of the event
     * @return the event
     */
    public Event getEventById(long id) {
        return eventRepository.getEventById(id);
    }

    /**
     * Method to get the title of an event by its id
     * commented out as it is not really needed and the speed increase is not that noticable compared to getEventById().getTitle()
     * @param id of the event
     * @return the title of the event
     */
     public String getEventTitleById(long id) {
         return eventRepository.getEventTitleById(id);
     }

    /**
     * Method to get the creator of an event by its id
     * @param id of the event
     * @return the creator of the event
     */
    public User getCreatorById(long id) {
        return eventRepository.getCreatorById(id);
    }

    /**
     * Method to get the expenses by the id of the event
     * @param id of the event
     * @return the list of expenses
     */
    public List<Expense> getExpensesByEventId(long id) {
        return eventRepository.getExpensesById(id);
    }

    /**
     * Method to get the description of an event by its id
     * @param id of the event
     * @return the description of the event
     */
    public String getDescriptionByEventId(long id) {
        return eventRepository.getDescriptionById(id);
    }
}

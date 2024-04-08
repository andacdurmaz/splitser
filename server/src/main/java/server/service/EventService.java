package server.service;

import commons.Event;
import commons.Expense;
import commons.ExpenseTag;
import commons.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.database.EventRepository;

import java.util.List;

@Service
public class EventService {

    /**
     * The repository for the event
     */
    private final EventRepository eventRepository;

    /**
     * Constructor for the event service
     *
     * @param eventRepository eventRepository
     */

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    /**
     * Method to get all events from the database
     *
     * @return the list of events
     */
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    /**
     * Checks if the event with the given id exists
     *
     * @param id event id
     * @return true if the event with the given id exists, false otherwise
     */
    public Boolean existsById(long id) {
        return eventRepository.existsById(id);
    }

    /**
     * Checks if the event with the given code exists
     *
     * @param eventCode event code
     * @return true if the event with the given code exists, false otherwise
     */
//    public Boolean existsByEventCode(long eventCode) {
//        return eventRepository.existsByEventCode(eventCode);
//    }

    /**
     * Method to get an event by its id
     *
     * @param id of the event
     * @return the event
     */
    public Event getEventById(long id) {
        return eventRepository.getEventById(id);
    }

//    public Event getEventByEventCode(long id) {
//        return eventRepository.getEventByEventCode(id);
//    }
//
//    public boolean existsByEventCode(long eventcode) {
//        return eventRepository.existsByEventCode(eventcode);
//    }


    /**
     * Method to get the title of an event by its id
     * commented out as it is not really needed and the speed increase is not
     * that noticeable compared to getEventById().getTitle()
     *
     * @param id of the event
     * @return the title of the event
     */
    public ResponseEntity<String> getEventTitleById(long id) {
        return eventRepository.getEventTitleById(id);
    }

    /**
     * Method to get the creator of an event by its id
     *
     * @param id of the event
     * @return the creator of the event
     */
    public User getCreatorById(long id) {
        return eventRepository.getCreatorById(id);
    }

    /**
     * Method to get the expenses by the id of the event
     *
     * @param id of the event
     * @return the list of expenses
     */
    public List<Expense> getExpensesByEventId(long id) {
        return eventRepository.getExpensesById(id);
    }


    /**
     * Method to get the participants by the id of the event
     *
     * @param id of the event
     * @return the list of participants
     */
    public List<User> getParticipantsByEventId(long id) {
        return eventRepository.getParticipantsById(id);
    }

    /**
     * expense tags
     * @param id
     * @return expense tags
     */
    public List<ExpenseTag> getExpenseTagsByEventId(long id) {
        return eventRepository.getExpenseTagsById(id);
    }

    /**
     * Method to get the description of an event by its id
     *
     * @param id of the event
     * @return the description of the event
     */
    public ResponseEntity<String> getDescriptionByEventId(long id) {
        return eventRepository.getDescriptionById(id);
    }

    /**
     * Method to add an event to the database
     *
     * @param e the event to be added
     * @return the response entity
     */
    public ResponseEntity<Event> addEvent(Event e) {
        eventRepository.save(e);
        return ResponseEntity.ok(e);
    }

    /**
     * Updates the name of the event with the given id to be the given name
     *
     * @param oldEvent Event's body
     * @return the updated event
     */
    public Event updateEvent(Event oldEvent) {
        return eventRepository.save(oldEvent);
    }

    /**
     * Method to delete a event id
     * @param event id of a event to be deleted
     * @return the response entity
     */
    public ResponseEntity<Event> deleteEvent(long event) {
        if (!existsById(event)) {
            return ResponseEntity.badRequest().build();
        }
        eventRepository.deleteById(event);
        return ResponseEntity.ok().build();
    }

    /**
     * Calculates the share per person for the event
     * @param id event id
     * @return double share per person
     */
    public double sharePerPerson(long id) {
        double total = totalSumOfAllExpenses(id);
        int people = eventRepository.getEventById(id).getParticipants().size();
        return total / people;
    }

    /**
     * calculates the total sum of all expenses for the event
     * @param id event id
     * @return double total sum
     */
    public double totalSumOfAllExpenses(long id) {
        List<Expense> allExpenses = eventRepository.getEventById(id).getExpenses();
        double sum = 0;
        for (Expense e : allExpenses) {
            sum += e.getAmount();
        }
        return sum;
    }
}

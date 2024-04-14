package server.api;


import commons.Expense;
import commons.User;
import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import commons.Event;
import org.springframework.http.ResponseEntity;
import server.database.EventRepository;
import server.service.EventService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Test
    public void testGetAllEvents() {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.findAll()).thenReturn(List.of(event));
        List<Event> events = eventService.getAllEvents();
        assertEquals(1, events.size());
    }

    @Test
    public void testGetEventById() {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getEventById(1)).thenReturn(event);
        Event foundEvent = eventService.getEventById(1);
        assertEquals(event, foundEvent);
    }



    @Test
    public void testGetEventTitleById() {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getEventTitleById(1)).thenReturn((ResponseEntity<String>) ResponseEntity.ok("title"));
        String title = String.valueOf(eventService.getEventTitleById(1));
        assertEquals("<200 OK OK,title,[]>", title);
    }

    @Test
    public void testGetCreatorById() throws EmailFormatException, IBANFormatException, BICFormatException {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);
        when(eventRepository.getCreatorById(1)).thenReturn(new User("username", "username@gmail.com",
                "GB94BARC10201530093459","RABONL2UXXX"));
        User creator = eventService.getCreatorById(1);
        assertEquals(new User("username", "username@gmail.com",
                "GB94BARC10201530093459","RABONL2UXXX"), creator);
    }

    @Test
    public void testGetExpensesById() {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getExpensesById(1)).thenReturn(new ArrayList<>());
        List<Expense> expenses = eventService.getExpensesByEventId(1);
        assertEquals(0, expenses.size());
    }

    @Test
    public void testGetParticipantsById() {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getParticipantsById(1)).thenReturn(new ArrayList<>());
        List<User> participants = eventService.getParticipantsByEventId(1);
        assertEquals(0, participants.size());
    }

    @Test
    public void testGetDescriptionByEventId() {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getDescriptionById(1)).thenReturn((ResponseEntity<String>) ResponseEntity.ok("Description"));
        String description = String.valueOf(eventService.getDescriptionByEventId(1));
        assertEquals("<200 OK OK,Description,[]>", description);
    }

    @Test
    public void testGetAllEventsEmpty() {
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.findAll()).thenReturn(new ArrayList<>());
        List<Event> events = eventService.getAllEvents();
        assertEquals(0, events.size());
    }

    @Test
    public void testGetEventByIdNull() {
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getEventById(1)).thenReturn(null);
        Event foundEvent = eventService.getEventById(1);
        assertNull(foundEvent);
    }

    @Test
    public void testGetEventTitleByIdNull() {
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getEventTitleById(1)).thenReturn(null);
        assertNull(eventService.getEventTitleById(1));
    }

    @Test
    public void testGetCreatorByIdNull() {
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getCreatorById(1)).thenReturn(null);
        User creator = eventService.getCreatorById(1);
        assertNull(creator);
    }

    @Test
    public void testGetExpensesByIdNull() {
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getExpensesById(1)).thenReturn(null);
        List<Expense> expenses = eventService.getExpensesByEventId(1);
        assertNull(expenses);
    }

    @Test
    public void testGetDescriptionByEventIdNull() {
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getDescriptionById(1)).thenReturn(null);
        assertNull(eventService.getDescriptionByEventId(1));
    }

    @Test
    public void testAddEvent() {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.save(event)).thenReturn(event);
        ResponseEntity<Event> savedEvent = eventService.addEvent(event);
        assertEquals(event, savedEvent.getBody());
    }
}

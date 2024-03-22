package server.api;


import commons.Expense;
import commons.User;
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
    public void testGetCreatorById() {
        Event event = new Event("Title", 4, "Description");
        EventRepository eventRepository = mock(EventRepository.class);
        EventService eventService = new EventService(eventRepository);

        when(eventRepository.getCreatorById(1)).thenReturn(new User("username", "password"));
        User creator = eventService.getCreatorById(1);
        assertEquals(new User("username", "password"), creator);
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
}

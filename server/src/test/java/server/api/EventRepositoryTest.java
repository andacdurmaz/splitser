package server.api;

import commons.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import server.database.EventRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;
//
//    @Test
//    public void testGetEventById() {
//        Event event = new Event("Title", 4, 1234, "Description");
//        Event savedEvent = eventRepository.save(event);
//        Event foundEvent = eventRepository.getEventById(savedEvent.getId());
//        assertEquals(event, foundEvent);
//    }
}

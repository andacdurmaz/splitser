package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    void fullConstructorTest() {
        Event event = new Event("test", 3, "123456abc");
        assertEquals("test", event.getTitle());
        assertEquals(3, event.getAmountOfParticipants());
        assertEquals("123456abc", event.getEventCode());
    }

    @Test
    void partialConstructorTest() {
        Event event = new Event("test", 3);
        assertEquals("test", event.getTitle());
        assertEquals(3, event.getAmountOfParticipants());
    }

    @Test
    void testTitleSetter() {
        Event event = new Event("test", 3, "123456abc");
        event.setTitle("newTitle");
        assertEquals("newTitle", event.getTitle());
    }

    @Test
    void testAmountOfParticipantsSetter() {
        Event event = new Event("test", 3, "123456abc");
        event.setAmountOfParticipants(4);
        assertEquals(4, event.getAmountOfParticipants());
    }
    
}
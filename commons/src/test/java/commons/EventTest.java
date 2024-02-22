package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    void fullConstructorTest() {
        Event event = new Event("test", 3, "123456abc");
        assertEquals("test", event.getTitle());
        assertEquals(3, event.getAmountOfParticipants());
        assertEquals("123456abc", event.getEventCode());
        assertEquals(0, event.getNumberOfExpenses());
        assertEquals(0, event.getExpenses().size());
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

    @Test
    void testAddExpenses() {
        List<Object> list = new ArrayList<>();
        list.add("hello");
        Event event = new Event("test", 3, "123456abc");
        event.addExpense("hello");
        assertEquals(list, event.getExpenses());
        assertEquals(1, event.getNumberOfExpenses());
    }

    @Test
    void testRemoveExpenses() throws Exception {
        Event event = new Event("test", 3, "123456abc");
        event.addExpense("hello");
        event.addExpense("test");
        List<Object> list = new ArrayList<>();
        list.add("hello");
        assertEquals("test", event.removeExpense("test"));
        assertEquals(list, event.getExpenses());
    }

    @Test
    void testExpenseSetter() throws Exception {
        Event event = new Event("test", 3, "123456abc");
        event.addExpense("hello");
        event.addExpense("test");
        List<Object> list = new ArrayList<>();
        list.add("hello");
        list.add("newTest");
        assertEquals("newTest", event.setExpense("test", "newTest"));
        assertEquals(list, event.getExpenses());
    }
}
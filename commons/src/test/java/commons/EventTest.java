package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    void fullConstructorTest() {
        Event event = new Event("test", 3, 123456, "this is for a test");
        assertEquals("test", event.getTitle());
        assertEquals(3, event.getAmountOfParticipants());
        assertEquals(123456, event.getEventCode());
        assertEquals(0, event.getExpenses().size());
        assertEquals(0, event.getExpenses().size());
        assertEquals("this is for a test", event.getDescription());
    }

    @Test
    void partialConstructorTest() {
        Event event = new Event("test", 3);
        assertEquals("test", event.getTitle());
        assertEquals(3, event.getAmountOfParticipants());
    }

    @Test
    void testTitleSetter() {
        Event event = new Event("test", 3, 123456, "this is for a test");
        event.setTitle("newTitle");
        assertEquals("newTitle", event.getTitle());
    }

    @Test
    void testAmountOfParticipantsSetter() {
        Event event = new Event("test", 3, 123456, "this is for a test");
        event.setAmountOfParticipants(4);
        assertEquals(4, event.getAmountOfParticipants());
    }

    @Test
    void testAddExpenses() {
        List<Expense> list = new ArrayList<>();
        list.add(new Expense("Drinks",3.29));
        Event event = new Event("test", 3, 123456, "this is for a test");
        event.addExpense(new Expense("Drinks",3.29));
        assertEquals(list, event.getExpenses());
        assertEquals(1, event.getExpenses().size());
    }

    @Test
    void testRemoveExpenses() throws Exception {
        Event event = new Event("test", 3, 123456, "this is for a test");
        Expense expense1 = new Expense("Drinks",3.29);
        Expense expense2 = new Expense("Food",15);
        event.addExpense(expense1);
        event.addExpense(expense2);

        List<Expense> list = new ArrayList<>();
        list.add(new Expense("Drinks",3.29));
        assertTrue(event.removeExpense(expense2));
        assertEquals(list, event.getExpenses());
    }

    @Test
    void testExpenseSetter() throws Exception {
        Event event = new Event("test", 3, 123456, "this is for a test");
        Expense expense1 = new Expense("Drinks",3.29);
        Expense expense2 = new Expense("Food",15);
        event.addExpense(expense1);
        event.addExpense(expense2);

        List<Object> list = new ArrayList<>();
        Expense expense3 = new Expense("More vodka",35);
        list.add(expense1);
        list.add(expense3);
        assertEquals(expense3, event.setExpense(expense2, expense3));
        assertEquals(list, event.getExpenses());
    }

    @Test
    void testDescriptionSetter() {
        Event event = new Event("test", 3, 123456, "this is for a test");
        event.setDescription("This is a new description");
        assertEquals("This is a new description", event.getDescription());
    }
}
package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    @Test
    void fullConstructorTest() {
        Event event = new Event("test", 3, "this is for a test");
        assertEquals("test", event.getTitle());
        assertEquals(3, event.getAmountOfParticipants());
        assertEquals(0, event.getExpenses().size());
        assertEquals(0, event.getExpenses().size());
        assertEquals("this is for a test", event.getDescription());
        assertEquals(0, event.getSumOfExpenses());
        assertEquals(0, event.getEventCode());
        assertEquals(0, event.getId());
    }

    @Test
    void partialConstructorTest() {
        Event event = new Event("test", 3);
        assertEquals("test", event.getTitle());
        assertEquals(3, event.getAmountOfParticipants());
    }

    @Test
    void testEmptyConstructor() {
        Event event = new Event();
        assertEquals(null, event.getTitle());
        assertEquals(0, event.getAmountOfParticipants());
        assertEquals(0, event.getExpenses().size());
        assertEquals(null, event.getDescription());
        assertEquals(0, event.getSumOfExpenses());
        assertEquals(0, event.getEventCode());
        assertEquals(0, event.getId());
    }

    @Test
    public void participantsTest(){
        Event event = new Event();
        User user = new User();
        assertTrue(event.getParticipants().isEmpty());
        List<User> participants = new ArrayList<>();
        participants.add(user);
        event.setParticipants(participants);
        assertEquals(participants, event.getParticipants());
    }
    @Test
    void testTitleSetter() {
        Event event = new Event("test", 3, "this is for a test");
        event.setTitle("newTitle");
        assertEquals("newTitle", event.getTitle());
    }

    @Test
    void testAmountOfParticipantsSetter() {
        Event event = new Event("test", 3, "this is for a test");
        event.setAmountOfParticipants(4);
        assertEquals(4, event.getAmountOfParticipants());
    }

    @Test
    void testAddExpenses() {
        List<Expense> list = new ArrayList<>();
        list.add(new Expense("Drinks",3.29));
        Event event = new Event("test", 3, "this is for a test");
        event.addExpense(new Expense("Drinks",3.29));
        assertEquals(list, event.getExpenses());
        assertEquals(1, event.getExpenses().size());
    }

    @Test
    void testRemoveExpenses() throws Exception {
        Event event = new Event("test", 3, "this is for a test");
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
        Event event = new Event("test", 3, "this is for a test");
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
    void testExpensesSum() throws Exception {
        Event event = new Event("test", 3, "this is for a test");
        Expense expense1 = new Expense("Drinks",3.29);
        Expense expense2 = new Expense("Food",15);
        event.addExpense(expense1);
        event.addExpense(expense2);

        assertEquals(18.29, event.getSumOfExpenses());
        event.removeExpense(expense1);
        assertEquals(15, event.getSumOfExpenses());
    }

    @Test
    void testDescriptionSetter() {
        Event event = new Event("test", 3, "this is for a test");
        event.setDescription("This is a new description");
        assertEquals("This is a new description", event.getDescription());
    }

    @Test
    void testRemoveExpenseException() {
        Event event = new Event("test", 3, "this is for a test");
        Expense expense1 = new Expense("Drinks",3.29);
        Expense expense2 = new Expense("Food",15);
        event.addExpense(expense1);
        assertThrows(Exception.class, () -> event.removeExpense(expense2));
    }

    @Test
    void testSetExpenseException() {
        Event event = new Event("test", 3, "this is for a test");
        Expense expense1 = new Expense("Drinks",3.29);
        Expense expense2 = new Expense("Food",15);
        event.addExpense(expense1);
        assertThrows(Exception.class, () -> event.setExpense(expense2, new Expense("More vodka",35)));
    }

    @Test
    void testGetEventCode() {
        Event event = new Event("test", 3, "this is for a test");
        assertEquals(0, event.getEventCode());
    }

    @Test
    void testSetId() {
        Event event = new Event("test", 3, "this is for a test");
        event.setId(1);
        assertEquals(1, event.getId());
    }

    @Test
    void testEqualsMethod(){
        Event event1 = new Event("test", 3, "this is for a test");
        Event event2 = new Event("test", 3, "this is for a test");
        Event event3 = new Event("test", 3, "this is for a test");
        event3.addExpense(new Expense("Drinks",3.29));
        event3.addExpense(new Expense("Food",15));
        Event event4 = new Event("test", 3, "this is for a test");
        event4.addExpense(new Expense("Drinks",3.29));
        assertEquals(event1, event2);
        assertEquals(event1, event3);
        assertEquals(event1, event4);
        assertEquals(event3, event4);
    }

    @Test
    void testHashCodeMethod(){
        Event event1 = new Event("test", 3, "this is for a test");
        Event event2 = new Event("test", 3, "this is for a test");
        Event event3 = new Event("test", 3, "this is for a test");
        event3.addExpense(new Expense("Drinks",3.29));
        event3.addExpense(new Expense("Food",15));
        Event event4 = new Event("test", 3, "this is for a test");
        event4.addExpense(new Expense("Drinks",3.29));
        assertEquals(event1.hashCode(), event2.hashCode());
        assertNotEquals(event1.hashCode(), event3.hashCode());
        assertNotEquals(event1.hashCode(), event4.hashCode());
        assertNotEquals(event3.hashCode(), event4.hashCode());
    }

    @Test
    void testToStringMethod(){
        Event event = new Event("test", 3, "this is for a test");
        assertEquals("{\"id\":0,\"eventCode\":0,\"title\":\"test\",\"amountOfParticipants\":3,\"expenses\":[],\"description\":\"this is for a test\",\"participants\":[],\"expenseTags\":[],\"sumOfExpenses\":0.0}",
                event.toString());
    }
}
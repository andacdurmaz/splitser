package commons;

import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {
    @Test
    void ConstructorTest(){
        User user = new User();
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.add(user);
        Expense expense = new Expense("Drinks", 3.29, user, payingParticipants);
        assertEquals("Drinks", expense.getName());
        assertEquals(3.29, expense.getAmount());
        assertEquals(payingParticipants, expense.getPayingParticipants());
        assertEquals(user, expense.getPayer());
        assertEquals(0, expense.getId());
    }

    @Test
    void PartialConstructorTest() {
        Expense expense = new Expense("Drinks", 3.29);
        assertEquals("Drinks", expense.getName());
        assertEquals(3.29, expense.getAmount());
    }

    @Test
    void emptyConstructorTest() {
        Expense expense = new Expense();
        assertEquals(null, expense.getName());
        assertEquals(0, expense.getAmount());
    }

    @Test
    void getName() {
        Expense expense = new Expense("Drinks", 3.29);
        assertEquals("Drinks", expense.getName());
    }

    @Test
    void setName() throws EmailFormatException, IBANFormatException, BICFormatException {
        User user = new User("ivan","ivan@email.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        Expense expense = new Expense("Drinks", 3.29);
        expense.setName("Food");
        assertEquals("Food",expense.getName());
    }



    @Test
    void testSetPayer() throws EmailFormatException, IBANFormatException, BICFormatException {
        User user = new User("ivan","ivan@email.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        Expense expense = new Expense("Drinks", 3.29);
        expense.setPayer(user);
        assertEquals(user,expense.getPayer());
    }

    @Test
    void testSetId() {
        Expense expense = new Expense("Drinks", 3.29);
        expense.setId(1);
        assertEquals(1,expense.getId());
    }

    @Test
    void getAmount() {
        Expense expense = new Expense("Drinks", 3.29);
        assertEquals(3.29, expense.getAmount());
    }

    @Test
    void setAmount() {
        Expense expense = new Expense("Drinks", 3.29);
        expense.setAmount(3);
        assertEquals(3,expense.getAmount());
    }

    @Test
    void getPayingParticipants() throws EmailFormatException, IBANFormatException, BICFormatException {
        User user = new User("ivan","ivan@email.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.add(user);
        Expense expense = new Expense("Drinks", 3.29, user, payingParticipants);
        assertEquals(payingParticipants, expense.getPayingParticipants());
    }

    @Test
    void setPayingParticipants() throws EmailFormatException, IBANFormatException, BICFormatException {
        User user = new User("ivan","ivan@email.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.add(user);
        Expense expense = new Expense("Drinks", 3.29);
        expense.setPayingParticipants(payingParticipants);
        assertEquals(payingParticipants, expense.getPayingParticipants());
    }

    @Test
    void testEquals() {
        Expense expense1 = new Expense("Drinks", 3.29);
        Expense expense2 = new Expense("Drinks", 3.29);
        assertEquals(expense1, expense2);
    }
    @Test
    void testNotEquals() {
        Expense expense1 = new Expense("Drinks", 3.29);
        Expense expense3 = new Expense("Food", 15);
        assertNotEquals(expense1, expense3);
    }

    @Test
    void testHashCode() {
        Expense expense1 = new Expense("Drinks", 3.29);
        Expense expense2 = new Expense("Drinks", 3.29);
        assertEquals(expense1.hashCode(), expense2.hashCode());
    }

    @Test
    void testNotEqualsHashCode() {
        Expense expense1 = new Expense("Drinks", 3.29);
        Expense expense3 = new Expense("Food", 15);
        assertNotEquals(expense1.hashCode(), expense3.hashCode());
    }
    @Test
    void testToString() {
        Expense expense = new Expense("Drinks", 3.29);
        assertEquals("Expense{name='Drinks', amount=3.29, payingParticipants=[]}", expense.toString());
    }
}
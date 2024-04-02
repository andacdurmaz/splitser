package commons;


import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void constructorTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        assertNotNull(user);
        assertEquals("andac", user.getUsername());
        assertEquals("andac@gmail.com", user.getEmail());
        assertEquals("iban", user.getIban());
        assertEquals("bic", user.getBic());
        assertEquals(Language.EN, user.getLanguage());
    }

    @Test
    public void defaultConstructorTest() {
        User user = new User();
        assertNotNull(user);
        assertNull( user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getLanguage());
    }

    @Test
    public void usernameTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        user.setUsername("mete");
        assertEquals("mete", user.getUsername());
    }
    @Test
    public void testID() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        user.setUserID(486);
        assertEquals(486, user.getUserID());
    }

    @Test
    public void emailTest() throws EmailFormatException {
        User user =new User("andac", "andac@gmail.com","iban", "bic");
        user.setEmail("andac72@hotmail.com");
        assertEquals("andac72@hotmail.com", user.getEmail());
    }



    @Test
    public void bicTest() throws BICFormatException {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        user.setBic("AAAA1212123");
        assertEquals("AAAA1212123", user.getBic());
    }

    @Test
    public void ibanTest() throws IBANFormatException {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        user.setIban("NL11112222333344445555666677778888");
        assertEquals("NL11112222333344445555666677778888", user.getIban());
    }

    @Test
    public void serverURLTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        assertNull(user.getServerURL());
        user.setServerURL("server.com");
        assertEquals("server.com", user.getServerURL());
    }

    @Test
    public void walletTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        assertEquals(0, user.getWallet());
        user.setWallet(10);
        assertEquals(10, user.getWallet());
    }



    @Test
    public void languageTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        user.setLanguage(Language.NL);
        assertEquals(Language.NL, user.getLanguage());
    }


    @Test
    public void debtTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        User user2 = new User("ivan", "ivan@gmail.com", "iban", "bic");
        Debt debt = new Debt(user, user2, 15.0);
        List<Debt> debts = new ArrayList<>();
        debts.add(debt);
        user.setDebts(debts);
        assertEquals(debts, user.getDebts());
    }


    @Test
    public void expenseTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        Expense expense = new Expense();
        List<Expense> expensess = new ArrayList<>();
        expensess.add(expense);
        user.setExpenses(expensess);
        assertEquals(expensess, user.getExpenses());
    }

    @Test
    public void addExpenseTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        List<Expense> expenses = new ArrayList<>();
        assertTrue(user.getExpenses().isEmpty());
        Expense expense = new Expense();
        user.addExpense(expense);
        expenses.add(expense);
        assertEquals(expenses, user.getExpenses());
    }




    @Test
    public void toStringTest() throws IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        user.setIban("NL11112222333344445555666677778888");
        user.setBic("AAAA1122333");
        user.setServerURL("server.123");
        String result = "User Information:\nUsername: andac\nE-mail: andac@gmail.com\nServer: server.123\n" +
                "IBAN: NL11112222333344445555666677778888\nBIC: AAAA1122333\nPreferred Language: EN\n";
        assertEquals(result, user.toString());
    }

    @Test
    public void equalsTestSame(){
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        assertTrue(user.equals(user));
    }

    @Test
    public void equalsTestDifferent(){
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        User user2 = new User("mehmet", "andac@gmail.com","iban", "bic");
        assertFalse(user.equals(user2));
    }
    @Test
    public void equalsNullTest(){
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        assertFalse(user.equals(null));
    }
    @Test
    public void IBANFormatTest() {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        assertThrows(IBANFormatException.class, () -> {        user.setIban("123");} );
    }

    @Test
    public void BICFormatTest()  {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        assertThrows(BICFormatException.class, () -> {        user.setBic("123");} );
    }

    @Test
    public void emailFormatTest()  {
        User user = new User("andac", "andac@gmail.com","iban", "bic");
        assertThrows(EmailFormatException.class, () -> {        user.setEmail("123");} );
    }
}

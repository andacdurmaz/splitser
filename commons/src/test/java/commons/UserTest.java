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
    public void constructorTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        assertNotNull(user);
        assertEquals("andac", user.getUsername());
        assertEquals("andac@gmail.com", user.getEmail());
        assertEquals("1234123412341234123412341234123412", user.getIban());
        assertEquals("12312312312", user.getBic());
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
    public void usernameTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        user.setUsername("mete");
        assertEquals("mete", user.getUsername());
    }
    @Test
    public void testID() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        user.setUserID(486);
        assertEquals(486, user.getUserID());
    }

    @Test
    public void emailTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user =new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        user.setEmail("andac72@hotmail.com");
        assertEquals("andac72@hotmail.com", user.getEmail());
    }



    @Test
    public void bicTest() throws BICFormatException,
            EmailFormatException, IBANFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        user.setBic("AAAA1212123");
        assertEquals("AAAA1212123", user.getBic());
    }

    @Test
    public void ibanTest() throws IBANFormatException,
            EmailFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        user.setIban("NL11112222333344445555666677778888");
        assertEquals("NL11112222333344445555666677778888", user.getIban());
    }

    @Test
    public void serverURLTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        assertNull(user.getServerURL());
        user.setServerURL("server.com");
        assertEquals("server.com", user.getServerURL());
    }

    @Test
    public void walletTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        assertEquals(0, user.getWallet());
        user.setWallet(10);
        assertEquals(10, user.getWallet());
    }



    @Test
    public void languageTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        user.setLanguage(Language.NL);
        assertEquals(Language.NL, user.getLanguage());
    }


    @Test
    public void debtTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        User user2 = new User("ivan", "ivan@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        Debt debt = new Debt(user, user2, 15.0);
        List<Debt> debts = new ArrayList<>();
        debts.add(debt);
        user.setDebts(debts);
        assertEquals(debts, user.getDebts());
    }


    @Test
    public void expenseTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        Expense expense = new Expense();
        List<Expense> expensess = new ArrayList<>();
        expensess.add(expense);
        user.setExpenses(expensess);
        assertEquals(expensess, user.getExpenses());
    }

    @Test
    public void addExpenseTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        List<Expense> expenses = new ArrayList<>();
        assertTrue(user.getExpenses().isEmpty());
        Expense expense = new Expense();
        user.addExpense(expense);
        expenses.add(expense);
        assertEquals(expenses, user.getExpenses());
    }




    @Test
    public void toStringTest() throws IBANFormatException,
            BICFormatException, EmailFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        user.setIban("NL11112222333344445555666677778888");
        user.setBic("AAAA1122333");
        user.setServerURL("server.123");
        String result = "User Information:\nUsername: andac\nE-mail: andac@gmail.com\nServer: server.123\n" +
                "IBAN: NL11112222333344445555666677778888\nBIC: AAAA1122333\nPreferred Language: EN\n";
        assertEquals(result, user.toString());
    }

    @Test
    public void equalsTestSame() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        assertTrue(user.equals(user));
    }

    @Test
    public void equalsTestDifferent() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        User user2 = new User("mehmet", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        assertFalse(user.equals(user2));
    }
    @Test
    public void equalsNullTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "1234123412341234123412341234123412",
                "12312312312");
        assertFalse(user.equals(null));
    }
    @Test
    public void setIBANFormatTest(){
        User user = new User();
        assertThrows(IBANFormatException.class, () -> {        user.setIban("123");} );
    }

    @Test
    public void setBICFormatTest() {
        User user = new User();
        assertThrows(BICFormatException.class, () -> {        user.setBic("123");} );
    }

    @Test
    public void setEmailFormatTest(){
        User user = new User();
        assertThrows(EmailFormatException.class, () -> {        user.setEmail("123");} );
    }
}

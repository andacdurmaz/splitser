package commons;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class DebtTest {
    @Test
    public void constructorTest() {
        User payer = new User("andac", "andac@gmail.com");
        User payee = new User("james", "james@gmail.com");
        Debt debt = new Debt(payer, payee, 5.0);
        assertNotNull(debt);
        assertEquals(payer, debt.getPayer());
        assertEquals(payee, debt.getPayee());
        assertEquals(5, debt.getAmount());
    }
    @Test
    public void emptyConstructorTest() {
        Debt debt = new Debt();
        assertNotNull(debt);
        assertEquals(null, debt.getPayer());
        assertEquals(null, debt.getPayee());
        assertEquals(null, debt.getAmount());
    }


    @Test
    public void payerTest(){
        Debt debt = new Debt();
        assertEquals(null, debt.getPayer());
        User payer = new User("andac", "andac@gmail.com");
        debt.setPayer(payer);
        assertEquals(payer, debt.getPayer());
    }

    @Test
    public void payeeTest(){
        Debt debt = new Debt();
        assertEquals(null, debt.getPayee());
        User payee = new User("andac", "andac@gmail.com");
        debt.setPayee(payee);
        assertEquals(payee, debt.getPayee());
    }

    @Test
    public void amountTest(){
        Debt debt = new Debt();
        assertEquals(null, debt.getAmount());
        debt.setAmount(2.0);
        assertEquals(2, debt.getAmount());
    }

    @Test
    public void idTest(){
        Debt debt = new Debt();
        debt.setId(254);
        assertEquals(254, debt.getId());
    }
}

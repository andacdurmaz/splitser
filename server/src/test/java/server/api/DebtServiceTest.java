package server.api;

import commons.Debt;
import commons.Event;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import org.junit.jupiter.api.Test;
import server.service.DebtService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DebtServiceTest {
    @Test
    public void constructorTest(){
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        assertNotNull(service);
        assertEquals(repo, service.getRepo());
    }

    @Test
    public void findAllTest(){
        DebtRepoTest repo = new DebtRepoTest();
        Debt debt1 = new Debt();
        debt1.setId(0);
        Debt debt2 = new Debt();
        debt1.setId(1);
        repo.save(debt2);
        repo.save(debt1);
        DebtService service = new DebtService(repo);
        List<Debt> debts = new ArrayList<>();
        debts.add(debt2);
        debts.add(debt1);
        assertEquals(debts, service.findAll());
    }

    @Test
    public void addDebtTest(){
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        service.addDebt(payer, payee, 15.0);
        List<Debt> debts = new ArrayList<>();
        debts.add(new Debt(payer, payee, 15.0));
        assertEquals(debts, service.findAll());
    }

    @Test
    public void settleDebtsCase1() throws NoDebtFoundException {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User("andac", "a@m.com");
        User payee = new User("mete", "m@m.com");
        Event event = new Event();

        Debt debt = new Debt(payer, payee, 15.0, event);
        Debt debt2 = new Debt(payer, payee, 15.0, event);
        service.settleDebt(debt);
        List<Debt> debts = new ArrayList<>();
        debts.add(debt);
        assertEquals(debts, service.findAll());

        service.settleDebt(debt2);
        List<Debt> debts2 = new ArrayList<>();
        debts2.add(new Debt(payer, payee, 30.0, event));
        assertEquals(debts2, service.findAll());
    }

    @Test
    public void settleDebtsCase2() throws NoDebtFoundException {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User("andac", "a@m.com");
        User payee = new User("mete", "m@m.com");
        Event event = new Event();

        Debt debt = new Debt(payer, payee, 15.0, event);
        Debt debt2 = new Debt(payee, payer, 15.0, event);
        service.settleDebt(debt);
        List<Debt> debts = new ArrayList<>();
        debts.add(debt);
        assertEquals(debts, service.findAll());

        service.settleDebt(debt2);
        List<Debt> debts2 = new ArrayList<>();
        assertEquals(debts2, service.findAll());
    }

    @Test
    public void settleDebtsCase3() throws NoDebtFoundException {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User("andac", "a@m.com");
        User payee = new User("mete", "m@m.com");
        Event event = new Event();

        Debt debt = new Debt(payer, payee, 15.0, event);
        Debt debt2 = new Debt(payee, payer, 30.0, event);
        service.settleDebt(debt);
        List<Debt> debts = new ArrayList<>();
        debts.add(debt);
        assertEquals(debts, service.findAll());

        service.settleDebt(debt2);
        List<Debt> debts2 = new ArrayList<>();
        debts2.add(new Debt(payee, payer, 15.0, event));
        assertEquals(debts2, service.findAll());
    }


    @Test
    public void settleDebtsCase4() throws NoDebtFoundException {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User("andac", "a@m.com");
        User payee = new User("mete", "m@m.com");
        Event event = new Event();

        Debt debt = new Debt(payer, payee, 15.0, event);
        Debt debt2 = new Debt(payee, payer, 10.0, event);
        service.settleDebt(debt);
        List<Debt> debts = new ArrayList<>();
        debts.add(debt);
        assertEquals(debts, service.findAll());

        service.settleDebt(debt2);
        List<Debt> debts2 = new ArrayList<>();
        debts2.add(new Debt(payer, payee, 5.0, event));
        assertEquals(debts2, service.findAll());
    }
    @Test
    public void deleteDebtTest() throws NoDebtFoundException {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        Debt debt = new Debt(payer, payee, 15.0);
        service.save(debt);
        service.deleteDebt(debt.getId());
        List<Debt> debts = new ArrayList<>();
        assertEquals(debts, service.findAll());
    }

    @Test
    public void existsByIdTest() {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        service.addDebt(payer, payee, 15.0);
        long id = service.findAll().get(0).getId();
        assertTrue(service.existsById(id));

    }

    @Test
    public void notExistsByIdTest() {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        assertFalse(service.existsById(5));
    }

    @Test
    public void getDebtTest() throws NoDebtFoundException {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        service.addDebt(payer, payee, 15.0);
        Debt debt = (new Debt(payer, payee, 15.0));
        assertEquals(debt, service.getDebtByPayerAndPayee(payer, payee));
    }

    @Test
    public void falseGetDebtTest() throws NoDebtFoundException {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        assertNull(service.getDebtByPayerAndPayee(payer, payee));

    }

    @Test
    public void saveTest() throws NoDebtFoundException {
        DebtRepoTest repo = new DebtRepoTest();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        Debt debt = (new Debt(payer, payee, 15.0));
        assertEquals(debt, service.save(debt));
        assertEquals(service.getRepo().getDebtByPayerAndPayee(payer, payee), debt);
    }
}

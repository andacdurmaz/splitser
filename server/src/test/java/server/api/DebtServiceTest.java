package server.api;

import commons.Debt;
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
        DebtRepo repo = new DebtRepo();
        DebtService service = new DebtService(repo);
        assertNotNull(service);
        assertEquals(repo, service.getRepo());
    }

    @Test
    public void findAllTest(){
        DebtRepo repo = new DebtRepo();
        Debt debt1 = new Debt();
        Debt debt2 = new Debt();
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
        DebtRepo repo = new DebtRepo();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        service.addDebt(payer, payee, 15.0);
        List<Debt> debts = new ArrayList<>();
        debts.add(new Debt(payer, payee, 15.0));
        assertEquals(debts, service.findAll());
    }

    @Test
    public void deleteDebtTest() throws NoDebtFoundException {
        DebtRepo repo = new DebtRepo();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        service.addDebt(payer, payee, 15.0);
        service.deleteDebt(payer, payee);
        List<Debt> debts = new ArrayList<>();
        assertEquals(debts, service.findAll());
    }

    @Test
    public void existsByIdTest() {
        DebtRepo repo = new DebtRepo();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        service.addDebt(payer, payee, 15.0);
        long id = service.findAll().get(0).getId();
        assertTrue(service.existsById(id));

    }

    @Test
    public void notExistsByIdTest() {
        DebtRepo repo = new DebtRepo();
        DebtService service = new DebtService(repo);
        assertFalse(service.existsById(5));

    }

    @Test
    public void getDebtTest() throws NoDebtFoundException {
        DebtRepo repo = new DebtRepo();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        service.addDebt(payer, payee, 15.0);
        Debt debt = (new Debt(payer, payee, 15.0));
        assertEquals(debt, service.getDebtByPayerAndPayee(payer, payee));
    }

    @Test
    public void falseGetDebtTest() throws NoDebtFoundException {
        DebtRepo repo = new DebtRepo();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        assertNull(service.getDebtByPayerAndPayee(payer, payee));

    }

    @Test
    public void saveTest() throws NoDebtFoundException {
        DebtRepo repo = new DebtRepo();
        DebtService service = new DebtService(repo);
        User payer = new User();
        User payee = new User();
        Debt debt = (new Debt(payer, payee, 15.0));
        assertEquals(debt, service.save(debt));
        assertEquals(service.getRepo().getDebtByPayerAndPayee(payer, payee), debt);
    }
}

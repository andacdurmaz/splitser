package server.api;

import commons.Expense;
import commons.User;
import commons.Debt;
import commons.exceptions.NoDebtFoundException;
import commons.exceptions.NoSuchExpenseException;
import commons.exceptions.NoUserFoundException;
import org.junit.jupiter.api.Test;
import server.service.DebtService;
import server.service.ExpenseService;
import server.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void constructorTest(){
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        assertNotNull(service);
        assertEquals(repo, service.getRepo());
    }

    @Test
    public void setterTestExpense(){
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        ExpenseRepositoryTest expenseRepository = new ExpenseRepositoryTest();
        ExpenseService expenseService = new ExpenseService(expenseRepository);
        service.setExpenseService(expenseService);
        assertEquals(expenseService, service.getExpenseService());
    }

    @Test
    public void setterTestDebt(){
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        DebtRepoTest debtrepo = new DebtRepoTest();
        DebtService debtService = new DebtService(debtrepo);
        service.setDebtService(debtService);
        assertEquals(debtService, service.getDebtService());
    }


    @Test
    public void findAllTest(){
        UserRepoTest repo = new UserRepoTest();
        User user1 = new User();
        User user2 = new User();
        repo.save(user1);
        repo.save(user2);
        UserService service = new UserService(repo);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        assertEquals(users, service.findAll());
    }

    @Test
    public void getUserTest() throws NoUserFoundException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        User user = new User("andac","andac@gmail.com");
        service.save(user);
        long id = user.getUserID();
        assertEquals(user, service.getUserById(id));
    }

    @Test
    public void getNoUserTest()  {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        assertThrows(NoUserFoundException.class, () -> {        service.getUserById(123);} );
    }
    @Test
    public void saveTest() throws NoUserFoundException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        User user = new User();
        assertEquals(user, service.save(user));
        long id = user.getUserID();
        assertEquals(service.getRepo().getUserById(id), user);
    }

    @Test
    public void existsByIdTest() {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        User user = new User();
        service.save(user);
        long id = service.findAll().get(0).getUserID();
        assertTrue(service.existsById(id));

    }

    @Test
    public void notExistsByIdTest() {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        assertFalse(service.existsById(5));
    }

    @Test
    public void settleDebtTest() throws NoDebtFoundException, NoSuchExpenseException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        ExpenseRepositoryTest expenseRepository = new ExpenseRepositoryTest();
        ExpenseService expenseService = new ExpenseService(expenseRepository);
        service.setExpenseService(expenseService);
        DebtRepoTest debtrepo = new DebtRepoTest();
        DebtService debtService = new DebtService(debtrepo);
        service.setDebtService(debtService);
        User payer = new User("andac", "andac@gmail.com");
        User payee = new User("mete", "mete@mail.com");
        repo.save(payer);
        repo.save(payee);
        List<User> payers = new ArrayList<>();
        payers.add(payer);
        Expense expense = new Expense("test", 4.0, payee, payers);
        payer.addExpense(expense);
        //service.settleDebt(payer, expense);
        //Debt debt = new Debt(payer, payee, 2.0);
        //assertTrue(payer.getDebts().contains(debt));
    }


    @Test
    public void addDebtCase1() throws NoDebtFoundException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        ExpenseRepositoryTest expenseRepository = new ExpenseRepositoryTest();
        ExpenseService expenseService = new ExpenseService(expenseRepository);
        service.setExpenseService(expenseService);
        DebtRepoTest debtrepo = new DebtRepoTest();
        DebtService debtService = new DebtService(debtrepo);
        service.setDebtService(debtService);
        User payer = new User("andac", "andac@gmail.com");
        User payee = new User("mete", "mete@mail.com");
        repo.save(payer);
        repo.save(payee);
        service.addDebts(payer, payee, 5.0);
        Debt debt = new Debt(payer, payee, 5.0);
        assertTrue(payer.getDebts().contains(debt));
        assertTrue((debtService.getRepo()).getDebts().contains(debtService.getDebtByPayerAndPayee(payer, payee)) );
    }
    @Test
    public void addDebtCase2() throws NoDebtFoundException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        ExpenseRepositoryTest expenseRepository = new ExpenseRepositoryTest();
        ExpenseService expenseService = new ExpenseService(expenseRepository);
        service.setExpenseService(expenseService);
        DebtRepoTest debtrepo = new DebtRepoTest();
        DebtService debtService = new DebtService(debtrepo);
        service.setDebtService(debtService);
        User payer = new User("andac", "andac@gmail.com");
        User payee = new User("mete", "mete@mail.com");
        repo.save(payer);
        repo.save(payee);
        service.addDebts(payer, payee, 5.0);
        service.addDebts(payer, payee, 3.0);
        Debt debt = new Debt(payer, payee, 8.0);
        assertTrue(payer.getDebts().contains(debt));
        assertTrue((debtService.getRepo()).getDebts().contains(debtService.getDebtByPayerAndPayee(payer, payee)) );
    }
    @Test
    public void addDebtCase3() throws NoDebtFoundException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        ExpenseRepositoryTest expenseRepository = new ExpenseRepositoryTest();
        ExpenseService expenseService = new ExpenseService(expenseRepository);
        service.setExpenseService(expenseService);
        DebtRepoTest debtrepo = new DebtRepoTest();
        DebtService debtService = new DebtService(debtrepo);
        service.setDebtService(debtService);
        User payer = new User("andac", "andac@gmail.com");
        User payee = new User("mete", "mete@mail.com");
        repo.save(payer);
        repo.save(payee);
        service.addDebts(payer, payee, 5.0);
        service.addDebts(payee, payer, 7.0);
        Debt debt = new Debt(payee, payer, 2.0);
        assertTrue(payee.getDebts().contains(debt));
        assertTrue((debtService.getRepo()).getDebts().contains(debtService.getDebtByPayerAndPayee(payee, payer)) );
    }

    @Test
    public void addDebtCase4() throws NoDebtFoundException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        ExpenseRepositoryTest expenseRepository = new ExpenseRepositoryTest();
        ExpenseService expenseService = new ExpenseService(expenseRepository);
        service.setExpenseService(expenseService);
        DebtRepoTest debtrepo = new DebtRepoTest();
        DebtService debtService = new DebtService(debtrepo);
        service.setDebtService(debtService);
        User payer = new User("andac", "andac@gmail.com");
        User payee = new User("mete", "mete@mail.com");
        repo.save(payer);
        repo.save(payee);
        service.addDebts(payer, payee, 5.0);
        service.addDebts(payee, payer, 3.0);
        Debt debt = new Debt(payer, payee, 2.0);
        assertTrue(payer.getDebts().contains(debt));
        assertTrue((debtService.getRepo()).getDebts().contains(debtService.getDebtByPayerAndPayee(payer, payee)) );
    }

    @Test
    public void addDebtCase5() throws NoDebtFoundException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        ExpenseRepositoryTest expenseRepository = new ExpenseRepositoryTest();
        ExpenseService expenseService = new ExpenseService(expenseRepository);
        service.setExpenseService(expenseService);
        DebtRepoTest debtrepo = new DebtRepoTest();
        DebtService debtService = new DebtService(debtrepo);
        service.setDebtService(debtService);
        User payer = new User("andac", "andac@gmail.com");
        User payee = new User("mete", "mete@mail.com");
        repo.save(payer);
        repo.save(payee);
        service.addDebts(payer, payee, 5.0);
        service.addDebts(payee, payer, 5.0);
        assertTrue(payer.getDebts().isEmpty());
        assertTrue(payee.getDebts().isEmpty());
        assertTrue((debtService.getRepo()).getDebts().isEmpty());
    }
}

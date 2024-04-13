package server.api;

import commons.Expense;
import commons.User;
import commons.Debt;
import commons.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
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
    public void updateUserTest() {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        User added1 = new User();
        repo.save(added1);
        assertNull(added1.getUsername());
        added1.setUsername("changed");
        long id1 = added1.getUserID();
        assertEquals(added1, service.updateUser(added1));
    }
    @Test
    public void getUserTest() throws NoUserFoundException, EmailFormatException,
            IBANFormatException, BICFormatException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        User user = new User("andac","andac@gmail.com",
                "GB94BARC10201530093459","RABONL2UXXX");
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
    public void updateTest() throws EmailFormatException, IBANFormatException, BICFormatException {
        UserRepoTest repo = new UserRepoTest();
        UserService service = new UserService(repo);
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "RABONL2UXXX");
        repo.save(user);
        User updated = new User();
        service.updateUser(updated);
        assertTrue(repo.getUsers().contains(updated));
        //assertTrue(!repo.getUsers().contains(user));
    }

}

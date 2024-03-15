package server.api;

import commons.Expense;
import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.UserRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserControllerTest {
    private UserRepo repo;
    private UserController sut;

    @BeforeEach
    public void setup() {
        repo = new UserRepo();
        sut = new UserController(repo);
    }


    @Test
    public void addTest(){
        User added = new User();
        sut.add(added);
        assertTrue(((UserRepo) sut.getRepo()).getUsers().contains(added));
        assertTrue(((UserRepo) sut.getRepo()).getCalledMethods().get(0).equals("New User added."));

    }

    @Test
    public void getByIdTest() throws UserRepository.NoUserFoundException {
        User added1 = new User();
        User added2 = new User();
        sut.add(added1);
        sut.add(added2);
        long id1 = added1.getUserID();
        long id2 = added2.getUserID();
        assertEquals(ResponseEntity.ok(added1), sut.getById(id1));
        assertEquals(ResponseEntity.ok(added2), sut.getById(id2));
    }

    @Test
    public void getUsernameTest() throws UserRepository.NoUserFoundException {
        User added = new User();
        added.setUsername("andac");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("andac"), sut.getUsernameById(id));
    }

    @Test
    public void getEmailTest() throws UserRepository.NoUserFoundException, User.EmailFormatException {
        User added = new User();
        added.setEmail("andac123@gmail.com");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("andac123@gmail.com"), sut.getEmailById(id));
    }

    @Test
    public void getServerTest() throws UserRepository.NoUserFoundException {
        User added = new User();
        added.setServerURL("server1");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("server1"), sut.getServerById(id));
    }

    @Test
    public void getWalletTest() throws UserRepository.NoUserFoundException {
        User added = new User();
        added.setWallet(15);
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok(15), sut.getWalletById(id));
    }

    @Test
    public void getDebtTest() throws UserRepository.NoUserFoundException {
        User added = new User();
        User debt = new User();
        Map<User, Double> debts = new HashMap<>();
        debts.put(debt, 15.00);
        added.setDebts(debts);
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok(debts), sut.getDebtsById(id));
    }

    @Test
    public void getIBANTest() throws UserRepository.NoUserFoundException, User.IBANFormatException {
        User added = new User();
        added.setIBAN("NL00001111222233334444555566667777");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("NL00001111222233334444555566667777"), sut.getIBANById(id));
    }

    @Test
    public void getBICTest() throws UserRepository.NoUserFoundException, User.BICFormatException {
        User added = new User();
        added.setBIC("22333444555");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("22333444555"), sut.getBICById(id));
    }

    @Test
    public void getExpenseTest() throws UserRepository.NoUserFoundException {
        User added = new User();
        List<Expense> expenses = new ArrayList<>();
        List<User> participants = new ArrayList<>();
        participants.add(added);
        expenses.add(new Expense("expense", 25, added, participants));
        added.setExpenses(expenses);
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok(expenses), sut.getExpensesById(id));
    }

    @Test
    public void getByIdFail() throws UserRepository.NoUserFoundException {
        assertEquals(ResponseEntity.badRequest().build(),sut.getById(0));
    }

    @Test
    public void getAllTest() {
        User added1 = new User();
        User added2 = new User();
        sut.add(added1);
        sut.add(added2);
        assertEquals(((UserRepo) sut.getRepo()).getUsers(), sut.getAll());
    }


}

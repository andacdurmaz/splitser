package server.api;

//import commons.Expense;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.Random;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.http.HttpStatus.BAD_REQUEST;

import commons.Expense;
import commons.User;
import commons.exceptions.NoUserFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.service.ExpenseService;
import server.service.UserService;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseControllerTest {
    @BeforeEach
    public void setup() {
        repo = new ExpenseRepositoryTest();
        ExpenseService service = new ExpenseService(repo);
        Random random = new Random();
        sut = new ExpenseController(random, service);
    }

    public int nextInt;
//    private ExpenseControllerTest.MyRandom random;
    private ExpenseRepositoryTest repo;

    private ExpenseController sut;

    @Test
    public void constructorTest(){
        assertNotNull(sut.getService());
        assertNotNull(sut.getService().getRepo());
    }

    @Test
    public void addTest(){
        Expense expense = new Expense();
        sut.add(expense);
        assertTrue(((ExpenseRepositoryTest) sut.getService().getRepo()).findAll().contains(expense));
    }

    @Test
    public void deleteTest() {
        Expense expense = new Expense();
        sut.add(expense);
        sut.deleteExpense(expense.getId());
        assertFalse(((ExpenseRepositoryTest) sut.getService().getRepo()).findAll().contains(expense));
    }

    @Test
    public void getByIdTest() {
        Expense added1 = new Expense();
        added1.setName("andac");
        sut.add(added1);
        long id1 = added1.getId();
        assertEquals(ResponseEntity.ok(added1), sut.getById(id1));
    }

    @Test
    public void updateTest() {
        Expense added1 = new Expense();
        sut.add(added1);
        assertNull(added1.getName());
        added1.setName("changed");
        long id1 = added1.getId();
        assertEquals(ResponseEntity.ok(added1), sut.updateExpense(id1, added1));
    }
//    @BeforeEach
//    public void setup() {
//        random = new ExpenseControllerTest.MyRandom();
//        repo = new TestExpenseRepository();
//        sut = new ExpenseController(random, repo);
//    }
//
//    @Test
//    public void cannotAddNullPerson() {
//        var actual = sut.add(getExpense(null));
//        assertEquals(BAD_REQUEST, actual.getStatusCode());
//    }
//
//    @Test
//    public void randomSelection() {
//        sut.add(getExpense("q1"));
//        sut.add(getExpense("q2"));
//        nextInt = 1;
//        var actual = sut.getRandom();
//
//        assertTrue(random.wasCalled);
//        assertEquals("q2", actual.getBody());
//    }
//
//    @Test
//    public void databaseIsUsed() {
//        sut.add(getExpense("q1"));
//        repo.calledMethods.contains("save");
//    }
//
//    private static Expense getExpense(String q) {
//        return new Expense();
//    }
//
//    @SuppressWarnings("serial")
//    public class MyRandom extends Random {
//
//        public boolean wasCalled = false;
//
//        @Override
//        public int nextInt(int bound) {
//            wasCalled = true;
//            return nextInt;
//        }
//    }
}

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

public class ExpenseControllerTest {

    public int nextInt;
//    private ExpenseControllerTest.MyRandom random;
    private ExpenseRepositoryTest repo;

    private ExpenseController sut;

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

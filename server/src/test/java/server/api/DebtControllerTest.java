package server.api;

import commons.Debt;
import commons.Event;
import commons.User;
import commons.exceptions.NoDebtFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.service.DebtService;

import static org.junit.jupiter.api.Assertions.*;

public class DebtControllerTest {
    private DebtRepoTest repo;
    private DebtService service;
    private DebtController sut;

    @BeforeEach
    public void setup() {
        repo = new DebtRepoTest();
        service = new DebtService(repo);
        sut = new DebtController(service);
    }

    @Test
    public void constructorTest(){
        assertNotNull(repo);
        assertNotNull(service);
        assertNotNull(sut);
    }

    @Test
    public void getAllTest() throws NoDebtFoundException {
        User user1 = new User("andac", "v");
        User user2 = new User("mete", "m");
        Event event1 = new Event();
        Event event2 = new Event();
        Debt added1 = new Debt(user1, user2, 5.0, event1);
        Debt added2 = new Debt(user2, user1, 3.0, event2);
        sut.add(added1);
        sut.add(added2);
        assertEquals(((DebtRepoTest)sut.getService().getRepo()).getDebts(), sut.getAll());
    }

    @Test
    public void addTest() throws NoDebtFoundException {
        Debt added = new Debt();
        sut.add(added);
        assertTrue(((DebtRepoTest) sut.getService().getRepo()).getDebts().contains(added));
    }

    @Test
    public void addFailTest() throws NoDebtFoundException {
        assertEquals(ResponseEntity.badRequest().build(), sut.add(null));
    }




}

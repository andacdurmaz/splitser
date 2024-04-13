package server.api;

import commons.Debt;
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
    public void getAllTest() {
        Debt added1 = new Debt();
        Debt added2 = new Debt();
        sut.add(added1);
        sut.add(added2);
        assertEquals(((DebtRepoTest)sut.getService().getRepo()).getDebts(), sut.getAll());
    }

    @Test
    public void addTest() {
        Debt added = new Debt();
        sut.add(added);
        assertTrue(((DebtRepoTest) sut.getService().getRepo()).getDebts().contains(added));
        assertEquals("save", ((DebtRepoTest) sut.getService().getRepo()).getCalledMethods().get(0));
    }

    @Test
    public void addFailTest() {
        assertEquals(ResponseEntity.badRequest().build(), sut.add(null));
    }




}

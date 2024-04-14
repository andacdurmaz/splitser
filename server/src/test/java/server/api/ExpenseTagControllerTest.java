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

import commons.ExpenseTag;
import commons.exceptions.NoUserFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.service.ExpenseTagService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ExpenseTagControllerTest {
    private ExpenseTagRepositoryTest repo;
    private ExpenseTagService service;
    private ExpenseTagController sut;

    @BeforeEach
    public void setup() {
        repo = new ExpenseTagRepositoryTest();
        service = new ExpenseTagService(repo);
        sut = new ExpenseTagController(service);
    }



    @Test
    public void getByIdTest() throws NoUserFoundException {
        ExpenseTag added1 = new ExpenseTag();
        added1.setName("andac");
        sut.addExpenseTag(added1);
        long id1 = added1.getId();
        assertEquals(ResponseEntity.ok(added1), sut.getById(id1));
    }


    @Test
    public void updateTest() {
        ExpenseTag added1 = new ExpenseTag();
        sut.addExpenseTag(added1);
        assertNull(added1.getName());
        added1.getName();
        long id1 = added1.getId();
        assertEquals(ResponseEntity.ok(added1), sut.updateExpenseTag(id1, added1));
    }


    @Test
    public void getByIdFail() throws NoUserFoundException {
        assertEquals(ResponseEntity.badRequest().build(),sut.getById(0));
    }

    @Test
    public void getAllTest() {
        ExpenseTag added1 = new ExpenseTag();
        ExpenseTag added2 = new ExpenseTag();
        sut.addExpenseTag(added1);
        sut.addExpenseTag(added2);
        assertEquals((sut.getService().getAllExpenseTags()), sut.getAll());
    }

    @Test
    public void addFailTest() {
        assertEquals(ResponseEntity.badRequest().build(), sut.addExpenseTag(null));
    }


}

package server.api;

import commons.Expense;
import commons.User;
import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import commons.exceptions.NoUserFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.service.UserService;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserControllerTest {
    private UserRepo repo;
    private UserService service;
    private UserController sut;

    @BeforeEach
    public void setup() {
        repo = new UserRepo();
        service = new UserService(repo);
        sut = new UserController(service);
    }



//
//import commons.Expense;
//import commons.User;
//import commons.exceptions.BICFormatException;
//import commons.exceptions.EmailFormatException;
//import commons.exceptions.IBANFormatException;
//import commons.exceptions.NoUserFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.ResponseEntity;
//import server.service.UserService;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//public class UserControllerTest {
//    private UserRepo repo;
//    private UserService service;
//    private UserController sut;
//
//    @BeforeEach
//    public void setup() {
//        repo = new UserRepo();
//        service = new UserService(repo);
//        sut = new UserController(service);
//    }
//
//
////    @Test
////    public void addTest(){
////        User added = new User();
////        sut.add(added);
////        assertTrue(((UserRepo) sut.getRepo()).getUsers().contains(added));
////        assertTrue(((UserRepo) sut.getRepo()).getCalledMethods().get(0).equals("New User added."));
////
////    }
//
//    @Test
//    public void getByIdTest() throws NoUserFoundException {
//        User added1 = new User();
//        added1.setUsername("andac");
//        sut.add(added1);
//        long id1 = added1.getUserID();
//        assertEquals(ResponseEntity.ok(added1), sut.getById(id1));
//    }
//
//    @Test
//    public void getUsernameTest() throws NoUserFoundException {
//        User added = new User();
//        added.setUsername("andac");
//        sut.add(added);
//        long id = added.getUserID();
//        assertEquals(ResponseEntity.ok("andac"), sut.getUsernameById(id));
//    }


    @Test
    public void getIBANTest() throws NoUserFoundException, IBANFormatException {
        User added = new User();
        added.setIBAN("NL00001111222233334444555566667777");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("NL00001111222233334444555566667777"), sut.getIBANById(id));
    }

    @Test
    public void getBICTest() throws NoUserFoundException, BICFormatException {
        User added = new User();
        added.setBIC("22333444555");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("22333444555"), sut.getBICById(id));
    }

    @Test
    public void getExpenseTest() throws NoUserFoundException {
        User added = new User();
        List<Expense> expenses = new ArrayList<>();
        List<User> participants = new ArrayList<>();
        participants.add(added);
        Expense expense = new Expense("expense", 25, added, participants);
        expenses.add(expense);
        added.setExpenses(expenses);
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok(expenses), sut.getExpensesById(id));
    }

    @Test
    public void getByIdFail() throws NoUserFoundException {
        assertEquals(ResponseEntity.badRequest().build(),sut.getById(0));
    }

    @Test
    public void getAllTest() {
        User added1 = new User();
        User added2 = new User();
        sut.add(added1);
        sut.add(added2);
        assertEquals(((UserRepo)sut.getService().getRepo()).getUsers(), sut.getAll());
    }


}


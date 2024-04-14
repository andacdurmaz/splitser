package server.api;

import commons.Debt;
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
import static org.junit.jupiter.api.Assertions.*;


public class UserControllerTest {
    private UserRepoTest repo;
    private UserService service;
    private UserController sut;

    @BeforeEach
    public void setup() {
        repo = new UserRepoTest();
        service = new UserService(repo);
        sut = new UserController(service);
    }


    @Test
    public void constructorTest(){
        assertNotNull(sut.getService());
        assertNotNull(sut.getService().getRepo());
    }
    @Test
    public void addTest(){
        User added = new User();
        sut.add(added);
        assertTrue(((UserRepoTest) sut.getService().getRepo()).getUsers().contains(added));
        assertEquals("save", ((UserRepoTest) sut.getService().getRepo()).getCalledMethods().get(0));

    }

    @Test
    public void getByIdTest() throws NoUserFoundException {
        User added1 = new User();
        added1.setUsername("andac");
        sut.add(added1);
        long id1 = added1.getUserID();
        assertEquals(ResponseEntity.ok(added1), sut.getById(id1));
    }

    @Test
    public void getUsernameTest() throws NoUserFoundException {
        User added = new User();
        added.setUsername("andac");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("andac"), sut.getUsernameById(id));
    }

    @Test
    public void updateTest() {
        User added1 = new User();
        sut.add(added1);
        assertNull(added1.getUsername());
        added1.setUsername("changed");
        long id1 = added1.getUserID();
        assertEquals(ResponseEntity.ok(added1), sut.updateUser(id1, added1));
    }

    @Test
    public void getServerTest() throws NoUserFoundException {
        User added = new User();
        added.setServerURL("andac123");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("andac123"), sut.getServerById(id));
    }

    @Test
    public void getEmailTest() throws EmailFormatException, NoUserFoundException {
        User added = new User();
        added.setEmail("andac@gmail.com");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("andac@gmail.com"), sut.getEmailById(id));
    }


    @Test
    public void getIBANTest() throws NoUserFoundException, IBANFormatException {
        User added = new User();
        added.setIban("GB94BARC10201530093459");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("GB94BARC10201530093459"), sut.getIBANById(id));
    }

    @Test
    public void getBICTest() throws NoUserFoundException, BICFormatException {
        User added = new User();
        added.setBic("RABONL2UXXX");
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok("RABONL2UXXX"), sut.getBICById(id));
    }


    @Test void getWalletTest() throws NoUserFoundException {
        User added = new User();
        added.setWallet(15.0);
        sut.add(added);
        long id = added.getUserID();
        assertEquals(ResponseEntity.ok(15.0), sut.getWalletById(id));
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
        assertEquals(((UserRepoTest)sut.getService().getRepo()).getUsers(), sut.getAll());
    }

    @Test
    public void addFailTest() {
        assertEquals(ResponseEntity.badRequest().build(), sut.add(null));
    }



}


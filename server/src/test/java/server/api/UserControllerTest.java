package server.api;

import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.UserRepository;


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

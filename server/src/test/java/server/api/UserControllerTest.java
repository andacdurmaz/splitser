package server.api;

import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


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

}

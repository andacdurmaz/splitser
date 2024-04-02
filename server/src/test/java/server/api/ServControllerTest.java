package server.api;

import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.EventRepository;
import server.service.ServService;


import static org.assertj.core.api.Assertions.assertThat;

class ServControllerTest {
    private ServService service;
    private EventRepository repo;
    private ServController controller;
    @BeforeEach
    @InjectService
    public void init(){
        service = new ServService(repo);
        controller = new ServController(service);
    }
    @Test
    public void LoginSuccess(){
        Long l = controller.getPass().getBody();
        Assertions.assertEquals(controller.login(String.valueOf(l)).getStatusCodeValue(), 200);
    }

    @Test
    public void LoginFail(){
        Long l = controller.getPass().getBody() + 5;
        Assertions.assertEquals(controller.login(String.valueOf(l)).getStatusCodeValue(), 401);
    }


    @Test
    void loginBadFormat() {
        Assertions.assertEquals(controller.login("String.valueOf(l)").getStatusCodeValue(), 400);
    }

    @Test
    void getPass() {

        assertThat(controller.getPass().getBody().getClass()).isEqualTo(Long.class);
    }
}

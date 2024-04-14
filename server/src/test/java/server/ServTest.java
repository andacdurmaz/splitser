package server;

import commons.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ServTest {
    @Test
    void TestConstructor() {
        Serv server = new Serv();
        Assertions.assertTrue(server != null);
        Assertions.assertTrue(server.getEvents().size() == 0);
    }

    @Test
    void TestAdmins() {
        Serv server = new Serv();
        Assertions.assertTrue(server.getAdmins().size() == 0);
    }

    @Test
    void TestId() {
        Serv server = new Serv();
        Assertions.assertTrue(server.getId() == 0);
    }

    @Test
    void TestAdmins1Admin() {
        Admin admin = new Admin("email", "password");
        Serv server = new Serv(admin);
        Assertions.assertTrue(
                server.getAdmins().get(0) != null
        );
        Assertions.assertTrue(
                server.getAdmins().size() == 1
        );
    }


    @Test
    void testEquals() {
        Serv server1 = new Serv();
        Serv server2 = new Serv();
        Assertions.assertEquals(server1, server2);
    }

    @Test
    void testNotEquals() {
        Admin admin1 = new Admin("email", "password");
        Admin admin2 = new Admin("noEmail", "passwordB");
        Serv server1 = new Serv(admin1);
        Serv server2 = new Serv(admin2);
        Assertions.assertEquals(server2, server2);
    }


    @Test
    void testToString() {
        Serv server1 = new Serv();
        Assertions.assertEquals(server1.toString(), "Serv{id=0, admins=[], events=[]}");
    }
}
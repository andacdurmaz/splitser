package commons;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void test1() {
        User user = new User("andac", "andac@gmail.com", "123");
        assertNotNull(user);
    }
}

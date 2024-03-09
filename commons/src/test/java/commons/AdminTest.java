package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    @Test
    void getId() {

    }

    @Test
    void getEmail() {
        Admin adminA = new Admin("123@email.com","Password123");
        String email = adminA.getEmail();
        assertEquals("123@email.com",email);
    }

    @Test
    void setEmail() {
        Admin adminA = new Admin("123@email.com","Password123");
        adminA.setEmail("ABC@email.com");
        assertEquals("ABC@email.com",adminA.getEmail());
    }

    @Test
    void getPassword() {
        Admin adminA = new Admin("123@email.com","Password123");
        String password = adminA.getPassword();
        assertEquals("Password123",password);

    }

    @Test
    void setPassword() {
        Admin adminA = new Admin("123@email.com","Password123");
        adminA.setPassword("PasswordABC");
        assertEquals("PasswordABC",adminA.getPassword());

    }

    @Test
    void testEqualsSame() {
        Admin adminA = new Admin("123@email.com","Password123");
        assertEquals(adminA, adminA);
    }

    @Test
    void testEqualsEqualButNotSame() {
        Admin adminA = new Admin("123@email.com","Password123");
        Admin adminB = new Admin("123@email.com","Password123");
        assertEquals(adminA, adminB);
    }
    @Test
    void testEqualsNotEqual() {
        Admin adminA = new Admin("123@email.com","Password123");
        Admin adminC = new Admin("ABC@email.com","PasswordABC");
        assertNotEquals(adminA,adminC);
    }

    @Test
    void testHashCode() {
        Admin adminA = new Admin("123@email.com","Password123");
        Admin adminB = new Admin("123@email.com","Password123");
        assertEquals(adminA.hashCode(), adminB.hashCode());
    }

    @Test
    void testToString() {
        Admin adminA = new Admin("123@email.com","Password123");
        assertEquals("Admin{id=0, email='123@email.com', password='Password123'}", adminA.toString());
    }
}

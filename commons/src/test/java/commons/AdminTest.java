package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    /**
     * test getEmail() method
     */
    @Test
    void getEmail() {
        Admin adminA = new Admin("123@email.com","Password123");
        String email = adminA.getEmail();
        assertEquals("123@email.com",email);
    }

    /**
     * test setEmail() method
     */
    @Test
    void setEmail() {
        Admin adminA = new Admin("123@email.com","Password123");
        adminA.setEmail("ABC@email.com");
        assertEquals("ABC@email.com",adminA.getEmail());
    }

    /**
     * test getPassword() method
     */
    @Test
    void getPassword() {
        Admin adminA = new Admin("123@email.com","Password123");
        String password = adminA.getPassword();
        assertEquals("Password123",password);

    }

    /**
     * test setPassword() method
     */
    @Test
    void setPassword() {
        Admin adminA = new Admin("123@email.com","Password123");
        adminA.setPassword("PasswordABC");
        assertEquals("PasswordABC",adminA.getPassword());

    }

    /**
     * testing reference, value
     */
    @Test
    void testEqualsSame() {
        Admin adminA = new Admin("123@email.com","Password123");
        assertEquals(adminA, adminA);
        assertSame(adminA, adminA);
    }

    /**
     * testing value
     */
    @Test
    void testEqualsEqualButNotSame() {
        Admin adminA = new Admin("123@email.com","Password123");
        Admin adminB = new Admin("123@email.com","Password123");
        assertEquals(adminA, adminB);
        assertNotSame(adminA, adminB);
    }

    /**
     * testing not equals, not same
     */
    @Test
    void testEqualsNotEqual() {
        Admin adminA = new Admin("123@email.com","Password123");
        Admin adminC = new Admin("ABC@email.com","PasswordABC");
        assertNotEquals(adminA,adminC);
        assertNotSame(adminA,adminC);
    }

    /**
     * test hashCode
     */
    @Test
    void testHashCode() {
        Admin adminA = new Admin("123@email.com","Password123");
        Admin adminB = new Admin("123@email.com","Password123");
        assertEquals(adminA.hashCode(), adminB.hashCode());
        assertNotSame(adminA.hashCode(),adminB.hashCode());
    }
    /**
     * test hashCode not same
     */
    @Test
    void testHashCodeNotEqual() {
        Admin adminA = new Admin("123@email.com","Password123");
        Admin adminB = new Admin("123@email.com","PasswordABC");
        assertNotEquals(adminA.hashCode(), adminB.hashCode());
        assertNotSame(adminA.hashCode(),adminB.hashCode());
    }

    /**
     * test toString() method
     */
    @Test
    void testToString() {
        Admin adminA = new Admin("123@email.com","Password123");
        assertEquals("Admin{email='123@email.com', password='Password123'}", adminA.toString());
    }
}

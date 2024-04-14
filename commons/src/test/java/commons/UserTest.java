package commons;


import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void constructorTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        assertNotNull(user);
        assertEquals("andac", user.getUsername());
        assertEquals("andac@gmail.com", user.getEmail());
        assertEquals("GB94BARC10201530093459", user.getIban());
        assertEquals("AABNNL2AXXX", user.getBic());
        assertEquals(Language.EN, user.getLanguage());
    }

    @Test
    public void defaultConstructorTest() {
        User user = new User();
        assertNotNull(user);
        assertNull( user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getLanguage());
    }

    @Test
    public void usernameTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        user.setUsername("mete");
        assertEquals("mete", user.getUsername());
    }
    @Test
    public void testID() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        user.setUserID(486);
        assertEquals(486, user.getUserID());
    }

    @Test
    public void emailTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user =new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        user.setEmail("andac72@hotmail.com");
        assertEquals("andac72@hotmail.com", user.getEmail());
    }



    @Test
    public void bicTest() throws BICFormatException,
            EmailFormatException, IBANFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        user.setBic("RABONL2UXXX");
        assertEquals("RABONL2UXXX", user.getBic());
    }

    @Test
    public void ibanTest() throws IBANFormatException,
            EmailFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        user.setIban("GB33BUKB20201555555555");
        assertEquals("GB33BUKB20201555555555", user.getIban());
    }

    @Test
    public void serverURLTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        assertNull(user.getServerURL());
        user.setServerURL("server.com");
        assertEquals("server.com", user.getServerURL());
    }

    @Test
    public void walletTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        assertEquals(0, user.getWallet());
        user.setWallet(10);
        assertEquals(10, user.getWallet());
    }



    @Test
    public void languageTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        user.setLanguage(Language.NL);
        assertEquals(Language.NL, user.getLanguage());
    }


    @Test
    public void toStringTest() throws IBANFormatException,
            BICFormatException, EmailFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        user.setIban("GB33BUKB20201555555555");
        user.setBic("RABONL2UXXX");
        user.setServerURL("server.123");
        String result = "User Information:\nUsername: andac\nE-mail: andac@gmail.com\nServer: server.123\n" +
                "IBAN: GB33BUKB20201555555555\nBIC: RABONL2UXXX\nPreferred Language: EN\n";
        assertEquals(result, user.toString());
    }

    @Test
    public void equalsTestSame() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        assertTrue(user.equals(user));
    }

    @Test
    public void equalsTestDifferent() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        User user2 = new User("mehmet", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        assertFalse(user.equals(user2));
    }
    @Test
    public void equalsNullTest() throws EmailFormatException,
            IBANFormatException, BICFormatException {
        User user = new User("andac", "andac@gmail.com",
                "GB94BARC10201530093459",
                "AABNNL2AXXX");
        assertFalse(user.equals(null));
    }

}

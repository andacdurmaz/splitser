package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;
    private String username;
    private String email;
    private String serverURL;
    private String IBAN;
    private String BIC;
    private Language language;
    @ManyToMany(mappedBy = "payingParticipants")
    private List<Expense> expenses;

    public User() {

    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    /**
     * Constructor method for a User
     * @param username the username of the User
     * @param email the email of the User
     * @param password the password of the User
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.language = Language.EN;
        this.expenses = new ArrayList<>();
    }

    /**
     * Getter method for User username
     * @return the username of the User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for an User's username
     * @param username new username of the User
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter method for User email
     * @return the email of the User
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for an User's e-mail
     * @param email new e-mail of the User
     * @throws EmailFormatException if the format isn inccorrect
     */
    public void setEmail(String email) throws EmailFormatException {
        if (email.indexOf('@') == -1) {
            throw new EmailFormatException();
        }
        this.email = email;
    }

    /**
     * Getter method for User's connected server
     * @return the server of the User
     */
    public String getServerURL() {
        return serverURL;
    }
    /**
     * Setter method for an User's server URL
     * @param serverURL new server URL of the User
     */
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }
    /**
     * Getter method for User IBAN
     * @return the IBAN of the User
     */
    public String getIBAN() {
        return IBAN;
    }

    /**
     * Setter method for an User's IBAN
     * @param IBAN new IBAN of the User
     * @throws IBANFormatException if the format is incorrect
     */
    public void setIBAN(String IBAN) throws IBANFormatException {
        if (IBAN.length() != 34) {
            throw new IBANFormatException();
        }
        this.IBAN = IBAN;
    }
    /**
     * Getter method for User BIC
     * @return the BIC of the User
     */
    public String getBIC() {
        return BIC;
    }

    /**
     * Setter method for an User's BIC
     * @param BIC new BIC of the User
     * @throws BICFormatException if the format is incorrect
     */
    public void setBIC(String BIC) throws BICFormatException {
        if (BIC.length() != 11) {
            throw new BICFormatException();
        }
        this.BIC = BIC;
    }
    /**
     * Getter method for User's preferred language
     * @return the language of the User
     */
    public Language getLanguage() {
        return language;
    }
    /**
     * Setter method for an User's preferred language
     * @param language new language of the User
     */
    public void setLanguage(Language language) {
        this.language = language;
    }
    /**
     * Getter method for User's ID
     * @return the ID of the User
     */
    public long getUserID() {
        return userID;
    }
    /**
     * Setter method for an User's ID
     * @param userID new ID of the User
     */
    public void setUserID(long userID) {
        this.userID = userID;
    }

    /**
     * ToString method for a User
     * @return information of a User in a readable format (excluding the password)
     */
    @Override
    public String toString() {
        return "User Information:\n" +
                "Username: " + username + "\n" +
                "E-mail: " + email + "\n" +
                "Server: " + serverURL + "\n" +
                "IBAN: " + IBAN + "\n" +
                "BIC: " + BIC + "\n" +
                "Preferred Language: " + language +
                "\n";
    }

    /**
     * Checks whether an object is equal to a User
     * @param o the compared object
     * @return true if the object is equal to an object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID;
    }


    static class IBANFormatException extends Exception {

    }

    class BICFormatException extends Exception {
    }

    class EmailFormatException extends Exception {
    }
}

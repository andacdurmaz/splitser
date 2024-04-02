package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import commons.exceptions.BICFormatException;
import commons.exceptions.EmailFormatException;
import commons.exceptions.IBANFormatException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "participant")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String serverURL;
    private String iban;
    private String bic;
    private Language language;
    private double wallet;

    @OneToMany(targetEntity = Debt.class)
    private List<Debt> debts = new ArrayList<>();
    @ManyToMany(targetEntity = Expense.class)
    @JoinTable(
            name = "user_expense_mapping",
            joinColumns = @JoinColumn(name = "expense", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "participant", referencedColumnName = "id")
    )
    private List<Expense> expenses = new ArrayList<>();

    /**
     * default constructor for a user object
     */
    public User() {

    }

    /**
     * getter methods for the expenses a user is a part of
     *
     * @return the list of expenses for a user
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * setter method for the expense list of a user
     *
     * @param expenses the list of the updated expenses for a user
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    /**
     * adds new expense for a user
     *
     * @param expense the new expense
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * Constructor method for user
     * @param username
     * @param email
     * @param iban
     * @param bic
     */
    public User(String username, String email, String iban, String bic)
            throws BICFormatException, IBANFormatException,
            EmailFormatException {
        this.username = username;
        if (email.indexOf('@') == -1 || email.indexOf('.') == -1)
            throw new EmailFormatException();
        this.email = email;
        if (iban.length() != 34) {
            throw new IBANFormatException();
        }
        this.iban = iban;
        if (bic.length() != 11) {
            throw new BICFormatException();
        }
        this.bic = bic;
        this.language = Language.EN;
        this.expenses = new ArrayList<>();
        this.wallet = 0;
        this.debts = new ArrayList<>();
    }

    /**
     * Getter method for User username
     *
     * @return the username of the User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for an User's username
     *
     * @param username new username of the User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for User email
     *
     * @return the email of the User
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for an User's e-mail
     *
     * @param email new e-mail of the User
     * @throws EmailFormatException if the format is incorrect
     */
    public void setEmail(String email) throws EmailFormatException {
        if (email.indexOf('@') == -1 || email.indexOf('.') == -1) {
            throw new EmailFormatException();
        }
        this.email = email;
    }

    /**
     * Getter method for User's connected server
     *
     * @return the server of the User
     */
    public String getServerURL() {
        return serverURL;
    }

    /**
     * Setter method for an User's server URL
     *
     * @param serverURL new server URL of the User
     */
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    /**
     * Getter method for User IBAN
     *
     * @return the IBAN of the User
     */
    public String getIban() {
        return iban;
    }

    /**
     * Setter method for an User's IBAN
     *
     * @param iban new IBAN of the User
     * @throws IBANFormatException if the format is incorrect
     */
    public void setIban(String iban) throws IBANFormatException {
        if (iban.length() != 34) {
            throw new IBANFormatException();
        }
        this.iban = iban;
    }

    /**
     * Getter method for User BIC
     *
     * @return the BIC of the User
     */
    public String getBic() {
        return bic;
    }

    /**
     * Setter method for an User's BIC
     *
     * @param bic new BIC of the User
     * @throws BICFormatException if the format is incorrect
     */
    public void setBic(String bic) throws BICFormatException {
        if (bic.length() != 11) {
            throw new BICFormatException();
        }
        this.bic = bic;
    }

    /**
     * Getter method for User's preferred language
     *
     * @return the language of the User
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Setter method for an User's preferred language
     *
     * @param language new language of the User
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Getter method for User's ID
     *
     * @return the ID of the User
     */
    public long getUserID() {
        return id;
    }

    /**
     * Setter method for an User's ID
     *
     * @param id new ID of the User
     */
    public void setUserID(long id) {
        this.id = id;
    }

    /**
     * getter method for the money of a user
     *
     * @return wallet
     */
    public double getWallet() {
        return wallet;
    }

    /**
     * setter method for the money of a user
     *
     * @param wallet the new money of a user
     */
    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    /**
     * getter method for the debts of a user
     *
     * @return the debts
     */
    public List<Debt> getDebts() {
        return new ArrayList<>(debts);
    }

    /**
     * setter method for the debts of a user
     *
     * @param debts new debts
     */
    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }


    /**
     * ToString method for a User
     *
     * @return information of a User in a readable format (excluding the password)
     */
    @Override
    public String toString() {
        return "User Information:\n" +
                "Username: " + username + "\n" +
                "E-mail: " + email + "\n" +
                "Server: " + serverURL + "\n" +
                "IBAN: " + iban + "\n" +
                "BIC: " + bic + "\n" +
                "Preferred Language: " + language +
                "\n";
    }

    /**
     * Checks whether an object is equal to a User
     *
     * @param o the compared object
     * @return true if the object is equal to an object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.getUsername());
    }


}

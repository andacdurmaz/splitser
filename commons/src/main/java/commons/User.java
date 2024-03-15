package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String email;
    private String serverURL;
    private String IBAN;
    private String BIC;
    private Language language;
    private double wallet;
    private Map<User, Double> debts;
    @ManyToMany(mappedBy = "payingParticipants")
    private List<Expense> expenses;

    /**
     * default constructor for a user object
     */
    public User() {

    }

    /**
     * getter methods for the expenses a user is a part of
     * @return the list of expenses for a user
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /**
     * setter method for the expense list of a user
     * @param expenses the list of the updated expenses for a user
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    /**
     * adds new expense for a user
     * @param expense the new expense
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * Constructor method for a User
     * @param username the username of the User
     * @param email the email of the User
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.language = Language.EN;
        this.expenses = new ArrayList<>();
        this.wallet = 0;
        this.debts = new HashMap();
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
        return id;
    }
    /**
     * Setter method for an User's ID
     * @param id new ID of the User
     */
    public void setUserID(long id) {
        this.id = id;
    }

    /**
     * getter method for the money of a user
     * @return wallet
     */
    public double getWallet() {
        return wallet;
    }

    /**
     * setter method for the money of a user
     * @param wallet the new money of a user
     */
    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    /**
     * getter method for the debts of a user
     * @return the debts
     */
    public Map<User, Double> getDebts() {
        return debts;
    }

    /**
     * setter method for the debts of a user
     * @param debts new debts
     */
    public void setDebts(Map<User, Double> debts) {
        this.debts = debts;
    }

    /**
     * adds a new debt to a users debt
     * @param user the user that will be paid
     * @param debt the amount needed to be paid
     */
    public void addDebts(User user, Double debt) {
        Double amount1 = user.getDebts().get(this);
        if (amount1 == null && this.getDebts().get(user) == null) {
            debts.put(user, debt);
        }
        else if (amount1 == null) {
            debts.put(user, debt + this.getDebts().get(user));
        }
        else if (amount1 >= debt){
            user.getDebts().put(this, amount1 - debt);
        }
        else {
            user.getDebts().remove(this);
            debts.put(user, debt - amount1);
        }
    }

    /**
     * creates a debt for a user for a given expense
     * @param expense the debt of the expense
     * @throws NoSuchExpenseException if the use ris not a part of the given expense
     */
    public void settleDebt(Expense expense) throws NoSuchExpenseException {
        if (!expenses.contains(expense))
            throw new NoSuchExpenseException();
        if (expense.getPayer().equals(this))
            return;
        int people = expense.getPayingParticipants().size() + 1;
        double payment = expense.getAmount() / people;
        this.addDebts(expense.getPayer(), payment);
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
        return id == user.id && username.equals(user.getUsername());
    }


    public static class IBANFormatException extends Exception {

    }

    public class BICFormatException extends Exception {
    }

    public class EmailFormatException extends Exception {
    }


    public class NoSuchExpenseException extends Throwable {
    }
}

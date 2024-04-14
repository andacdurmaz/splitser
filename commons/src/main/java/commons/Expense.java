package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private double amount;
    @OneToMany(targetEntity = Debt.class)
    private List<Debt> debts = new ArrayList<>();

    private Date date;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "payer", referencedColumnName = "id")
    private User payer;

    @ManyToMany()
    private List<User> payingParticipants;

    @ManyToOne(targetEntity = Event.class)
    @JoinColumn(name = "event", referencedColumnName = "id")
    private Event event;

    @ManyToOne()
    private ExpenseTag expenseTag;

    /**
     * Constructor for mapping
     */
    public Expense() {
        this.payingParticipants = new ArrayList<>();
    }

    /**
     * Expense constructor
     *
     * @param name               expense name
     * @param amount             expense amount
     * @param payer              expense payer
     * @param payingParticipants expense participants
     * @param date               expense date
     */
    public Expense(String name, double amount, User payer,
                   List<User> payingParticipants, Date date) {
        this.name = name;
        this.amount = amount;
        this.payer = payer;
        this.payingParticipants = payingParticipants;
        this.date = date;
    }
    /**
     * Expense constructor
     *
     * @param name               expense name
     * @param amount             expense amount
     * @param payer              expense payer
     * @param payingParticipants expense participants
     * @param date               expense date
     * @param expenseTag expense tag
     */
    public Expense(String name, double amount, User payer,
                   List<User> payingParticipants, Date date, ExpenseTag expenseTag) {
        this.name = name;
        this.amount = amount;
        this.payer = payer;
        this.payingParticipants = payingParticipants;
        this.date = date;
        this.expenseTag = expenseTag;
    }

    /**
     * Expense constructor
     *
     * @param name               expense name
     * @param amount             expense amount
     * @param payer              expense payer
     * @param payingParticipants expense participants
     */
    public Expense(String name, double amount, User payer,
                   List<User> payingParticipants) {
        this.name = name;
        this.amount = amount;
        this.payer = payer;
        this.payingParticipants = payingParticipants;
    }


    /**
     * Partial constructor
     *
     * @param name   expense name
     * @param amount expense amount
     */
    public Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.payingParticipants = new ArrayList<>();
    }

    /**
     * Partial constructor
     *
     * @param event event of the expense
     */
    public Expense(Event event) {
        this.event = event;
        this.payingParticipants = new ArrayList<>();
    }

    /**
     * Getter method
     *
     * @return expense name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method
     *
     * @param name expense name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method
     *
     * @return expense amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter method
     *
     * @param amount expense amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Getter method
     *
     * @return list of participants
     */
    public List<User> getPayingParticipants() {
        return payingParticipants;
    }

    /**
     * Setter method
     *
     * @param payingParticipants expense participants
     */
    public void setPayingParticipants(List<User> payingParticipants) {
        this.payingParticipants = payingParticipants;
    }

    /**
     * getter method for the expense payer
     *
     * @return the user who paid the expense
     */

    public User getPayer() {
        return payer;
    }

    /**
     * setter method for the expense payer
     *
     * @param payer who paid for the expense
     */
    public void setPayer(User payer) {
        this.payer = payer;
    }

    /**
     * setter method for the date the expense was made
     * @param expenseDate the new date
     */
    public void setExpenseDate(Date expenseDate) {
        this.date = expenseDate;
    }

    /**
     * Equals method
     *
     * @param o other object to compare to
     * @return true if this is equal to Object o, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense expense)) return false;
        return id == expense.id && Double.compare(getAmount(), expense.getAmount()) == 0
                && Objects.equals(getName(), expense.getName())
                && Objects.equals(getPayingParticipants(), expense.getPayingParticipants());
    }

    /**
     * Getter method
     *
     * @return expense tag
     */
    public ExpenseTag getExpenseTag() {
        return expenseTag;
    }

    /**
     * Setter method
     *
     * @param expenseTag sets expense tag of the expense
     */
    public void setExpenseTag(ExpenseTag expenseTag) {
        this.expenseTag = expenseTag;
    }

    /**
     * getter method for the id of an expense
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * setter method for the id of an expense
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Calls hash method
     *
     * @return hashCode of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getAmount(), getPayingParticipants());
    }

    /**
     * toString method
     *
     * @return human-readable String detail of expense
     */
    @Override
    public String toString() {
        return "Expense{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", payingParticipants=" + payingParticipants +
                '}';
    }

    /**
     * Getter method for the date
     *
     * @return date of expense
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter method for debts
     * @return debts list
     */
    public Collection<? extends Debt> getDebts() {
        return debts;
    }

    /**
     * Setter method for debts
     * @param debts debts list set
     */
    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }
}

package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //@GeneratedValue(strategy = GenerationType.AUTO)
    private long eventCode;
    private String title;
    private int amountOfParticipants;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Expense> expenses;
    private String description;

    /**
     * this constructor is needed for JPA
     */
    public Event() {
        this.eventCode = hashEventCode(id);
        this.expenses = new ArrayList<>();
    }

    /**
     * Constructor for the Event class
     *
     * @param title                title of the event
     * @param amountOfParticipants amount of participants that the event has
     * @param description          the event description
     */
    public Event(String title, int amountOfParticipants, String description) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
        this.eventCode = hashEventCode(id);
        this.expenses = new ArrayList<>();
        this.description = description;
    }

    /**
     * Constructor with only title
     *
     * @param title title of event
     */
    public Event(String title) {
        this.title = title;
        this.eventCode = hashEventCode(id);
    }

    /**
     * Get method
     *
     * @return id of this event
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method
     *
     * @param id set id of this event
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * constructor for when no eventCode was specified
     *
     * @param title                title of the event
     * @param amountOfParticipants amount of participants that the event has
     */
    public Event(String title, int amountOfParticipants) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
        this.eventCode = hashEventCode(id);
    }

    /**
     * gets the title for the event
     *
     * @return event title
     */
    public String getTitle() {
        return title;
    }

    /**
     * lets the user change the title for the event
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * shows the amount of participants
     *
     * @return amount of participants
     */
    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }

    /**
     * lets the user change the amount of participants allowed
     *
     * @param amountOfParticipants the new amount of participants
     */
    public void setAmountOfParticipants(int amountOfParticipants) {
        this.amountOfParticipants = amountOfParticipants;
    }

    /**
     * shows the user the eventCode for the given event
     *
     * @return eventCode
     */
    public long getEventCode() {
        return eventCode;
    }


    /**
     * lets the user change the description for the event
     *
     * @param description for the event
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * shows the event description
     *
     * @return event description
     */
    public String getDescription() {
        return description;
    }

    /**
     * shows all expenses
     *
     * @return list of expenses
     */
    public List<Expense> getExpenses() {
        return expenses;
    }


    /**
     * lets the user add expenses to the event
     *
     * @param expense to be added
     */
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    /**
     * lets the user remove certain expenses
     *
     * @param expense to be removed
     * @return true or false, dependent on if it succeeded
     * @throws Exception if the expense does not exist
     */
    public boolean removeExpense(Expense expense) throws Exception {
        if (!expenses.contains(expense)) {
            throw new Exception("This expense does not exist");
        }
        return expenses.remove(expense);
    }

    /**
     * edits an expense
     *
     * @param oldExpense the old expense that needs to be changed
     * @param newExpense the new expense
     * @return the new expense
     * @throws Exception if the expense does not exist
     */
    public Expense setExpense(Expense oldExpense, Expense newExpense) throws Exception {
        if (!expenses.contains(oldExpense)) {
            throw new Exception("This expense does not exist");
        }
        int index = expenses.indexOf(oldExpense);
        expenses.set(index, newExpense);
        return newExpense;
    }

    /**
     * gets the sum of all expenses in this event
     *
     * @return the sum
     */
    public double getSumOfExpenses() {
        if (expenses == null)
            return 0.0;
        return this.expenses
                .stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    /**
     * hashes the ID using the recommended hash calculation from java to get a unique eventCode
     *
     * @param id the id of the event
     * @return the new event-code
     */
    private long hashEventCode(long id) {
        return id ^ (id >>> 32);
    }

    /**
     * gets the event code
     *
     * @return the event code
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                eventCode == event.eventCode &&
                amountOfParticipants == event.amountOfParticipants &&
                Objects.equals(title, event.title) &&
                Objects.equals(expenses, event.expenses) &&
                Objects.equals(description, event.description);
    }

    /**
     * hashes the event
     *
     * @return the hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, eventCode, title, amountOfParticipants, expenses, description);
    }

    /**
     * returns the event as a string
     *
     * @return the event as a string
     */
    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"eventCode\":" + eventCode +
                ",\"title\":\"" + title + '\"' +
                ",\"amountOfParticipants\":" + amountOfParticipants +
                ",\"expenses\":" + expenses +
                ",\"description\":\"" + description + '\"' +
                ",\"sumOfExpenses\":" + getSumOfExpenses() +
                '}';
    }
}

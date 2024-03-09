package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    private String name;
    private double amount;

    @ManyToMany()
    private List<User> payingParticipants;

    @ManyToOne()
    private Event event;

    public Expense() {

    }

    /*
        Constructor for the expense class
     */
    public Expense (String name, double amount, List<User> payingParticipants) {
        this.name = name;
        this.amount = amount;
        this.payingParticipants = payingParticipants;
    }

    /*
        Partial constructor for the expense class
    */
    public Expense (String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.payingParticipants = new ArrayList<>();
    }

    /*
        Getter for the name attribute
     */
    public String getName() {
        return name;
    }

    /*
        Setter for the name attribute
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
        Getter for the amount attribute
     */
    public double getAmount() {
        return amount;
    }

    /*
        Setter for the amount attribute
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /*
        Getter for the paying participants
     */
    public List<User> getPayingParticipants() {
        return payingParticipants;
    }

    /*
        Setter for the paying participants
     */
    public void setPayingParticipants(List<User> payingParticipants) {
        this.payingParticipants = payingParticipants;
    }

    /*
        Equals method for the Expense class
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expense expense)) return false;
        return id == expense.id && Double.compare(getAmount(), expense.getAmount()) == 0 && Objects.equals(getName(), expense.getName()) && Objects.equals(getPayingParticipants(), expense.getPayingParticipants());
    }

    /*
        Hash code method for the Expense class
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getAmount(), getPayingParticipants());
    }

    /*
        ToString method for the expense class
     */
    @Override
    public String toString() {
        return "Expense{"+
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", payingParticipants=" + payingParticipants +
                '}';
    }
}

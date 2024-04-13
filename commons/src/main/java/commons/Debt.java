package commons;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table
public class Debt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private User payee;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    private Double amount;


    /**
     * constructor for a debt
     *
     * @param payer the payer of the debt
     * @param payee the payee of the debt
     * @param amount the amount that will be paid
     */
    public Debt(User payer, User payee, Double amount) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
    }

    /**
     * Constructor with expense
     * @param payer of the debt
     * @param payee of the debt
     * @param amount that will be paid
     * @param e the event of the debt
     */
    public Debt(User payer, User payee, Double amount, Event e) {
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.event = e;
    }

    /**
     * default constructor for a debt object
     */
    public Debt() {

    }

    /**
     * getter method for the payer of a debt
     *
     * @return the payer
     */
    public User getPayer() {
        return payer;
    }

    /**
     * setter method for the payer of a debt
     *
     * @param payer the new payer
     */
    public void setPayer(User payer) {
        this.payer = payer;
    }

    /**
     * getter method for the payee of a debt
     *
     * @return the payee
     */
    public User getPayee() {
        return payee;
    }

    /**
     * setter method for the payee of a debt
     *
     * @param payee the new payee
     */
    public void setPayee(User payee) {
        this.payee = payee;
    }

    /**
     * getter method for the amount of a debt
     *
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * setter method for the amount of a debt
     *
     * @param amount the new amount
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * Getter method
     * @return expense
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Setter method
     * @param e expense
     */
    public void setEvent(Event e) {
        this.event = e;
    }

    /**
     * setter method for the id of a debt
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * getter method for the id of a debt
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * equals method for two debts
     *
     * @param o the compared object
     * @return true if the object is equal to the debt
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt debt = (Debt) o;
        return id == debt.id && Objects.equals(payer, debt.payer)
                && Objects.equals(payee, debt.payee)
                && Objects.equals(amount, debt.amount);
    }


}

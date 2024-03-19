package commons;

import jakarta.persistence.*;


@Entity
@Table
public class Debt {

    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    private User payee;
    private Double amount;



    /**
     * constructor for a debt
     * @param owner the id of the payer
     * @param demander the id of the payee
     * @param amount the amount that will be paid
     */
    public Debt(User owner, User demander, Double amount) {
        this.payer = owner;
        this.payee = demander;
        this.amount = amount;
    }

    public Debt() {

    }


    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public User getPayee() {
        return payee;
    }

    public void setPayee(User payee) {
        this.payee = payee;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

package commons;

import jakarta.persistence.*;


@Entity
@Table
public class Debt {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "payer_id", referencedColumnName ="id")
    private long payer_id;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "payer_id", referencedColumnName ="id")
    private long payee_id;
    private Double amount;

    @Id
    private Long id;

    /**
     * constructor for a debt
     * @param owner_id the id of the payer
     * @param demander_id the id of the payee
     * @param amount the amount that will be paid
     */
    public Debt(long owner_id, long demander_id, Double amount) {
        this.payer_id = owner_id;
        this.payee_id = demander_id;
        this.amount = amount;
    }

    public Debt() {

    }


    public long getPayer_id() {
        return payer_id;
    }

    public void setPayer_id(long owner_id) {
        this.payer_id = owner_id;
    }

    public long getPayee_id() {
        return payee_id;
    }

    public void setPayee_id(long demander_id) {
        this.payee_id = demander_id;
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

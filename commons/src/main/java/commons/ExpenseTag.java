package commons;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table
public class ExpenseTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String colour;

    @OneToMany(mappedBy = "expenseTag")
    private List<Expense> expenses;

    /**
     * Empty constructor for the expense tag
     */
    public ExpenseTag()
    {

    }

    /**
     * Constructor for the expense tag
     * @param name
     * @param colour
     */
    public ExpenseTag(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }


    /**
     * set expense tag id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * get expense tag id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for the name of a tag
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the colour of tag
     * @return colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * Setter for the name of a tag
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the colour of a tag
     * @param colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * Equals Method
     * @param o object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseTag that = (ExpenseTag) o;
        return Objects.equals(name, that.name) && Objects.equals(colour, that.colour);
    }

    /**
     * hashCode Method
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, colour, expenses);
    }
}

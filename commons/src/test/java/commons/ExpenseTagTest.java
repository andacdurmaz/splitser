package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseTagTest {

    @Test
    public void testExpenseTagConstructor() {
        ExpenseTag expenseTag = new ExpenseTag("Food", "Red");
        assertEquals("Food", expenseTag.getName());
        assertEquals("Red", expenseTag.getColour());
    }

    @Test
    public void testSetName() {
        ExpenseTag expenseTag = new ExpenseTag("Food", "Red");
        expenseTag.setName("Travel");
        assertEquals("Travel", expenseTag.getName());
    }

    @Test
    public void testSetColour() {
        ExpenseTag expenseTag = new ExpenseTag("Food", "Red");
        expenseTag.setColour("Blue");
        assertEquals("Blue", expenseTag.getColour());
    }

}
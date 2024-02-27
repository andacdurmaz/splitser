package commons;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String title;
    private int amountOfParticipants;

    private final String eventCode;

    /*
    not in use yet, for when the Expense class has been made
     */
    private List<Expense> expenses;
    private int numberOfExpenses;

    private String description;

    /*
      Constructor for the Event class
    */
    public Event(String title, int amountOfParticipants, String eventCode, String description) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
        this.eventCode = eventCode;
        this.numberOfExpenses = 0;
        this.expenses = new ArrayList<>();
        this.description = description;
    }

    /*
    constructor for when no eventcode was specified
     */
    public Event(String title, int amountOfParticipants) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
        this.eventCode = "Temp";
    }

    /*
    gets the title of the event
     */
    public String getTitle() {
        return title;
    }

    /*
    lets the user set the title for the event
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /*
    shows the amount of participants
     */
    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }

    /*
    lets the user input the amount of participants
     */
    public void setAmountOfParticipants(int amountOfParticipants) {
        this.amountOfParticipants = amountOfParticipants;
    }

    /*
    lets the user see the event code for the event and share it eventually
     */
    public String getEventCode() {
        return eventCode;
    }


    /*
    lets the user change the description of an event
     */
    public void setDescription(String description){
        this.description = description;
    }

    /*
    shows the event description
     */
    public String getDescription(){
        return description;
    }

    /*
    shows all expenses
     */
    public List<Expense> getExpenses() {
        return expenses;
    }

    /*
    shows the number of expenses
     */
    public int getNumberOfExpenses() {
        return numberOfExpenses;
    }

    /*
            lets the user add expenses to the event
             */
    public void addExpense(Expense expense){
        expenses.add(expense);
        numberOfExpenses++;
    }

    /*
    lets the user remove some expenses that the user wants
    throws exception if the expense is not in the event
     */
    public boolean removeExpense(Expense expense) throws Exception {
        if(!expenses.contains(expense)){
            throw new Exception("This expense does not exist");
        }
        return expenses.remove(expense);
    }

    /*
    edits or sets the expense based on the oldExpense index
    the newExpense will have the same location as the oldExpense
    throws exception if the expense is not in the event
    returns the newExpense
     */
    public Expense setExpense(Expense oldExpense, Expense newExpense) throws Exception {
        if(!expenses.contains(oldExpense)){
            throw new Exception("This expense does not exist");
        }
        int index = expenses.indexOf(oldExpense);
        expenses.set(index, newExpense);
        return newExpense;
    }
}

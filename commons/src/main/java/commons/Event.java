package commons;

public class Event {
    private String title;
    private int amountOfParticipants;

    private String eventCode;

    /*
    not in use yet, for when the Expense class has been made
     */
//    private List<Expense> Expenses;
    private int numberOfExpenses;

    /*
      Constructor for the Event class
    */
    public Event(String title, int amountOfParticipants, String eventCode) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
        this.eventCode = eventCode;
        numberOfExpenses = 0;
    }


    public Event(String title, int amountOfParticipants) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
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
//
//    public void addExpense(Expense expense){
//        expenses.add(expense);
//        numberOfExpenses++;
//    }
//

}

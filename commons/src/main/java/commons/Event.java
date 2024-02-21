package commons;

public class Event {
    private String title;
    private int amountOfParticipants;

    /*
    not in use yet, for when the Expense class has been made
     */
    //private List<Expense> Expenses;

    /*
    Constructor for the Event class
    @param title, amountOfParticipants
     */
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
}

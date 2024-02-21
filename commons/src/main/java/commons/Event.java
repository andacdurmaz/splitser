package commons;

public class Event {
    private String title;
    private int amountOfParticipants;

    /*
    Change to Expenses class whenever that has been made
     */
    private String expense;


    public Event(String title, int amountOfParticipants, String expense) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
        this.expense = expense;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }

    public void setAmountOfParticipants(int amountOfParticipants) {
        this.amountOfParticipants = amountOfParticipants;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }
}

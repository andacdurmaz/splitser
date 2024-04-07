package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;

import java.util.List;

public class StatisticsCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private ObservableList<Data> data;

    @FXML
    private PieChart pieChart;
    @FXML
    private Label eventTitle;



    /**
     * Adding javadoc for checkstyle
     *
     * @param server   server
     * @param mainCtrl mainCtrl
     */
    @Inject
    public StatisticsCtrl(ServerUtils server, MainCtrl mainCtrl,Event event) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.event = event;
    }



    /**
     * Set event
     * @param event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     *
     * @return
     */
    private void setPieChart(Event event) {
        data = FXCollections.observableArrayList();
        List<Expense> expenses = event.getExpenses();
        for(Expense expense : expenses)
            data.addAll(new PieChart.Data(expense.getExpenseTag().getName(), expense.getAmount()));


    }

    /**
     * set the event title label
     * @param event
     */
    public void updateEventTitle(Event event) {
        if (event != null || event.getTitle().length() != 0)
            eventTitle.setText(event.getTitle());
    }


    /**
     * set the event title and code
     * @param event
     */
    public void setData(Event event) {
        updateEventTitle(event);
        setPieChart(event);
        pieChart.setData(data);
    }


    /**
     *  return back to event info
     */
    public void cancel() {
//        clearFields();
        mainCtrl.showEventInfo(event);
    }


}

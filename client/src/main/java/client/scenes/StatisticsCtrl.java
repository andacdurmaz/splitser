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

    @FXML
    private Label eventTitle;
    @FXML
    private Label eventCode;


    /**
     * Adding javadoc for checkstyle
     *
     * @param server   server
     * @param mainCtrl mainCtrl
     * @param event event
     */
    @Inject
    public StatisticsCtrl(ServerUtils server, MainCtrl mainCtrl, Event event) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.event = event;
    }

    public void initialize() {
        PieChart piechart = new PieChart();
        piechart.setData(getChartData());
    }


    private ObservableList<Data> getChartData() {
        ObservableList<Data> list = FXCollections.observableArrayList();
        List<Expense> expenses = event.getExpenses();
        for(Expense expense : expenses)
            list.addAll(new PieChart.Data(expense.getExpenseTag().getName(), expense.getAmount()));
        return list;
    }

    /**
     * * set the event title label
         * @param event
         */
    public void updateEventTitle(Event event) {
        if (event != null || event.getTitle().length() != 0)
            eventTitle.setText(event.getTitle());
    }


    /**
     * set the event
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     *  return back to event info
     */
    public void cancel() {
//        clearFields();
        mainCtrl.showEventInfo(event);
    }


}

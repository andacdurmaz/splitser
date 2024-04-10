package client.scenes;

import client.services.StatisticsService;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsCtrl {
    private final StatisticsService service;
    private Event event;
    private ObservableList<Data> data;

    @FXML
    private PieChart pieChart;
    @FXML
    private Label eventTitle;



    /**
     * Adding javadoc for checkstyle
     *
     * @param service service
     * @param event  event
     */
    @Inject
    public StatisticsCtrl(StatisticsService service,Event event) {
        this.service = service;
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
     * sets data for the pie chart
     */
    private void setPieChart(Event event) {
        data = FXCollections.observableArrayList();
        List<Expense> expenses = event.getExpenses();
        Map<String, Double> totalExpensesMap = calculateTotalPrices(expenses);

        for (Map.Entry<String, Double> entry : totalExpensesMap.entrySet()) {
            String expenseType = entry.getKey();
            double expenseAmount = entry.getValue();
            data.addAll(new PieChart.Data(expenseType, expenseAmount));
        }
    }

    /**
     * sum prices
     * @param expenses
     * @return a mapped of the expenses of the same type summed together
     */
    public static Map<String, Double> calculateTotalPrices(List<Expense> expenses) {
        Map<String, Double> totalPrices = new HashMap<>();

        for (Expense expense : expenses) {
            String expenseType = expense.getExpenseTag().getName();
            double expenseAmount = expense.getAmount();

            if (totalPrices.containsKey(expenseType)) {
                double currentTotal = totalPrices.get(expenseType);
                totalPrices.put(expenseType, currentTotal + expenseAmount);
            } else {
                totalPrices.put(expenseType, expenseAmount);
            }
        }

        return totalPrices;
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
        service.showEventInfo(event);
    }


}

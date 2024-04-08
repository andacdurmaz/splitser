package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import commons.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;

    private double totalSumOfExpenses = 0;
    private ObservableList<Data> data;

    @FXML
    private PieChart pieChart;
    @FXML
    private Label totalSum;



    /**
     * Adding javadoc for checkstyle
     *
     * @param server   server
     * @param mainCtrl mainCtrl
     * @param event  event
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
     * sets data for the pie chart
     */
    private void setPieChart(Event event) {
        data = FXCollections.observableArrayList();
        List<Expense> expenses = event.getExpenses();
        Map<String, Double> totalExpensesMap = calculateTotalPrices(expenses);

        for (Map.Entry<String, Double> entry : totalExpensesMap.entrySet()) {
            String expenseType = entry.getKey();
            double expenseAmount = entry.getValue();
            totalSumOfExpenses += expenseAmount;
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
     * set the event title and code
     * @param event
     */
    public void setData(Event event) {
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_DRAGGED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "%");
                        }
                    });
        }

        setPieChart(event);
        pieChart.setData(data);
        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.RIGHT);
        totalSum.setText("Total sum of expenses : " + totalSumOfExpenses);

        pieChart.getData().forEach(data ->
        {
            String percentage = String.format("%.2f%%",(data.getPieValue()/totalSumOfExpenses*100));
            Tooltip tooltip = new Tooltip(percentage);
            Tooltip.install(data.getNode(),tooltip);
        });


    }


    /**
     *  return back to event info
     */
    public void cancel() {
//        clearFields();
        mainCtrl.showEventInfo(event);
    }


}

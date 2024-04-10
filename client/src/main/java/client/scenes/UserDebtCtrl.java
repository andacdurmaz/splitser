package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDebtCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private commons.Event event;
    private User user;
    private List<String> instructions = new ArrayList<>();
    @FXML
    private ListView<String> payments;

    /**
     * constructor for the page
     * @param server for accessing the database
     * @param mainCtrl for accessing other pages
     */
    @Inject
    public UserDebtCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * initializes the data of the page
     * @param list stores the payments that will take place
     * @param event of the debts
     * @param user that will pay their debts
     */
    public void setData(List<String> list, Event event, User user) {
        this.user = user;
        this.event = event;
        for (String st : list) {
            Scanner scanner = new Scanner(st);
            int payerId = Integer.parseInt(scanner.next());
            int payeeId = Integer.parseInt(scanner.next());
            double amount = Double.parseDouble(scanner.next());
            String in = "You owe " +
                    server.getUserById(payeeId).getUsername() + " " + amount + " euros";
            if (payerId == user.getUserID())
                this.instructions.add(in);
        }
        payments.getItems().setAll(instructions);
    }

    /**
     * returns to the settle debts page of the event
     * @param actionEvent when the button is clicked
     */
    public void exit(ActionEvent actionEvent) {
        mainCtrl.showSettleDebts(event);
    }
}

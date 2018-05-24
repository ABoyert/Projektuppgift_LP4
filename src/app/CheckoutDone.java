package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Random;

public class CheckoutDone extends AnchorPane {

    @FXML
    Label orderID;

    Random rand;

    public CheckoutDone() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/doneCheckOutElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        orderID.setText("Order-ID: " + getOrderID());
    }

    private String getOrderID() {
        rand = new Random();
        String id = "";

        for (int i = 0; i < 6; i++) {
            id += "" + rand.nextInt(10);
        }

        return id;
    }
}

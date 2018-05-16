package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CheckoutCost extends AnchorPane {
    public CheckoutCost() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/imat_betalning_kostnadpane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}

package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CheckoutOverview extends AnchorPane {
    public CheckoutOverview() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("overviewCheckoutElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}

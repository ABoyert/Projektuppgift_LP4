package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CheckoutDone extends AnchorPane {
    public CheckoutDone() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/doneCheckOutElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}

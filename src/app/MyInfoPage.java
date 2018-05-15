package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MyInfoPage extends AnchorPane {
    public MyInfoPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mina_uppgifter_element.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}

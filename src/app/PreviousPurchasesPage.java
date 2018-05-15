package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PreviousPurchasesPage extends AnchorPane {


    public PreviousPurchasesPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_tidigare_kop_middle.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}

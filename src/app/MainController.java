package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();

    @FXML
    Label testLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testLabel.setText(db.getProduct(87).getName());
    }
}

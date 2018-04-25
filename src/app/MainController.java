package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();

    @FXML
    Label testLabel;
    @FXML
    TextField testTextField;
    @FXML
    ImageView testImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void getTestSearchResult() {
        try {
            int result = Integer.parseInt(testTextField.getCharacters().toString());
            testLabel.setText(db.getProduct(result).getName());
            testImage.setImage(db.getFXImage(db.getProduct(result)));
        }
        catch (NumberFormatException err) {
            testLabel.setText("Inget resultat");
            testImage.setImage(null);
        }
    }
}

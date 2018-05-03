package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();

    @FXML
    StackPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateProducts();
    }

    private void updateProducts() {
        List<Product> allProducts = db.getProducts();

        for (Product p : allProducts) {
            IMatProduct item = new IMatProduct(p, this);
            mainPane.getChildren().add(item);
        }
    }

    /*@FXML
    public void getTestSearchResult() {
        try {
            int result = Integer.parseInt(testTextField.getCharacters().toString());
            testLabel.setText(db.getProduct(result).getName());
            testImage.setImage(db.getFXImage(db.getProduct(result)));
        }
        catch (NumberFormatException | NullPointerException err) {
            testLabel.setText("Inget resultat");
            testImage.setImage(null);
        }
    }*/


}

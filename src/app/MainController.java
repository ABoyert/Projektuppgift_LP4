package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();

    @FXML
    FlowPane mainPane, cartPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateProducts();
    }

    private void updateProducts() {
        List<Product> allProducts = db.getProducts();

        for (Product p : allProducts) {
            IMatProduct item = new IMatProduct(p, this);
            item.numberProduct.setText("0 st");
            item.productPrice.setText(p.getPrice() + " kr");
            item.productName.setText(p.getName());
            item.productImage.setImage(db.getFXImage(p));
            mainPane.getChildren().add(item);
        }
    }

    public void addToCart(Product p, int amount) {
        IMatCartProduct item = new IMatCartProduct(p, this);
        item.cartElementName.setText(p.getName());
        item.cartElementTotalPrice.setText(p.getPrice() * amount + " kr");
        item.cartElementTotalProduct.setText(amount + " st");
        item.cartElementImage.setImage(db.getFXImage(p));
        cartPane.getChildren().add(item);
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

package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart;

    @FXML
    FlowPane mainPane, cartPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shoppingCart = new ShoppingCart();
        updateProducts();
    }

    private void updateProducts() {
        mainPane.getChildren().clear();
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

    public void updateCart() {
        cartPane.getChildren().clear();

        for (ShoppingItem shopItem : shoppingCart.getItems()) {
            IMatCartProduct cartItem = new IMatCartProduct(shopItem, this);
            cartPane.getChildren().add(cartItem);
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

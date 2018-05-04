package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart;

    @FXML
    FlowPane mainPane, cartPane;
    @FXML
    TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        shoppingCart = db.getShoppingCart();
        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateCart();
            }
        });
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

    @FXML
    public void getSearchResult() {
        try {
            String result = searchBar.getText();
            List<Product> searchResult = db.findProducts(result);
        }
        catch (NumberFormatException | NullPointerException err) {

        }
    }


}

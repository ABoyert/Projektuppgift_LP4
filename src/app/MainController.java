package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart;
    private Map<String, IMatProduct> productItemMap = new HashMap<String, IMatProduct>();

    @FXML
    FlowPane mainPane, cartPane;
    @FXML
    TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillProductMap();
        shoppingCart = db.getShoppingCart();
        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateCart();
            }
        });

        updateProducts(db.getProducts(), true); //Show all products on start
    }

    private void updateProducts(List<Product> productList, boolean setInfo) {
        mainPane.getChildren().clear();

        for (Product p : productList) {
            IMatProduct item = productItemMap.get(p.getName());
            if (setInfo) {
                item.numberProduct.setText("0 st");
                item.productPrice.setText(p.getPrice() + " kr");
                item.productName.setText(p.getName());
                item.productImage.setImage(db.getFXImage(p));
            }
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
    private void getSearchResult() {
        try {
            String result = searchBar.getText();
            List<Product> searchResult = db.findProducts(result);
            updateProducts(searchResult, false);
        }
        catch (NumberFormatException | NullPointerException err) {
            System.out.println("Error with search!");
        }
    }

    private void fillProductMap() {
        for (Product p : db.getProducts()) {
            IMatProduct item = new IMatProduct(p, this);
            productItemMap.put(p.getName(), item);
        }
    }
}

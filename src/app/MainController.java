package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart;
    private Map<String, IMatProduct> productItemMap = new HashMap<String, IMatProduct>();
    List<Product> currentProducts;
    enum Sort {
        ALPHABETICAL,
        PRICE,
        NONE;
    }

    @FXML
    FlowPane mainPane, cartPane;
    @FXML
    TextField searchBar;
    @FXML
    Button emptyCart;
    @FXML
    Label cartTotal, cartProducts;

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

        updateProducts(db.getProducts(), Sort.NONE); //Show all products on start
    }

    private void updateProducts(List<Product> productList, Sort sort) {
        mainPane.getChildren().clear();
        currentProducts = productList;

        List<Product> list = null;

        switch(sort) {
            case NONE:
                list = productList;
                break;
            case PRICE:
                list = sortPrice(productList);
                break;
            case ALPHABETICAL:
                list = sortAlphabetical(productList);
                break;
        }

        for (Product p : list) {
            IMatProduct item = productItemMap.get(p.getName());
            mainPane.getChildren().add(item);
        }
    }

    public void updateCart() {
        cartPane.getChildren().clear();

        cartTotal.setText("Totalt: " + Math.round(shoppingCart.getTotal() * 100) / 100 + " kr");
        cartProducts.setText("Antal varor: " + countCartProducts() + " st");

        List<ShoppingItem> cart = shoppingCart.getItems();
        Collections.reverse(cart); //show most recently added item first

        for (ShoppingItem shopItem : cart) {
            IMatCartProduct cartItem = new IMatCartProduct(shopItem, this);
            cartPane.getChildren().add(cartItem);
        }
    }

    @FXML
    private void clearCart() {
        shoppingCart.clear();
    }

    @FXML
    private void sortPricePressed() {
        updateProducts(currentProducts, Sort.PRICE);
    }

    @FXML
    private void sortAlphabeticalPressed() {
        updateProducts(currentProducts, Sort.ALPHABETICAL);
    }

    @FXML
    private void getSearchResult() {
        try {
            String result = searchBar.getText();
            List<Product> searchResult = db.findProducts(result);
            updateProducts(searchResult, Sort.NONE);
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

    private int countCartProducts() {
        int n = 0;

        for (ShoppingItem shopItem : shoppingCart.getItems()) {
            n += shopItem.getAmount();
        }

        return n;
    }

    private List<Product> sortPrice(List<Product> productList) {
        Product[] productArray = new Product[productList.size()];
        productArray = productList.toArray(productArray);

        for (int i = (productArray.length - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (productArray[j - 1].getPrice() > productArray[j].getPrice()) {
                    Product temp = productArray[j - 1];
                    productArray[j - 1] = productArray[j];
                    productArray[j] = temp;
                }
            }
        }

        return Arrays.asList(productArray);
    }

    private List<Product> sortAlphabetical(List<Product> productList) {
        Product[] productArray = new Product[productList.size()];
        productArray = productList.toArray(productArray);

        for (int i = (productArray.length - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (productArray[j - 1].getName().charAt(0) > productArray[j].getName().charAt(0)) {
                    Product temp = productArray[j - 1];
                    productArray[j - 1] = productArray[j];
                    productArray[j] = temp;
                }
            }
        }

        return Arrays.asList(productArray);
    }
}

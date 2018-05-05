package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class IMatProduct extends AnchorPane {
    @FXML
    ImageView productImage;
    @FXML
    Label productName, productPrice, numberProduct, productWeight, productComparePrice;
    @FXML
    Button addProduct, removeProduct;

    private UtilityMethods um = new UtilityMethods();
    private MainController parentController;
    private Product product;
    private int amount = 1;

    public IMatProduct(Product product, MainController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        productWeight.setText("");
        productComparePrice.setText("");
        numberProduct.setText(amount + " st");
        productPrice.setText(product.getPrice() + " " + product.getUnit());
        productName.setText(product.getName());
        productImage.setImage(mainController.db.getFXImage(product));
        this.product = product;
        this.parentController = mainController;
    }

    @FXML
    private void addToCart() {
        if (!isInCart(product)) {
            ShoppingItem shopItem = new ShoppingItem(product);
            shopItem.setAmount(amount);
            parentController.shoppingCart.addItem(shopItem);
        } else {
            ShoppingItem item = getProductsShoppingItem(product);
            item.setAmount(item.getAmount() + amount);
            parentController.updateCart();
        }
    }

    @FXML
    private void addItem() {
        this.amount++;
        numberProduct.setText(amount + " st");
    }

    @FXML
    private void removeItem() {
        if (amount != 1) {
            this.amount--;
            numberProduct.setText(amount + " st");
        }
    }

    private boolean isInCart(Product p) {
        for(ShoppingItem shopItem : parentController.shoppingCart.getItems()) {
            if(shopItem.getProduct().equals(p)) {
                return true;
            }
        }

        return false;
    }

    private ShoppingItem getProductsShoppingItem(Product p) {
        for(ShoppingItem shopItem : parentController.shoppingCart.getItems()) {
            if(shopItem.getProduct().equals(p)) {
                return shopItem;
            }
        }

        return null;
    }
}

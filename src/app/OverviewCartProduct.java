package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class OverviewCartProduct extends AnchorPane {
    @FXML
    ImageView cartElementImage;
    @FXML
    Label cartElementTotalProduct, cartElementName, cartElementTotalPrice, cartElementWeight;
    @FXML
    Button cartRemoveItem, cartAddItem;

    private MainController parentController;
    private ShoppingItem shoppingItem;

    public OverviewCartProduct(ShoppingItem shoppingItem, MainController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/cartOverviewElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        cartElementWeight.setText(shoppingItem.getProduct().getPrice() + " " + shoppingItem.getProduct().getUnit());
        cartElementName.setText(shoppingItem.getProduct().getName());
        cartElementTotalProduct.setText(shoppingItem.getAmount() + " st");
        cartElementTotalPrice.setText("Totalt: " + (double) Math.round(shoppingItem.getTotal() * 100) / 100 + " kr");
        cartElementImage.setImage(mainController.getSquareImage(mainController.db.getFXImage(shoppingItem.getProduct())));
        this.shoppingItem = shoppingItem;
        this.parentController = mainController;
    }

    @FXML
    private void addItem() {
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        cartElementTotalProduct.setText(shoppingItem.getAmount() + " st");
        parentController.updateCart();
    }

    @FXML
    private void removeItem() {
        if (shoppingItem.getAmount() != 1) {
            shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            cartElementTotalProduct.setText(shoppingItem.getAmount() + " st");
            parentController.updateCart();
        }
    }

    @FXML
    private void clearCart() {
        parentController.shoppingCart.removeItem(shoppingItem);
    }
}

package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class IMatCartProduct extends AnchorPane {
    @FXML
    ImageView cartElementImage;
    @FXML
    Label cartElementTotalProduct, cartElementName, cartElementTotalPrice;

    private MainController parentController;
    private ShoppingItem shoppingItem;

    public IMatCartProduct(ShoppingItem shoppingItem, MainController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_cart_product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        cartElementName.setText(shoppingItem.getProduct().getName());
        cartElementTotalProduct.setText(shoppingItem.getAmount() + " st");
        cartElementTotalPrice.setText(shoppingItem.getTotal() + " kr");
        cartElementImage.setImage(mainController.db.getFXImage(shoppingItem.getProduct()));
        this.shoppingItem = shoppingItem;
        this.parentController = mainController;
    }
}

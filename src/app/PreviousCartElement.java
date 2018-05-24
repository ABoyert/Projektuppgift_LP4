package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class PreviousCartElement extends AnchorPane {

    @FXML
    ImageView previousCartsImage;
    @FXML
    Label previousCartsTotalProduct, previousCartsName, previousCartsPrice, previousCartsWeight;
    @FXML
    Button previousCartsRemoveProduct, previousCartsAddProduct, previousCartsAddToCart;

    private MainController parentController;
    private ShoppingItem shoppingItem;

    public PreviousCartElement(ShoppingItem item, MainController controller){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/previousCartsElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        previousCartsWeight.setText(item.getProduct().getPrice() + " " + item.getProduct().getUnit());
        previousCartsName.setText(item.getProduct().getName());
        previousCartsTotalProduct.setText(item.getAmount() + " st");
        previousCartsPrice.setText("Totalt: " + (double) Math.round(item.getTotal() * 100) / 100 + " kr");
        previousCartsImage.setImage(controller.getSquareImage(controller.db.getFXImage(item.getProduct())));
        this.shoppingItem = item;
        this.parentController = controller;
    }

    public ShoppingItem getShoppingItem() {
        return shoppingItem;
    }
}

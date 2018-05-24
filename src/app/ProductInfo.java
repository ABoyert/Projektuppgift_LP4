package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ProductInfo extends AnchorPane{
    @FXML
    ImageView productImage;
    @FXML
    Label productName, productInfo, productPrice, productAmount;

    private MainController parentController;
    private IMatProduct productElement;
    private Product product;
    private int amount;

    public ProductInfo(MainController parentController, IMatProduct productElement) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/singleProductElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
        this.productElement = productElement;
        this.amount = amount;

        populateView();
    }

    private void populateView() {
        productImage.setImage(parentController.getSquareImage(parentController.db.getFXImage(productElement.product)));
        productName.setText(productElement.product.getName());
        productPrice.setText(productElement.product.getPrice() + productElement.product.getUnit());
        productAmount.setText(productElement.amount + " st");
    }

    @FXML
    public void closeMoreInfo() {
        parentController.middleStack.getChildren().remove(this);
    }

    @FXML
    public void addItem() {
        productElement.addItem();
        populateView();
    }

    @FXML
    public void removeItem() {
        productElement.removeItem();
        populateView();
    }

    @FXML
    public void addToCart() {
        productElement.addToCart();
        populateView();
    }

}

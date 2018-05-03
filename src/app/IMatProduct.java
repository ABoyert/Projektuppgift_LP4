package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class IMatProduct extends AnchorPane {
    @FXML
    ImageView productImage;
    @FXML
    Label productName, productPrice, numberProduct;
    @FXML
    Button addProduct, removeProduct;

    private MainController parentController;
    private Product product;
    private int amount = 0;

    public IMatProduct(Product product, MainController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.parentController = mainController;
    }

    @FXML
    private void addItem() {
        this.amount++;
        numberProduct.setText(amount + " st");
    }

    @FXML
    private void removeItem() {
        this.amount--;
        numberProduct.setText(amount + " st");
    }
}

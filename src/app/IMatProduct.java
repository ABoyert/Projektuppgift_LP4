package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class IMatProduct extends AnchorPane {
    @FXML
    ImageView productImage;
    @FXML
    Label productName, productPrice;

    private MainController parentController;
    private Product product;

    public IMatProduct(Product product, MainController mainController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("imat_product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        productPrice.setText(product.getPrice() + " kr");
        productName.setText(product.getName());
        productImage.setImage(mainController.db.getFXImage(product));
        this.product = product;
        this.parentController = mainController;
    }
}

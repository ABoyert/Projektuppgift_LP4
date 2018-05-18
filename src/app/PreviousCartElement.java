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
    Label previousCartsTotalProduct, previousCartsName, previousElementTotalPrice, previousElementWeight;
    @FXML
    Button previousRemoveProduct, previousRemoveItem, previousAddItem;

    private MainController parentController;
    private ShoppingItem shoppingItem;

    public PreviousCartElement(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/previousCartsElement"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}

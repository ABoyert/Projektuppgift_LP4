package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class CheckoutCost extends AnchorPane {

    @FXML Label productCost, totalCostLabel;

    MainController parentController;

    public CheckoutCost(MainController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/imat_betalning_kostnadpane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
        //productCost = parentController.cartTotal;


    }

    public Label getProductCostLabel() {
        return productCost;
    }

    public Label getTotalCostLabel() {
        return totalCostLabel;
    }

    public void setProductCostLabel(Label productCostLabel) {
        this.productCost = productCostLabel;
    }

    public void setTotalCostLabel(Label totalCostLabel) {
        this.totalCostLabel = totalCostLabel;
    }
}

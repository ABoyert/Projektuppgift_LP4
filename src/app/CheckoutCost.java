package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class CheckoutCost extends AnchorPane {

    @FXML Label productCost, totalCostLabel;
    @FXML
    Button nextButton;

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

    @FXML
    public void goToNext() {
        if (parentController.checkoutState.equals(MainController.CheckoutState.OVERVIEW)) {
            parentController.checkoutOverview.placeOrder();
            //clearFocus();
            //parentController.paymentSteps.get(1).setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");
            System.out.println("TO STEP TWO");
        } else if (parentController.checkoutState.equals(MainController.CheckoutState.INFO)) {
            parentController.checkoutOverview.infoPage.saveInfo();
            //clearFocus();
            //parentController.paymentSteps.get(2).setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");
            System.out.println("TO STEP 3");
        } else if (parentController.checkoutState.equals(MainController.CheckoutState.PAYMENT)) {
            parentController.checkoutOverview.infoPage.cp.saveInfo();
            //clearFocus();
            //parentController.paymentSteps.get(3).setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");
            System.out.println("TO STEP 4");
            nextButton.setText("Tillbaka till butiken");
        } else if (parentController.checkoutState.equals(MainController.CheckoutState.DONE)) {
            parentController.shopButtonPressed();
            clearFocus();
            parentController.checkoutState = MainController.CheckoutState.OVERVIEW;
            nextButton.setText("Nästa");
        }

        else {
            System.out.println("FAIL");
        }
    }

    private void clearFocus() {
        for (IMatCategoryElement c : parentController.paymentSteps) {
            c.setStyle("");
        }
    }
}

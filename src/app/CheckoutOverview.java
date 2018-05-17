package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class CheckoutOverview extends AnchorPane {
    @FXML
    FlowPane overviewCheckOutFlowPane;

    MainController parentController;

    public CheckoutOverview(MainController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/overviewCheckoutElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;

        updateView();
    }

    @FXML
    public void placeOrder() {
        if (parentController.shoppingCart.getTotal() != 0) {
            parentController.db.placeOrder();
        }
        parentController.shoppingCart.clear();
        updateView();
    }

    public void updateView() {
        overviewCheckOutFlowPane.getChildren().clear();

        for (ShoppingItem item : parentController.shoppingCart.getItems()) {
            OverviewCartProduct op = new OverviewCartProduct(item, parentController);
            overviewCheckOutFlowPane.getChildren().add(op);
        }
    }
}

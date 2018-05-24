package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.ShoppingItem;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreviousPurchasesPage extends AnchorPane {


    private MainController controller;

    @FXML
    FlowPane recentFlowPane;
    @FXML
    Label recentCartLabel;

    List<PreviousCartElement> previousCartElements = new ArrayList<>();

    @FXML
    Button addRecentToCart;

    private MainController parentController;

    public PreviousPurchasesPage(MainController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/imat_tidigare_kop_middle.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
    }

    public FlowPane getRecentFlowPane(){
        return recentFlowPane;
    }

    public List<PreviousCartElement> getPreviousCartElements() {
        return previousCartElements;
    }

    public void addPreviousCartElements(PreviousCartElement previousCartElement) {
        previousCartElements.add(previousCartElement);
    }

    public void setRecentCartLabel(String dateLabel) {
        recentCartLabel.setText(dateLabel);
    }

    @FXML
    public void addPrevCart(){

            parentController.addToCartFromPrevious();

    }


}

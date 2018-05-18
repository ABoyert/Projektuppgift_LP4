package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class PreviousPurchasesPage extends AnchorPane {


    private MainController controller;

    @FXML
    FlowPane recentFlowPane;
    @FXML
    Label recentCartLabel;
    List<PreviousCartElement> previousCartElements;

    public PreviousPurchasesPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/imat_tidigare_kop_middle.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
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

    public void addRecentToCart(){
        for (PreviousCartElement cartElement: getPreviousCartElements()
             ) {
            cartElement.getShoppingItem();

        }
    }
}

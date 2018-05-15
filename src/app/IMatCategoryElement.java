package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class IMatCategoryElement extends AnchorPane {

    @FXML
    Label textLabelCategory;

    private MainController controller;

    public IMatCategoryElement (MainController c, String category){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("categoryElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.controller = c;
        textLabelCategory.setText(category);
    }


}




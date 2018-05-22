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
    private String category;

    public IMatCategoryElement (MainController c, String category){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/categoryElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.controller = c;
        this.category = category;
        textLabelCategory.setText("   " + toBetterText(category));
    }

    @FXML
    public void buttonPressed() {
        controller.categoryPressed("   " + category);
        //System.out.println(textLabelCategory.getText().toString());
    }

    private String toBetterText(String s) {
        switch (s) {
            case "FRUKT_GRONT":
                return "Frukt och grönt";
            case "BROD":
                return "Bröd";
            case "DRYCKER":
                return "Drycker";
            case "MJOLKPRODUKTER":
                return "Mjölkprodukter";
            case "FISK":
                return "Fisk";
            case "SKAFFERI":
                return "Skafferi";
            case "KOTT":
                return "Kött";
            case "PASTA":
                return "Pasta";
            case "POTATIS_RIS":
                return "Potatis och ris";
            case "SOTSAKER":
                return "Sötsaker";
        }

        return s;
    }

    public Label getTextLabelCategory() {
        return textLabelCategory;
    }

    public String getCategory() {
        return category;
    }
}




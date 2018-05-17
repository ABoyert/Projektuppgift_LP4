package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MyInfoPage extends AnchorPane {
    @FXML
    Button myDetailsSaveButtonTop;
    @FXML
    TextArea myDetailsFirstName, myDetailsLastName, myDetailsPhoneNumber, myDetailsEmail, myDetailsAddress,
            myDetailsZIPCode, myDetailsCity;

    MainController parentController;

    public MyInfoPage(MainController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/mina_uppgifter_element.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;

        myDetailsFirstName.setText(parentController.customer.getFirstName());
        myDetailsLastName.setText(parentController.customer.getLastName());
        myDetailsPhoneNumber.setText(parentController.customer.getPhoneNumber());
        myDetailsEmail.setText(parentController.customer.getEmail());
        myDetailsAddress.setText(parentController.customer.getAddress());
        myDetailsZIPCode.setText(parentController.customer.getPostCode());
        myDetailsCity.setText(parentController.customer.getPostAddress());
    }

    @FXML
    public void saveInfo() {
        if (isAlpha(myDetailsFirstName.getText()))
            parentController.customer.setFirstName(myDetailsFirstName.getText());
        else
            myDetailsFirstName.setText("Förnamn får bara innehålla bokstäver!");

        if (isAlpha(myDetailsLastName.getText()))
            parentController.customer.setLastName(myDetailsLastName.getText());
        else
            myDetailsLastName.setText("Efternamn får bara innehålla bokstäver!");

        if (isNumber(myDetailsPhoneNumber.getText()))
            parentController.customer.setPhoneNumber(myDetailsPhoneNumber.getText());
        else
            myDetailsPhoneNumber.setText("Telefonnummer får bara innehålla siffror! (Till exempel 070123456, inte 070-123456)");

        parentController.customer.setEmail(myDetailsEmail.getText());
        parentController.customer.setAddress(myDetailsAddress.getText());

        if (isNumber(myDetailsZIPCode.getText()))
            parentController.customer.setPostCode(myDetailsZIPCode.getText());
        else
            myDetailsZIPCode.setText("Postnummer får bara innehålla siffror!");

        if (isAlpha(myDetailsCity.getText()))
            parentController.customer.setPostAddress(myDetailsCity.getText());
        else
            myDetailsCity.setText("Stad får bara innehålla bokstäver!");
    }

    private boolean isNumber(String s) {
        char[] chars = s.toCharArray();

        for (char c : chars) {
            if(!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    private boolean isAlpha(String s) {
        char[] chars = s.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
}

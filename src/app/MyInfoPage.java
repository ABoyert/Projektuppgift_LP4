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
        parentController.customer.setFirstName(myDetailsFirstName.getText());
        parentController.customer.setLastName(myDetailsLastName.getText());
        parentController.customer.setPhoneNumber(myDetailsPhoneNumber.getText());
        parentController.customer.setEmail(myDetailsEmail.getText());
        parentController.customer.setAddress(myDetailsAddress.getText());
        parentController.customer.setPostCode(myDetailsZIPCode.getText());
        parentController.customer.setPostAddress(myDetailsCity.getText());
    }
}

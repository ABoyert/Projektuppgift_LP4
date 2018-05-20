package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckoutInfo extends AnchorPane {

    private MainController parentController;
    private MyInfoPage infoPage;

    @FXML
    TextArea myDetailsFirstName, myDetailsLastName, myDetailsPhoneNumber, myDetailsEmail, myDetailsAddress,
            myDetailsZIPCode, myDetailsCity;

    List<TextArea> textAreaList = new ArrayList<TextArea>();

    public CheckoutInfo(MainController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/myInformationCheckOutElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
        this.infoPage = new MyInfoPage(parentController);

        myDetailsFirstName.setText(parentController.customer.getFirstName());
        myDetailsLastName.setText(parentController.customer.getLastName());
        myDetailsPhoneNumber.setText(parentController.customer.getPhoneNumber());
        myDetailsEmail.setText(parentController.customer.getEmail());
        myDetailsAddress.setText(parentController.customer.getAddress());
        myDetailsZIPCode.setText(parentController.customer.getPostCode());
        myDetailsCity.setText(parentController.customer.getPostAddress());


        UtilityMethods.takeIntegersOnly(myDetailsPhoneNumber);
        UtilityMethods.takeIntegersOnly(myDetailsZIPCode);
        UtilityMethods.takeLettersOnly(myDetailsFirstName);
        UtilityMethods.takeLettersOnly(myDetailsLastName);
        UtilityMethods.takeLettersOnly(myDetailsCity);
        //UtilityMethods.takeIntegersOnly(mySocialSecurityNumber);

        fillTextAreaList();
    }

    @FXML
    public void saveInfo() {
        verifyInfo();

        if (isAllInfoEntered("") && isAllInfoEntered("OGILTIG INFORMATION!")) {
            System.out.println("GO TO PAYMENT!");
            CheckoutPayment cp = new CheckoutPayment(parentController);
            parentController.middleStack.getChildren().add(cp);
            cp.toFront();
        } else if (!isAllInfoEntered("")){
            infoPage.showErrorPopup("Kontrollera så att alla fälten innehåller korrekt information!");
        }
    }

    private void verifyInfo() {
        if (infoPage.isAlpha(myDetailsFirstName.getText()))
            parentController.customer.setFirstName(myDetailsFirstName.getText());
        else {
            myDetailsFirstName.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Förnamn får bara innehålla bokstäver!");

        }

        if (infoPage.isAlpha(myDetailsLastName.getText()))
            parentController.customer.setLastName(myDetailsLastName.getText());
        else {
            myDetailsLastName.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Efternamn får bara innehålla bokstäver!");

        }

        if (infoPage.isNumber(myDetailsPhoneNumber.getText()))
            parentController.customer.setPhoneNumber(myDetailsPhoneNumber.getText());
        else {
            myDetailsPhoneNumber.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Telefonnummer får bara innehålla siffror! \n(Till exempel 070123456, inte 070-123456)");

        }

        parentController.customer.setEmail(myDetailsEmail.getText());
        parentController.customer.setAddress(myDetailsAddress.getText());

        if (infoPage.isNumber(myDetailsZIPCode.getText()))
            parentController.customer.setPostCode(myDetailsZIPCode.getText());
        else {
            myDetailsZIPCode.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Postnummer får bara innehålla siffror!");

        }

        if (infoPage.isAlpha(myDetailsCity.getText()))
            parentController.customer.setPostAddress(myDetailsCity.getText());
        else {
            myDetailsCity.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Stad får bara innehålla bokstäver!");

        }
    }

    private boolean isAllInfoEntered(String s) {
        for (TextArea ta : textAreaList) {
            if (ta.getText().equals(s)) {
                return false;
            }
        }

        return true;
    }

    private void fillTextAreaList() {
        textAreaList.add(myDetailsFirstName);
        textAreaList.add(myDetailsLastName);
        textAreaList.add(myDetailsPhoneNumber);
        textAreaList.add(myDetailsEmail);
        textAreaList.add(myDetailsAddress);
        textAreaList.add(myDetailsZIPCode);
        textAreaList.add(myDetailsCity);
    }
}

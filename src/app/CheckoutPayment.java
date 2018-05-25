package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheckoutPayment extends AnchorPane {
    MainController parentController;
    MyInfoPage infoPage;
    CheckoutDone cd;

    @FXML
    TextArea cardNumber, cardName, cardCVC, cardMonth, cardYear;
    @FXML
    Button cardVisa, cardMastercard, cardPay;

    List<TextArea> textAreaList = new ArrayList<TextArea>();

    public CheckoutPayment(MainController parentController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/paymentMethodCheckOut.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parentController = parentController;
        this.infoPage = new MyInfoPage(parentController);

        cardPay.setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");

        cardName.setText(parentController.creditCard.getHoldersName());
        cardMonth.setText(String.valueOf(parentController.creditCard.getValidMonth()));
        cardYear.setText(String.valueOf(parentController.creditCard.getValidYear()));
        cardNumber.setText(parentController.creditCard.getCardNumber());
        cardCVC.setText(String.valueOf(parentController.creditCard.getVerificationCode()));

        UtilityMethods.takeIntegersOnly(cardCVC);
        UtilityMethods.takeIntegersOnly(cardNumber);
        UtilityMethods.takeIntegersOnly(cardMonth);
        UtilityMethods.takeIntegersOnly(cardYear);
        UtilityMethods.takeLettersOnly(cardName);

        fillTextAreaList();
    }

    private void verifyInfo() {
        if (infoPage.isNumber(cardNumber.getText()))
            parentController.creditCard.setCardNumber(cardNumber.getText());
        else {
            cardNumber.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Kortnummer får bara innehålla siffror!");
        }

        if (infoPage.isAlpha(cardName.getText()))
            parentController.creditCard.setHoldersName(cardName.getText());
        else {
            cardName.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Namn på kortinnehavare får bara innehålla bokstäver!");
        }

        if (infoPage.isNumber(cardCVC.getText()))
            parentController.creditCard.setVerificationCode(Integer.parseInt(cardCVC.getText()));
        else {
            cardCVC.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Detta fält får bara innehålla siffror!");
        }

        if (infoPage.isNumber(cardMonth.getText()))
            parentController.creditCard.setValidMonth(Integer.parseInt(cardMonth.getText()));
        else {
            cardMonth.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Detta fält får bara innehålla siffror!");
        }

        if (infoPage.isNumber(cardYear.getText()))
            parentController.creditCard.setValidYear(Integer.parseInt(cardYear.getText()));
        else {
            cardYear.setText("OGILTIG INFORMATION!");
            infoPage.showErrorPopup("Detta fält får bara innehålla siffror!");
        }
    }

    private boolean isAllInfoEntered(String s) {
        for (javafx.scene.control.TextArea ta : textAreaList) {
            if (ta.getText().equals(s)) {
                return false;
            }
        }

        return true;
    }

    @FXML
    public void setCardVisa() {
        parentController.creditCard.setCardType("Visa");
    }

    @FXML
    public void setCardMastercard() {
        parentController.creditCard.setCardType("Mastercard");
    }

    @FXML
    public void saveInfo() {
        verifyInfo();

        if (isAllInfoEntered("") && isAllInfoEntered("OGILTIG INFORMATION!")) {
            finishCheckout();
        } else if (!isAllInfoEntered("")){
            infoPage.showErrorPopup("Kontrollera så att alla fälten innehåller korrekt information!");
        }
    }

    @FXML
    public void directPay() {
        clearFocus();
        parentController.paymentSteps.get(3).setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");
        finishCheckout();
    }

    private void clearFocus() {
        for (IMatCategoryElement c : parentController.paymentSteps) {
            c.setStyle("");
        }
    }

    private void finishCheckout() {
        System.out.println("GO TO LAST STEP!");
        cd = new CheckoutDone(parentController);
        parentController.middleStack.getChildren().add(cd);
        cd.toFront();
        parentController.db.placeOrder(true);
        parentController.checkoutState = MainController.CheckoutState.DONE;
    }

    private void fillTextAreaList() {
        textAreaList.add(cardCVC);
        textAreaList.add(cardMonth);
        textAreaList.add(cardName);
        textAreaList.add(cardYear);
        textAreaList.add(cardNumber);
    }
}

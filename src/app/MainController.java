package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import se.chalmers.cse.dat216.project.*;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable{
    IMatDataHandler db = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart;
    HelpPage helpPage;
    MyInfoPage infoPage;
    PreviousPurchasesPage prevPage;
    private Map<String, IMatProduct> productItemMap = new HashMap<String, IMatProduct>();
    List<Product> currentProducts;
    enum Sort {
        ALPHABETICAL,
        PRICE,
        NONE;
    }
    boolean sortPricePressed = false;
    boolean sortAlphaPressed = false;
    // Sätta programmet i olika states beroende på vilken kategori man är i?
    enum States{
        HANDLA,
        MINA_UPPGIFTER,
        HJALP,
        TIDIGARE_KÖP;
    }
    @FXML
    FlowPane mainPane, cartPane;
    @FXML
    TextField searchBar;
    @FXML
    Button emptyCart, helpButton;
    @FXML
    Label cartTotal, cartProducts, Left_panel_label;
    @FXML
    ImageView Left_panel_picture;
    @FXML
    StackPane middleStack;
    @FXML
    AnchorPane categoryTab, shopPage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillProductMap();
        shoppingCart = db.getShoppingCart();
        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateCart();
            }

        });
        updateCart();
        helpPage = new HelpPage();
        infoPage = new MyInfoPage();
        prevPage = new PreviousPurchasesPage();
        updateProducts(db.getProducts(), Sort.NONE); //Show all products on start
    }

    private void updateProducts(List<Product> productList, Sort sort) {
        mainPane.getChildren().clear();
        currentProducts = productList;

        List<Product> list = null;

        switch(sort) {
            case NONE:
                list = productList;
                break;
            case PRICE:
                list = sortPrice(productList);
                break;
            case ALPHABETICAL:
                list = sortAlphabetical(productList);
                break;
        }

        for (Product p : list) {
            IMatProduct item = productItemMap.get(p.getName());
            mainPane.getChildren().add(item);
        }
    }

    public void updateCart() {
        cartPane.getChildren().clear();

        cartTotal.setText("Totalt: " + Math.round(shoppingCart.getTotal() * 100) / 100 + " kr");
        cartProducts.setText("Antal varor: " + countCartProducts() + " st");

        List<ShoppingItem> cart = shoppingCart.getItems();

        for (int i = cart.size() - 1; i >= 0; i--) {
            IMatCartProduct cartItem = new IMatCartProduct(cart.get(i), this);
            cartPane.getChildren().add(cartItem);
        }
    }

    @FXML
    private void clearCart() {
        shoppingCart.clear();
    }

    @FXML
    private void sortPricePressed() {
        updateProducts(currentProducts, Sort.PRICE);
    }

    @FXML
    private void sortAlphabeticalPressed() {
        updateProducts(currentProducts, Sort.ALPHABETICAL);
    }

    @FXML
    private void getSearchResult() {
        try {
            String result = searchBar.getText();
            List<Product> searchResult = db.findProducts(result);
            updateProducts(searchResult, Sort.NONE);
        }
        catch (NumberFormatException | NullPointerException err) {
            System.out.println("Error with search!");
        }
    }

    private void fillProductMap() {
        for (Product p : db.getProducts()) {
            IMatProduct item = new IMatProduct(p, this);
            productItemMap.put(p.getName(), item);
        }
    }

    private int countCartProducts() {
        int n = 0;

        for (ShoppingItem shopItem : shoppingCart.getItems()) {
            n += shopItem.getAmount();
        }

        return n;
    }

    private List<Product> sortPrice(List<Product> productList) {
        Product[] productArray = new Product[productList.size()];
        productArray = productList.toArray(productArray);
        boolean buttonStatus;
        
        for (int i = (productArray.length - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (!sortPricePressed) {
                    buttonStatus = (productArray[j - 1].getPrice() > productArray[j].getPrice());
                } else {
                    buttonStatus = (productArray[j - 1].getPrice() < productArray[j].getPrice());
                }
                if (buttonStatus) {
                    Product temp = productArray[j - 1];
                    productArray[j - 1] = productArray[j];
                    productArray[j] = temp;
                }
            }
        }
        
        sortPricePressed = !sortPricePressed;
        return Arrays.asList(productArray);
    }

    private List<Product> sortAlphabetical(List<Product> productList) {
        Product[] productArray = new Product[productList.size()];
        productArray = productList.toArray(productArray);
        boolean buttonStatus;

        for (int i = (productArray.length - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (!sortAlphaPressed) {
                    buttonStatus = (productArray[j - 1].getName().charAt(0) > productArray[j].getName().charAt(0));
                } else {
                    buttonStatus = (productArray[j - 1].getName().charAt(0) < productArray[j].getName().charAt(0));
                }
                if (buttonStatus) {
                    Product temp = productArray[j - 1];
                    productArray[j - 1] = productArray[j];
                    productArray[j] = temp;
                }
            }
        }
        
        sortAlphaPressed = !sortAlphaPressed;
        return Arrays.asList(productArray);
    }

    public Image getSquareImage(Image image){

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        if(image.getWidth() > image.getHeight()){
            width = (int) image.getHeight();
            height = (int) image.getHeight();
            x = (int)(image.getWidth() - width)/2;
            y = 0;
        }

        else if(image.getHeight() > image.getWidth()){
            width = (int) image.getWidth();
            height = (int) image.getWidth();
            x = 0;
            y = (int) (image.getHeight() - height)/2;
        }

        else{
            //Width equals Height, return original image
            return image;
        }
        return new WritableImage(image.getPixelReader(), x, y, width, height);
    }


    @FXML
    public void previousPurchasesButtonPressed() {
        middleStack.getChildren().clear();

        if (middleStack.getChildren().contains(prevPage)) {
            prevPage.toFront();
        } else {
            middleStack.getChildren().add(prevPage);
        }
    }

    @FXML
    public void helpButtonPressed() {
        middleStack.getChildren().clear();

            if (middleStack.getChildren().contains(helpPage)) {
                helpPage.toFront();
            } else {
                middleStack.getChildren().add(helpPage);
            }
    }

    @FXML
    public void myInfoButtonPressed() {
        middleStack.getChildren().clear();

        if (middleStack.getChildren().contains(infoPage)) {
            infoPage.toFront();
        } else {
            middleStack.getChildren().add(infoPage);
        }
    }

    @FXML
    public void shopButtonPressed() {
        middleStack.getChildren().clear();
        middleStack.getChildren().add(shopPage);
        categoryTab.toFront();
        shopPage.toFront();
    }
}

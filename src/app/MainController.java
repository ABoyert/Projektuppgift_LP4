package app;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

public class MainController implements Initializable {
    IMatDataHandler db = IMatDataHandler.getInstance();
    ShoppingCart shoppingCart;
    HelpPage helpPage;
    CreditCard creditCard;
    MyInfoPage infoPage;
    CheckoutOverview checkoutOverview;
    PreviousPurchasesPage prevPage;

    private Map<String, IMatProduct> productItemMap = new HashMap<String, IMatProduct>();
    List<Product> currentProducts;
    Customer customer;

    enum Sort {
        ALPHABETICAL,
        PRICE,
        NONE
    }

    boolean sortPricePressed = false;
    boolean sortAlphaPressed = false;
    // Sätta programmet i olika states beroende på vilken kategori man är i?

    List<String> categoryStringList = new ArrayList<String>();
    List<IMatCategoryElement> categoryElements = new ArrayList<>();
    List<IMatCategoryElement> paymentSteps = new ArrayList<>();
    List<IMatCategoryElement> orders = new ArrayList<>();

    int git_suger = 0;

    enum States {
        HANDLA,
        MINA_UPPGIFTER,
        HJALP,
        TIDIGARE_KÖP
    }

    @FXML
    FlowPane mainPane, cartPane, leftPane;
    @FXML
    TextField searchBar;
    @FXML
    Button emptyCart, helpButton, goToKassa, sortAlphaButton;
    @FXML
    Label cartTotal, cartProducts, Left_panel_label;
    @FXML
    ImageView Left_panel_picture;
    @FXML
    StackPane middleStack, rightStack;
    @FXML
    AnchorPane categoryTab, shopPage;

    CheckoutCost cc;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillProductMap();
        shoppingCart = db.getShoppingCart();
        customer = db.getCustomer();
        creditCard = db.getCreditCard();
        shoppingCart.addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                updateCart();
            }

        });
        updateCart();
        helpPage = new HelpPage();
        infoPage = new MyInfoPage(this);
        prevPage = new PreviousPurchasesPage();
        checkoutOverview = new CheckoutOverview(this);
        updateProducts(db.getProducts(), Sort.NONE); //Show all products on start
        cc = new CheckoutCost();
        System.out.println(cc.toString());
        rightStack.getChildren().add(cc);
        createPaymentSteps();
        cc.toBack();
        UtilityMethods.takeLettersOnlyField(searchBar);

        createCategoryList();
        loadCategories();
    }

    private void updateProducts(List<Product> productList, Sort sort) {
        mainPane.getChildren().clear();
        currentProducts = productList;

        List<Product> list = null;

        switch (sort) {
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

        cartTotal.setText("Totalt: " + (double) Math.round(shoppingCart.getTotal() * 100) / 100 + " kr");
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
        } catch (NumberFormatException | NullPointerException err) {
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
                    sortAlphaButton.setText("A - Ö");
                    buttonStatus = (productArray[j - 1].getName().charAt(0) > productArray[j].getName().charAt(0));
                } else {
                    sortAlphaButton.setText("Ö - A");
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

    public Image getSquareImage(Image image) {

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;

        if (image.getWidth() > image.getHeight()) {
            width = (int) image.getHeight();
            height = (int) image.getHeight();
            x = (int) (image.getWidth() - width) / 2;
            y = 0;
        } else if (image.getHeight() > image.getWidth()) {
            width = (int) image.getWidth();
            height = (int) image.getWidth();
            x = 0;
            y = (int) (image.getHeight() - height) / 2;
        } else {
            //Width equals Height, return original image
            return image;
        }
        return new WritableImage(image.getPixelReader(), x, y, width, height);
    }


    @FXML
    public void previousPurchasesButtonPressed() {
        clearCategories();
        middleStack.getChildren().clear();
        hideCost();
        loadPreviousPurchaseDates();
        setLeftLabelPreviousPurchase();
        if (middleStack.getChildren().contains(prevPage)) {
            prevPage.toFront();
        } else {
            middleStack.getChildren().add(prevPage);
        }
    }

    @FXML
    public void helpButtonPressed() {

        clearCategories();
        middleStack.getChildren().clear();
        hideCost();
        setLeftLabelHelp();

        if (middleStack.getChildren().contains(helpPage)) {
            helpPage.toFront();
        } else {
            middleStack.getChildren().add(helpPage);
        }
    }

    @FXML
    public void myInfoButtonPressed() {
        clearCategories();
        middleStack.getChildren().clear();
        hideCost();
        setLeftLabelMyInformation();

        infoPage = new MyInfoPage(this);

        if (middleStack.getChildren().contains(infoPage)) {
            infoPage.toFront();
        } else {
            middleStack.getChildren().add(infoPage);
        }
    }

    @FXML
    public void shopButtonPressed() {
        setLeftLabelVaror();
        middleStack.getChildren().clear();
        hideCost();
        setLeftLabelVaror();
        middleStack.getChildren().add(shopPage);
        categoryTab.toFront();
        shopPage.toFront();
        loadCategories();
    }

    public void createCategoryList() {
        List<String> tempCatString = new ArrayList<>();
        List<IMatCategoryElement> tempCategories = new ArrayList<>();
        for (UtilityMethods.Categories cat : UtilityMethods.Categories.values()
                ) {
            tempCatString.add(cat.toString());
        }
        setCategoryStringList(tempCatString);


        for (String cat : getCategoryStringList()
                ) {
            tempCategories.add(new IMatCategoryElement(this, cat));

        }
        setCategoryElements(tempCategories);

    }


    public void loadCategories() {
        clearCategories();
        for (IMatCategoryElement c : getCategoryElements()
                ) {
            leftPane.getChildren().add(c);
        }
    }

    public List<String> getCategoryStringList() {
        return categoryStringList;
    }

    public void setCategoryStringList(List<String> categoryStringList) {
        this.categoryStringList = categoryStringList;
    }

    public List<IMatCategoryElement> getCategoryElements() {
        return categoryElements;
    }

    public void setCategoryElements(List<IMatCategoryElement> categoryElements) {
        this.categoryElements = categoryElements;
    }

    @FXML
    public void goToKassaPressed() {
        if (shoppingCart.getTotal() != 0) {
            middleStack.getChildren().clear();
            Left_panel_label.setText("Betalningssteg");

            middleStack.getChildren().clear();
            middleStack.getChildren().add(new CheckoutOverview(this));
            showPaymentSteps();
            showCost();
        }
    }


    public void showCost() {
        cc.toFront();
    }

    public void hideCost() {
        cc.toBack();
    }

    public void createPaymentSteps() {
        paymentSteps.add(new IMatCategoryElement(this, "1. Översikt"));
        paymentSteps.add(new IMatCategoryElement(this, "2. Uppgifter"));
        paymentSteps.add(new IMatCategoryElement(this, "3. Betala"));
        paymentSteps.add(new IMatCategoryElement(this, "4. Klar"));


    }

    public void showPaymentSteps() {
        clearCategories();
        for (IMatCategoryElement step : paymentSteps
                ) {
            leftPane.getChildren().add(step);

        }
    }
    public void clearCategories(){
        leftPane.getChildren().clear();
    }

    public void setLeftLabelVaror() {
        Left_panel_label.setText("Varor");
    }
    public void setLeftLabelHelp() {
        Left_panel_label.setText("Hjälp");
    }
    public void setLeftLabelMyInformation() {
        Left_panel_label.setText("Mina Uppgifter");
    }
    public void setLeftLabelPreviousPurchase() {
        Left_panel_label.setText("Tidigare Köp");
    }

    public void loadPreviousPurchaseDates() {
        for (Order order : db.getOrders()) {
            IMatCategoryElement ce = new IMatCategoryElement(this, order.getDate().toString());

            leftPane.getChildren().add(ce);
        }
    }

    public void categoryPressed(String label){

        if(middleStack.getChildren().contains(shopPage)){
            switch (label){
                case "   KOTT":
                    System.out.println("Kött borde visas");
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.KOTT), Sort.NONE);
                    break;
                case "   BROD":
                updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.BROD), Sort.NONE);
                break;
                case "   FISK":
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.FISK), Sort.NONE);
                    break;
                case "   SKAFFERI":
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.SKAFFERI), Sort.NONE);
                    break;
                case "   PASTA":
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.PASTA), Sort.NONE);
                    break;
                case "   POTATIS_RIS":
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.POTATIS_RIS), Sort.NONE);
                    break;
                case "   SOTSAKER":
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.SOTSAKER), Sort.NONE);
                    break;
                case "   MJOLKPRODUKTER":
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.MJOLKPRODUKTER), Sort.NONE);
                    break;
                case "   FRUKT_GRONT":
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.FRUKT_GRONT), Sort.NONE);
                    break;
                case "   DRYCKER":
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.DRYCKER), Sort.NONE);
                    break;
            }
        }
        else {
            switch (label){
                case "Betala":
                    System.out.println("Betala borde visas");
                    updateProducts(UtilityMethods.getCategory(UtilityMethods.Categories.KOTT), Sort.NONE);
                    break;
            }

        }
        if(middleStack.getChildren().contains(prevPage)){
            System.out.println("Contains prev page");
            linkOrderToButton(label);
        }



    }

    public void callCheckoutOverviewUpdate() {
        checkoutOverview.updateView();
    }

    public void linkOrderToButton(String label){
        for (Order order : db.getOrders()) {
            String orderLabel = "   " + order.getDate().toString();
            if(label.equals(orderLabel) ){
                prevPage.getRecentFlowPane().getChildren().clear();
                List<ShoppingItem> tmpItems = order.getItems();
                for (ShoppingItem item : tmpItems
                        ) {
                    System.out.println("Adding recent item to flowpane");
                    prevPage.setRecentCartLabel(order.getDate().toString());
                    prevPage.getRecentFlowPane().getChildren().add(new PreviousCartElement(item, this));


                }
            }
        }
    }


}

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
    Button nextButton;

    private Map<String, IMatProduct> productItemMap = new HashMap<String, IMatProduct>();
    List<Product> currentProducts;
    Customer customer;

    enum Sort {
        ALPHABETICAL,
        PRICE,
        NONE
    }

    public enum CheckoutState {
        OVERVIEW,
        INFO,
        PAYMENT,
        DONE
    }

    CheckoutState checkoutState;

    boolean sortPricePressed = false;
    boolean sortAlphaPressed = false;
    // Sätta programmet i olika states beroende på vilken kategori man är i?

    List<String> categoryStringList = new ArrayList<String>();
    List<IMatCategoryElement> categoryElements = new ArrayList<>();
    public List<IMatCategoryElement> paymentSteps = new ArrayList<>();
    List<IMatCategoryElement> orders = new ArrayList<>();
    List<Button> topMenuButtons = new ArrayList<>();

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
    Button emptyCart, helpButton, goToKassa, sortAlphaButton, prevPurchasesButton, shopButton, myInfoButton, sortPriceButton;
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
        prevPage = new PreviousPurchasesPage(this);
        checkoutOverview = new CheckoutOverview(this);
        updateProducts(db.getProducts(), Sort.ALPHABETICAL); //Show all products on start
        cc = new CheckoutCost(this);
        System.out.println(cc.toString());
        rightStack.getChildren().add(cc);
        createPaymentSteps();
        cc.toBack();
        UtilityMethods.takeLettersOnlyField(searchBar);

        topMenuButtons.add(shopButton);
        topMenuButtons.add(myInfoButton);
        topMenuButtons.add(helpButton);
        topMenuButtons.add(prevPurchasesButton);

        topMenuButtonPressed(shopButton);

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

        sortAlphaButton.setStyle("");
        sortPriceButton.setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");

        for (int i = (productArray.length - 1); i >= 0; i--) {
            for (int j = 1; j <= i; j++) {
                if (!sortPricePressed) {
                    buttonStatus = (productArray[j - 1].getPrice() > productArray[j].getPrice());
                    sortPriceButton.setText("Billigast först");
                } else {
                    buttonStatus = (productArray[j - 1].getPrice() < productArray[j].getPrice());
                    sortPriceButton.setText("Dyrast först");
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

        sortPriceButton.setStyle("");
        sortPriceButton.setText("Pris");
        sortAlphaButton.setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");

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

        topMenuButtonPressed(prevPurchasesButton);
        Left_panel_picture.setImage(getSquareImage(new Image("resources/noteBook_1.png")));

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
        topMenuButtonPressed(helpButton);
        Left_panel_picture.setImage(getSquareImage(new Image("resources/questionMarkIcon.png")));


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
        topMenuButtonPressed(myInfoButton);
        Left_panel_picture.setImage(getSquareImage(new Image("resources/profileIcon.png")));
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
        topMenuButtonPressed(shopButton);
        Left_panel_picture.setImage(getSquareImage(new Image("resources/shoppingBag.png")));

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

    public void clearOverview(){
        checkoutOverview.overviewCheckOutFlowPane.getChildren().clear();
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
            Left_panel_picture.setImage(getSquareImage(new Image("resources/walletIcon.png")));
            updateCC();

            for (IMatCategoryElement c : paymentSteps) {
                c.setStyle("");
            }

            paymentSteps.get(0).setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");
            checkoutState = CheckoutState.OVERVIEW;
        }
    }

    public void updateCC(){
        cc.getProductCostLabel().setText("" + shoppingCart.getTotal());
        double total = shoppingCart.getTotal() + 50;
        cc.getTotalCostLabel().setText("" + total);
        cc.nextButton.setText("Nästa");
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
        orders.clear();
        for (Order order : db.getOrders()) {
            orders.add(new IMatCategoryElement(this, replaceDateString(order.getDate().toString())));


        }
        leftPane.getChildren().addAll(orders);
    }



    public void categoryPressed(String label){

        if(middleStack.getChildren().contains(shopPage)){
            switch (label){
                case "   ERBJUDANDEN":
                    System.out.println("Erbjudanden!");
                    mainPane.getChildren().clear();
                    break;
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
            shopButtonPressed();
            categorySetColor(label, categoryElements);
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
            categorySetColor(label, orders);
        }



    }

    public void callCheckoutOverviewUpdate() {
        checkoutOverview.updateView();
    }

    public void linkOrderToButton(String label){
        for (Order order : db.getOrders()) {
            String orderLabel = "   " + replaceDateString(order.getDate().toString());
            if(label.equals(orderLabel) ){
                prevPage.getRecentFlowPane().getChildren().clear();
                List<ShoppingItem> tmpItems = order.getItems();
                prevPage.previousCartElements.clear();
                for (ShoppingItem item : tmpItems
                        ) {

                    prevPage.setRecentCartLabel(replaceDateString(order.getDate().toString()));
                    prevPage.getRecentFlowPane().getChildren().add(new PreviousCartElement(item, this));
                    prevPage.previousCartElements.add(new PreviousCartElement(item, this));


                }
            }
        }
    }

    public void topMenuButtonPressed(Button pressedButton){
        for (Button button: topMenuButtons
             ) {
            button.setStyle("-fx-background-color: #FDC377");
        }

        pressedButton.setStyle("-fx-background-color: #e3a24c ; -fx-border-width: 2px ;-fx-font-weight: bold");

    }

    public void categorySetColor(String label, List<IMatCategoryElement> list){
        for (IMatCategoryElement listItem: list
             ) {

            listItem.setStyle("-fx-background-color: #FDFDFD");

            if(label.equals("   " + listItem.getCategory())){
                listItem.setStyle("-fx-background-color: #E5E5E5");
            }


        }



    }

    public String replaceDateString(String sTemp){
        String s = sTemp.substring(0,16);

        if (s.contains("Thu")) {

            s = s.replaceAll("\\bThu\\b", "TOR");
        }
        if (s.contains("Fri")) {

            s = s.replaceAll("\\bFri\\b", "FRE");
        }
        if (s.contains("Sat")) {
            s = s.replaceAll("(?i)\\bSat\\b", "LÖR");
        }
        if (s.contains("Mon")) {
            s = s.replaceAll("(?i)\\bMon\\b", "MÅN");
        }
        if (s.contains("Tue")) {
            s = s.replaceAll("(?i)\\bTue\\b", "TIS");
        }
        if (s.contains("Wed")) {

            s = s.replaceAll("(?i)\\bWed\\b", "ONS");
        }
        if (s.contains("May")) {
            s = s.replaceAll("(?i)\\bMay\\b", "MAJ");
        }

        System.out.println(s);

        s.replaceAll("THU", "TOR");
        s.replaceAll("FRI", "FRE");
        s.replaceAll("TUE", "TIS");
        s.replaceAll("SAT", "LÖR");
        s.replaceAll("SUN","SÖN");
        s.replaceAll("WED", "ONS");
        s.replaceAll("MAY", "MAJ");
        //s = s.substring(0, s.length()-10);
        //System.out.println(s);



        return s;
    }

    public void addToCartFromPrevious(){
        System.out.println("Inne i add to cart");
        List<ShoppingItem> changeLater = new ArrayList<>();
        List<ShoppingItem> increaseWith = new ArrayList<>();
        List<ShoppingItem> addLater = new ArrayList<>();
        changeLater.clear();
        increaseWith.clear();
        addLater.clear();


        /*
        for (ShoppingItem item: shoppingCart.getItems()
             ) {
            System.out.println("Getting shopping cart items");
            for (ShoppingItem item2: prevPage.getRecentShoppingItems()
                 ) {
                System.out.println("Getting prevPage items");
                if(item.getProduct().equals(item2.getProduct())){
                    System.out.println("Identical items");
                    changeLater.add(item);
                    increaseWith.add(item2);
                    System.out.println("PREVIOUS AMOUNT" + item2.getProduct().toString() + item2.getAmount());
                    item2.getAmount();
                    item2.getAmount();
                    //item.setAmount(item.getAmount()+item2.getAmount());
                }
                else{
                    System.out.println("Added new item");
                    addLater.add(item2);
                    //shoppingCart.addItem(item2);
                }
            }


        }*/

        for (ShoppingItem prevItem: prevPage.getRecentShoppingItems()
                ) {
            if(!shoppingCart.getItems().contains(prevItem.getProduct().toString())){
                System.out.println("Adding new item :D");
                shoppingCart.addItem(prevItem);
            }
        }



        /*
        int i = 0;
        double tempAmount = 0;
        for (ShoppingItem item: changeLater
             ) {
            if(item.getProduct().equals(increaseWith.get(i).getProduct()))
                tempAmount = increaseWith.get(i).getAmount();
                item.setAmount((item.getAmount()) + (tempAmount));
            System.out.println("PREVIOUS AMOUNT" + increaseWith.get(i).getProduct().toString() + increaseWith.get(i).getAmount());
            i++;
        }*/

        /*if(shoppingCart.getItems().isEmpty()){
            System.out.println("Added new items!");
            shoppingCart.getItems().addAll(prevPage.getRecentShoppingItems());
        }*/

        updateCart();







    }







    @FXML
    public void clearHistory() {
        db.reset();
    }


}

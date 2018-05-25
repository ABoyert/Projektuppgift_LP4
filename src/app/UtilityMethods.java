package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import se.chalmers.cse.dat216.project.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilityMethods
{
    static IMatDataHandler db = IMatDataHandler.getInstance();

    enum Categories {
        ERBJUDANDEN,
        MJOLKPRODUKTER,
        PASTA,
        POTATIS_RIS,
        KOTT,
        FISK,
        BROD,
        SKAFFERI,
        FRUKT_GRONT,
        DRYCKER,
        SOTSAKER
    }

    public void listCategory(ProductCategory pc) {
        List<Product> productList = db.getProducts(pc);

        for(Product p : productList) {
            System.out.println(p.getName());
        }
    }

    public static List<Product> getCategory(Categories c) {
        List<Product> result = null;

        switch (c) {
            case FRUKT_GRONT:
                result = db.getProducts(ProductCategory.MELONS);
                result.addAll(db.getProducts(ProductCategory.BERRY));
                result.addAll(db.getProducts(ProductCategory.CABBAGE));
                result.addAll(db.getProducts(ProductCategory.CITRUS_FRUIT));
                result.addAll(db.getProducts(ProductCategory.ROOT_VEGETABLE));
                result.addAll(db.getProducts(ProductCategory.VEGETABLE_FRUIT));
                result.addAll(db.getProducts(ProductCategory.FRUIT));
                result.addAll(db.getProducts(ProductCategory.EXOTIC_FRUIT));
                break;
            case BROD:
                result = db.getProducts(ProductCategory.BREAD);
                break;
            case DRYCKER:
                result = db.getProducts(ProductCategory.COLD_DRINKS);
                result.addAll(db.getProducts(ProductCategory.HOT_DRINKS));
                break;
            case MJOLKPRODUKTER:
                result = db.getProducts(ProductCategory.DAIRIES);
                break;
            case FISK:
                result = db.getProducts(ProductCategory.FISH);
                break;
            case SKAFFERI:
                result = db.getProducts(ProductCategory.FLOUR_SUGAR_SALT);
                result.addAll(db.getProducts(ProductCategory.HERB));
                result.addAll(db.getProducts(ProductCategory.NUTS_AND_SEEDS));
                result.addAll(db.getProducts(ProductCategory.POD));
                break;
            case KOTT:
                result = db.getProducts(ProductCategory.MEAT);
                break;
            case PASTA:
                result = db.getProducts(ProductCategory.PASTA);
                break;
            case POTATIS_RIS:
                result = db.getProducts(ProductCategory.POTATO_RICE);
                break;
            case SOTSAKER:
                result = db.getProducts(ProductCategory.SWEET);
                break;
        }

        return result;
    }

    public static void takeIntegersOnly(TextArea textArea){
    textArea.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue,
                            String newValue) {
            if (!newValue.matches("\\d*")) {
                textArea.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }
    });


}


    public static void takeLettersOnly(TextArea textArea){
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isAlpha(newValue)) {
                textArea.setText(oldValue);
            }
        });
    }

    public static void takeLettersOnlyField(TextField textArea){
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isAlpha(newValue)) {
                textArea.setText(oldValue);
            }
        });
    }

    public static boolean isNumber(String s) {
        char[] chars = s.toCharArray();

        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isAlpha(String s) {
        char[] chars = s.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }



    /*public static Image getSquareImage(Image image){

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
    }*/
}

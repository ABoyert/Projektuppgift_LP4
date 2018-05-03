package app;

import se.chalmers.cse.dat216.project.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilityMethods
{
    IMatDataHandler db = IMatDataHandler.getInstance();

    enum Categories {
        FRUKT_GRONT,
        BROD,
        DRYCKER,
        MJOLKPRODUKTER,
        FISK,
        SKAFFERI,
        KOTT,
        PASTA,
        POTATIS_RIS,
        SOTSAKER;
    }

    public void listCategory(ProductCategory pc) {
        List<Product> productList = db.getProducts(pc);

        for(Product p : productList) {
            System.out.println(p.getName());
        }
    }

    public List<Product> getCategory(Categories c) {
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


}

package app;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCartListener;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShoppingCart {
    private ArrayList<ShoppingItem> items = new ArrayList();
    private ArrayList<ShoppingCartListener> listeners = new ArrayList();

    public ShoppingCart() {
    }

    public void addItem(ShoppingItem sci) {
        this.items.add(sci);
        this.fireShoppingCartChanged(sci, true);
    }

    public void removeItem(ShoppingItem sci) {
        this.items.remove(sci);
        this.fireShoppingCartChanged(sci, false);
    }

    public void removeItem(int index) {
        ShoppingItem sci = (ShoppingItem)this.items.get(index);
        this.items.remove(sci);
        this.fireShoppingCartChanged(sci, false);
    }

    public void addProduct(Product p) {
        ShoppingItem item = new ShoppingItem(p);
        this.items.add(item);
        this.fireShoppingCartChanged(item, true);
    }

    public void addProduct(Product p, double d) {
        ShoppingItem item = new ShoppingItem(p, d);
        this.items.add(item);
        this.fireShoppingCartChanged(item, true);
    }

    public void clear() {
        this.items.clear();
        System.out.println("Clear shopping cart");
        this.fireShoppingCartChanged((ShoppingItem)null, false);
    }

    public List<ShoppingItem> getItems() {
        return this.items;
    }

    public double getTotal() {
        double total = 0.0D;

        ShoppingItem sci;
        for(Iterator var3 = this.items.iterator(); var3.hasNext(); total += sci.getTotal()) {
            sci = (ShoppingItem)var3.next();
        }

        return total;
    }

    public void addShoppingCartListener(ShoppingCartListener scl) {
        this.listeners.add(scl);
    }

    public void removeShoppingCartListener(ShoppingCartListener scl) {
        this.listeners.remove(scl);
    }

    public void fireShoppingCartChanged(ShoppingItem item, boolean addEvent) {
        CartEvent evt = new CartEvent(this);
        evt.setShoppingItem(item);
        evt.setAddEvent(addEvent);
        Iterator var4 = this.listeners.iterator();

        while(var4.hasNext()) {
            ShoppingCartListener scl = (ShoppingCartListener)var4.next();
            scl.shoppingCartChanged(evt);
        }

    }
}

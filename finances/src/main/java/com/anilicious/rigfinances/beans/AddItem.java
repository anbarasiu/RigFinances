package com.anilicious.rigfinances.beans;

/**
 * Created by ANBARASI on 19/10/14.
 */
public class AddItem {
    private String item;
    private String details;
    private int quantity;
    private int price;

    public AddItem(){
        this.item = "Some Item";
        this.details = "Details";
        this.quantity = 10;
        this.price = 100;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

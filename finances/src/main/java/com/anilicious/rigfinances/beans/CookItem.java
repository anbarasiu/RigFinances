package com.anilicious.rigfinances.beans;

/**
 * Created by ANBARASI on 14/11/14.
 */
public class CookItem {
    private String item;
    private int quantity;
    private float amount;

    public CookItem(String item, int quantity, float amount){
        this.item = item;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}

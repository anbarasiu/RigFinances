package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.List;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Cook {
    private String date;
    private String item;
    private int quantity;
    private float price;
    private String spentBy;
    private float amount;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return CommonUtils.formatDateEntry(date);
    }

    public void setDate(String date) {
        this.date = date;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }
}

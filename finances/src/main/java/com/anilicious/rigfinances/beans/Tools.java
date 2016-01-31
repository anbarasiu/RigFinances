package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

import java.util.List;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Tools {
    private Integer date;
    private String item;
    private String details;
    private int quantity;
    private float price;
    private String spentBy;
    private String company;
    private float amount;
    private float totalAmount;
    private int inserted_date;

    public Integer getInsertedDate() {
        return (inserted_date);
    }

    public void setInsertedDate(Integer inserted_date) {
        this.inserted_date = inserted_date;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getDate() {
        return (date);
    }

    public void setDate(Integer date) {
        this.date = date;
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

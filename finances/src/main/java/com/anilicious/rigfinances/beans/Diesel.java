package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Diesel {
    private Integer date;
    private String dieselFor;
    private int litres;
    private int totalAmount;
    private String spentBy;
    private int dieselInHand;

    public String getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }

    public Integer getDate() {
        return (date);
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getDieselFor() {
        return dieselFor;
    }

    public void setDieselFor(String dieselFor) {
        this.dieselFor = dieselFor;
    }

    public int getLitres() {
        return litres;
    }

    public void setLitres(int litres) {
        this.litres = litres;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getDieselInHand() {
        return dieselInHand;
    }

    public void setDieselInHand(int dieselInHand) {
        this.dieselInHand = dieselInHand;
    }
}

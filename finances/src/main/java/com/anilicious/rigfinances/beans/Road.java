package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Road {
    private Integer date;
    private String expenseDetails;
    private int totalAmount;
    private String spentBy;
    private int inserted_date;

    public Integer getInsertedDate() {
        return (inserted_date);
    }

    public void setInsertedDate(Integer inserted_date) {
        this.inserted_date = inserted_date;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getDate() {
        return (date);
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(String expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }
}

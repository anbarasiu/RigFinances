package com.anilicious.rigfinances.beans;

/**
 * Created by ANBARASI on 27/11/14.
 */
public class Site {
    private String workType;
    private String remarks;
    private float totalAmount;
    private String spentBy;
    private Integer date;
    private int inserted_date;

    public Integer getInsertedDate() {
        return (inserted_date);
    }

    public void setInsertedDate(Integer inserted_date) {
        this.inserted_date = inserted_date;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }
}

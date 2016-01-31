package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Pipe {
    private Integer date;
    private String workType;
    private Double length;
    private String type;
    private double amount;
    private String spentBy;
    private String remarks;
    private int inserted_date;

    public Integer getInsertedDate() {
        return (inserted_date);
    }

    public void setInsertedDate(Integer inserted_date) {
        this.inserted_date = inserted_date;
    }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getDate() {
        return (date);
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

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

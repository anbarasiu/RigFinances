package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Salary {
    private Integer date;
    private int employeeNumber;
    private String employeeName;
    private float amount;
    private String spentBy;
    private String reason;
    private int inserted_date;

    public Integer getInsertedDate() {
        return (inserted_date);
    }

    public void setInsertedDate(Integer inserted_date) {
        this.inserted_date = inserted_date;
    }
    public Integer getDate() {
        return (date);
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

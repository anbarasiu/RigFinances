package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Maintenance {
    private Integer date;
    private String workType;
    private boolean service;
    private double engineHrs;
    private float amount;
    private String reason;
    private String spentBy;

    public Integer getDate() {
        return (date);
    }

    public double getEngineHrs() {
        return engineHrs;
    }

    public void setEngineHrs(double engineHrs) {
        this.engineHrs = engineHrs;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSpentBy() {
        return spentBy;
    }

    public void setSpentBy(String spentBy) {
        this.spentBy = spentBy;
    }
}

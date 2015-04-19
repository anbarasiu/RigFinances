package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Credit {
    private String date;
    private float amountReceived;
    private String from;
    private String receivedBy;
    private String remarks;
    private float amountInCredit;

    public String getDate() {
        return CommonUtils.formatDateEntry(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(float amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public float getAmountInCredit() {
        return amountInCredit;
    }

    public void setAmountInCredit(float amountInCredit) {
        this.amountInCredit = amountInCredit;
    }
}

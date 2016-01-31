package com.anilicious.rigfinances.beans;

import com.anilicious.rigfinances.utils.CommonUtils;

/**
 * Created by ANBARASI on 6/11/14.
 */
public class Credit {
    private Integer date;
    private float amountReceived;
    private String from;
    private String receivedBy;
    private String remarks;
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

    public void setDate(String date) {
        String date1 = date.toString();
        String[] test=date1.split("/");
        if(test[1].length()<=1)
        {
            test[1] = "0"+test[1];
        }
        if(test[0].length()<=1)
        {
            test[0] = "0"+test[0];
        }
        String date2 =(test[2]+test[1]+test[0]);
        this.date=Integer.parseInt(date2);
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
}

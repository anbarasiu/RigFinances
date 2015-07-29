package com.anilicious.rigfinances.beans;

/**
 * Created by ANBARASI on 11/12/14.
 */
public class Bore {
    private Integer Bore_Date;
    private float totalDepth;
    private float castingDepth;
    private String engineHrsStart;  // TODO: Input field to be changed
    private String engineHrsEnd;  // TODO: Input field to be changed
    private String customerName;
    private String place;
    private String agentName;
    private String boreType;
    private float billAmount;
    private float commission;
    private float totalAmount;
    private String engineRunningTime;
    private float dieselUsed;
    private String lastEngineHrEnd;

    public Integer getDate() {
        return Bore_Date;
    }

    public void setDate(Integer Bore_Date) {
        this.Bore_Date = Bore_Date;
    }

    public float getTotalDepth() {
        return totalDepth;
    }

    public void setTotalDepth(float totalDepth) {
        this.totalDepth = totalDepth;
    }

    public float getCastingDepth() {
        return castingDepth;
    }

    public void setCastingDepth(float castingDepth) {
        this.castingDepth = castingDepth;
    }

    public String getEngineHrsStart() {
        return engineHrsStart;
    }

    public void setEngineHrsStart(String engineHrsStart) {
        this.engineHrsStart = engineHrsStart;
    }

    public String getEngineHrsEnd() {
        return engineHrsEnd;
    }

    public void setEngineHrsEnd(String engineHrsEnd) {
        this.engineHrsEnd = engineHrsEnd;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getBoreType() {
        return boreType;
    }

    public void setBoreType(String boreType) {
        this.boreType = boreType;
    }

    public float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(float billAmount) {
        this.billAmount = billAmount;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getEngineRunningTime() {
        return engineRunningTime;
    }

    public void setEngineRunningTime(String engineRunningTime) {
        this.engineRunningTime = engineRunningTime;
    }

    public float getDieselUsed() {
        return dieselUsed;
    }

    public void setDieselUsed(float dieselUsed) {
        this.dieselUsed = dieselUsed;
    }

    public String getLastEngineHrEnd() {
        return lastEngineHrEnd;
    }

    public void setLastEngineHrEnd(String lastEngineHrEnd) {
        this.lastEngineHrEnd = lastEngineHrEnd;
    }
}

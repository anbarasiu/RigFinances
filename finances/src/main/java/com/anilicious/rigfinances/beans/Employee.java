package com.anilicious.rigfinances.beans;

/**
 * Created by ANBARASI on 11/12/14.
 */
public class Employee {
    private Integer date;
    private String name;
    private int number;
    private Integer dateOfJoining;
    private Integer dateOfLeaving;
    private double currentBalance;
    private String remarks;
    private double salary;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Integer dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Integer getDateOfLeaving() {
        return dateOfLeaving;
    }

    public void setDateOfLeaving(Integer dateOfLeaving) {
        this.dateOfLeaving = dateOfLeaving;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
package com.rngesus.smartwallet;

public class Income_data {
    private String date;
    private String income;
    private String sender;
    private String reason;
    private String day;

    public Income_data(String date, String income, String sender, String reason, String day) {
        this.date = date;
        this.income = income;
        this.sender = sender;
        this.reason = reason;
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

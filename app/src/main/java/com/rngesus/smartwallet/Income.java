package com.rngesus.smartwallet;

import org.jetbrains.annotations.NotNull;

public class Income {
    private String date;
    private String amount;
    private String sender;
    private String reason;
    private String time;

    public Income(){}

    @NotNull
    @Override
    public String toString() {
        return "Income_data{" +
                "date='" + date + '\'' +
                ", amount='" + amount + '\'' +
                ", sender='" + sender + '\'' +
                ", reason='" + reason + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Income(String date, String income, String sender, String reason, String time)
    {
        this.date = date;
        this.amount = income;
        this.sender = sender;
        this.reason = reason;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

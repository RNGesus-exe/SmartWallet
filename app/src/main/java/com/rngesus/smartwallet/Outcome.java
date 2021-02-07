package com.rngesus.smartwallet;

import org.jetbrains.annotations.NotNull;

public class Outcome {
    private String date;
    private String amount;
    private String receiver;
    private String reason;
    private String time;

    public Outcome(){

    }

    @NotNull
    @Override
    public String toString() {
        return "Outcome_data{" +
                "date='" + date + '\'' +
                ", amount='" + amount + '\'' +
                ", receiver='" + receiver + '\'' +
                ", reason='" + reason + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Outcome(String date, String amount, String receiver, String reason, String time) {
        this.date = date;
        this.amount = amount;
        this.receiver = receiver;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

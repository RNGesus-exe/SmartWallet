package com.rngesus.smartwallet;

public class Outcome_data {
    private String date;
    private String outcome;
    private String receiver;
    private String reason;
    private String day;

    public Outcome_data(String date, String outcome, String receiver, String reason, String day) {
        this.date = date;
        this.outcome = outcome;
        this.receiver = receiver;
        this.reason = reason;
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }



}

package com.rngesus.smartwallet;

public class Cards {
    private  String ID;
    private int recharge;

    public Cards(String ID, int recharge) {
        this.ID = ID;
        this.recharge = recharge;
    }

    public Cards() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getRecharge() {
        return recharge;
    }

    public void setRecharge(int recharge) {
        this.recharge = recharge;
    }
}

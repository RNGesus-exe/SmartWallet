package com.rngesus.smartwallet;

public class Cards {
    String ID;
    int recharge;

    public Cards(String ID, int recharge) {
        this.ID = ID;
        this.recharge = recharge;
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

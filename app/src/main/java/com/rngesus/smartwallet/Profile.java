package com.rngesus.smartwallet;

public class Profile {
    private  String email;
    private int Amount;
    private String fullname;

    private  String documentId;

    public  String getDocumentId() {
        return documentId;
    }

    public  void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Profile(String docId, int amount) {
        this.Amount = amount;
        this.documentId = docId;
    }

    public Profile() {
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        this.Amount = amount;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String username) {
        this.fullname = username;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

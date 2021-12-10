package com.buns.mgadmin.Models.Transaction;


public class Withdrawal extends Transaction {
    private String upiId;

    public Withdrawal() {
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

}

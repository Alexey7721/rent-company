package com.company.dao;

public abstract class AbstractRentCompany implements  IRentCompany {

    private int finePercent;
    private int gasPrice;

    public AbstractRentCompany(int finePercent, int gasPrice) {
        this.finePercent = finePercent;
        this.gasPrice = gasPrice;
    }

    public AbstractRentCompany() {
        this(15, 10);
    }

    public int getFinePercent() {
        return finePercent;
    }

    public void setFinePercent(int finePercent) {
        this.finePercent = finePercent;
    }

    public int getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(int gasPrice) {
        this.gasPrice = gasPrice;
    }
}

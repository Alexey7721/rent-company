package com.company.domain;

import java.io.Serializable;

public class Model implements Serializable {
    private String modelName;
    private int gasTank;
    private String country;
    private int priceDay;
    private String company;


    public Model(String modelName, int gasTank, String country, int priceDay, String company) {
        this.modelName = modelName;
        this.gasTank = gasTank;
        this.country = country;
        this.priceDay = priceDay;
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getModelName() {
        return modelName;
    }

    public int getGasTank() {
        return gasTank;
    }

    public String getCountry() {
        return country;
    }

    public int getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(int priceDay) {
        this.priceDay = priceDay;
    }

    @Override
    public String toString() {
        return "Model{" +
                "modelName='" + modelName + '\'' +
                ", gasTank=" + gasTank +
                ", country='" + country + '\'' +
                ", priceDay=" + priceDay +
                '}';
    }
}

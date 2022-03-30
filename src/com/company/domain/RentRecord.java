package com.company.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class RentRecord implements Serializable {
    private long licenseId;
    private String regNumber;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private int gasTankPercent;
    private int rentDays;
    private double cost;
    private int damages;

    public RentRecord(long licenseId, String regNumber, LocalDate rentDate, int rentDays) {
        this.licenseId = licenseId;
        this.regNumber = regNumber;
        this.rentDate = rentDate;
        this.rentDays = rentDays;
    }

    public long getLicenseId() {
        return licenseId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getGasTankPercent() {
        return gasTankPercent;
    }

    public int getRentDays() {
        return rentDays;
    }

    public double getCost() {
        return cost;
    }

    public int getDamages() {
        return damages;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setGasTankPercent(int gasTankPercent) {
        this.gasTankPercent = gasTankPercent;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDamages(int damages) {
        this.damages = damages;
    }

    @Override
    public String toString() {
        return "RentRecord{" +
                "licenseId=" + licenseId +
                ", regNumber='" + regNumber + '\'' +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                ", gasTankPercent=" + gasTankPercent +
                ", rentDays=" + rentDays +
                ", cost=" + cost +
                ", damages=" + damages +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentRecord record = (RentRecord) o;
        return licenseId == record.licenseId &&
                gasTankPercent == record.gasTankPercent &&
                rentDays == record.rentDays &&
                Double.compare(record.cost, cost) == 0 &&
                damages == record.damages &&
                Objects.equals(regNumber, record.regNumber) &&
                Objects.equals(rentDate, record.rentDate) &&
                Objects.equals(returnDate, record.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licenseId, regNumber, rentDate, returnDate, gasTankPercent, rentDays, cost, damages);
    }
}

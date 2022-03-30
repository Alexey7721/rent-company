package com.company.domain;

import com.company.dto.State;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Car implements Serializable {
    private String regNumber;
    private String color;
    private State state;
    private String modelName;
    private boolean inUse;
    private boolean flRemoved;

    public Car(String regNumber, String color, String modelName) {
        this.regNumber = regNumber;
        this.color = color;
        this.modelName = modelName;
        state = State.EXCELLENT;
        inUse = false;
        flRemoved = false;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getColor() {
        return color;
    }

    public State getState() {
        return state;
    }

    public String getModelName() {
        return modelName;
    }

    public boolean isInUse() {
        return inUse;
    }

    public boolean isFlRemoved() {
        return flRemoved;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public void setFlRemoved(boolean flRemoved) {
        this.flRemoved = flRemoved;
    }

    @Override
    public String toString() {
        return "Car{" +
                "regNumber='" + regNumber + '\'' +
                ", color='" + color + '\'' +
                ", state=" + state +
                ", modelName='" + modelName + '\'' +
                ", inUse=" + inUse +
                ", flRemoved=" + flRemoved +
                '}';
    }
}


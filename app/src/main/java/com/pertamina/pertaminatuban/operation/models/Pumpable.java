package com.pertamina.pertaminatuban.operation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pumpable {

    @SerializedName("no_tank")
    @Expose
    private String noTank;

    @SerializedName("fuel")
    @Expose
    private String fuel;

    @SerializedName("value")
    @Expose
    private double value;

    public Pumpable(String noTank, String fuel, double value) {
        this.noTank = noTank;
        this.fuel = fuel;
        this.value = value;
    }

    public String getNoTank() {
        return noTank;
    }

    public String getFuel() {
        return fuel;
    }

    public double getValue() {
        return value;
    }
}

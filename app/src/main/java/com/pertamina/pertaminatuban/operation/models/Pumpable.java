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

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("id")
    @Expose
    private String id;

    public Pumpable() {
    }

    public Pumpable(String noTank, String fuel, double value) {
        this.noTank = noTank;
        this.fuel = fuel;
        this.value = value;
    }

    public String getNoTank() {
        return noTank;
    }

    public void setNoTank(String noTank) {
        this.noTank = noTank;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.pertamina.pertaminatuban.finance.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RealisasiAnggaran {

    @SerializedName("cost_center")
    @Expose
    private String cost_center;

    @SerializedName("cost_group")
    @Expose
    private String cost_group;

    @SerializedName("cost_elements")
    @Expose
    private String cost_elements;

    @SerializedName("actual")
    @Expose
    private double actual;

    @SerializedName("commitment")
    @Expose
    private double commitment;

    @SerializedName("allotted")
    @Expose
    private double allotted;

    @SerializedName("plan")
    @Expose
    private double plan;

    @SerializedName("available")
    @Expose
    private double available;

    public RealisasiAnggaran(String cost_center, String cost_group, String cost_elements, double actual, double commitment, double allotted, double plan, double available) {
        this.cost_center = cost_center;
        this.cost_group = cost_group;
        this.cost_elements = cost_elements;
        this.actual = actual;
        this.commitment = commitment;
        this.allotted = allotted;
        this.plan = plan;
        this.available = available;
    }

    public String getCost_center() {
        return cost_center;
    }

    public String getCost_group() {
        return cost_group;
    }

    public String getCost_elements() {
        return cost_elements;
    }

    public double getActual() {
        return actual;
    }

    public double getCommitment() {
        return commitment;
    }

    public double getAllotted() {
        return allotted;
    }

    public double getPlan() {
        return plan;
    }

    public double getAvailable() {
        return available;
    }
}

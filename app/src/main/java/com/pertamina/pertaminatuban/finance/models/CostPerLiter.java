package com.pertamina.pertaminatuban.finance.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CostPerLiter {

    @SerializedName("costPerLitre")
    @Expose
    private double costPerLiter;

    public double getCostPerLiter() {
        return costPerLiter;
    }
}

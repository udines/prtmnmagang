package com.pertamina.pertaminatuban.operation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datels {

    @SerializedName("datels")
    @Expose
    private String datels;

    public Datels(String datels) {
        this.datels = datels;
    }
}

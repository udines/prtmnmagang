package com.pertamina.pertaminatuban.finance.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NoPerjalanan {
    @SerializedName("nomor_perjalanan")
    @Expose
    private String noPerjalanan;

    public NoPerjalanan(String noPerjalanan) {
        this.noPerjalanan = noPerjalanan;
    }
}

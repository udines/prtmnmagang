package com.pertamina.pertaminatuban.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterData {

    @SerializedName("jenis")
    @Expose
    private String jenis;

    @SerializedName("variable")
    @Expose
    private String variable;

    public String getJenis() {
        return jenis;
    }

    public String getVariable() {
        return variable;
    }
}

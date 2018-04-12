package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewMarineInput {

    @SerializedName("variable")
    @Expose
    private String variable;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("nomorBl")
    @Expose
    private String nomorBl;

    @SerializedName("kapal_nama")
    @Expose
    private String kapal;

    @SerializedName("berthing_date")
    @Expose
    private String berthingDate;

    public NewMarineInput(String variable, String value, String nomorBl, String kapal, String berthingDate) {
        this.variable = variable;
        this.value = value;
        this.nomorBl = nomorBl;
        this.kapal = kapal;
        this.berthingDate = berthingDate;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNomorBl() {
        return nomorBl;
    }

    public void setNomorBl(String nomorBl) {
        this.nomorBl = nomorBl;
    }

    public String getKapal() {
        return kapal;
    }

    public void setKapal(String kapal) {
        this.kapal = kapal;
    }

    public String getBerthingDate() {
        return berthingDate;
    }

    public void setBerthingDate(String berthingDate) {
        this.berthingDate = berthingDate;
    }
}

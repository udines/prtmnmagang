package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarineInput {

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("variable")
    @Expose
    private String variable;

    @SerializedName("kapal_nama")
    @Expose
    private String kapal;

    @SerializedName("nomorBl")
    @Expose
    private String nomorBl;

    @SerializedName("berthing_date")
    @Expose
    private String date;

    @SerializedName("call_tanker")
    @Expose
    private String callTanker;

    public MarineInput(String value, String variable, String kapal, String nomorBl, String date) {
        this.value = value;
        this.variable = variable;
        this.kapal = kapal;
        this.nomorBl = nomorBl;
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getKapal() {
        return kapal;
    }

    public void setKapal(String kapal) {
        this.kapal = kapal;
    }

    public String getNomorBl() {
        return nomorBl;
    }

    public void setNomorBl(String nomorBl) {
        this.nomorBl = nomorBl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCallTanker() {
        return callTanker;
    }

    public void setCallTanker(String callTanker) {
        this.callTanker = callTanker;
    }
}

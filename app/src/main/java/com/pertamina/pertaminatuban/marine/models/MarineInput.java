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

    @SerializedName("periode")
    @Expose
    private String periode;

    @SerializedName("bulan")
    @Expose
    private String bulan;

    @SerializedName("call_tanker")
    @Expose
    private String callTanker;

    public MarineInput(String value, String variable, String kapal, String periode, String bulan, String callTanker) {
        this.value = value;
        this.variable = variable;
        this.kapal = kapal;
        this.periode = periode;
        this.bulan = bulan;
        this.callTanker = callTanker;
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

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public String getCallTanker() {
        return callTanker;
    }

    public void setCallTanker(String callTanker) {
        this.callTanker = callTanker;
    }
}

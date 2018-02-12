package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PortCharges {
    @SerializedName("unique_group")
    @Expose
    private String id;
    @SerializedName("bulan")
    @Expose
    private String bulan;
    @SerializedName("call_tanker")
    @Expose
    private String call_number;
    @SerializedName("kapal_nama")
    @Expose
    private String kapal;
    @SerializedName("periode")
    @Expose
    private String periode;


    @SerializedName("lightDues")
    @Expose
    private String lightDues;
    @SerializedName("harbourDues")
    @Expose
    private String harborDues;
    @SerializedName("quayDues")
    @Expose
    private String quayDues;
    @SerializedName("pilotages")
    @Expose
    private String pilotage;
    @SerializedName("towage")
    @Expose
    private String towage;
    @SerializedName("pup9A2")
    @Expose
    private String pup9a2;

    public PortCharges(String lightDues, String harborDues, String quayDues, String pilotage, String towage, String pup9a2) {
        this.lightDues = lightDues;
        this.harborDues = harborDues;
        this.quayDues = quayDues;
        this.pilotage = pilotage;
        this.towage = towage;
        this.pup9a2 = pup9a2;
    }

    public String getCall_number() {
        return call_number;
    }

    public String getId() {
        return id;
    }

    public String getBulan() {
        return bulan;
    }

    public String getKapal() {
        return kapal;
    }

    public String getPeriode() {
        return periode;
    }

    public String getLightDues() {
        return lightDues;
    }

    public void setLightDues(String lightDues) {
        this.lightDues = lightDues;
    }

    public String getHarborDues() {
        return harborDues;
    }

    public void setHarborDues(String harborDues) {
        this.harborDues = harborDues;
    }

    public String getQuayDues() {
        return quayDues;
    }

    public void setQuayDues(String quayDues) {
        this.quayDues = quayDues;
    }

    public String getPilotage() {
        return pilotage;
    }

    public void setPilotage(String pilotage) {
        this.pilotage = pilotage;
    }

    public String getTowage() {
        return towage;
    }

    public void setTowage(String towage) {
        this.towage = towage;
    }

    public String getPup9a2() {
        return pup9a2;
    }

    public void setPup9a2(String pup9a2) {
        this.pup9a2 = pup9a2;
    }
}

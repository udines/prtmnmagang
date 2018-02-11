package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PortCharges {
    private String id;

    @SerializedName("lightDues")
    @Expose
    private float lightDues;
    @SerializedName("harbourDues")
    @Expose
    private float harborDues;
    @SerializedName("quayDues")
    @Expose
    private float quayDues;
    @SerializedName("pilotages")
    @Expose
    private float pilotage;
    @SerializedName("towage")
    @Expose
    private float towage;
    @SerializedName("pup9A2")
    @Expose
    private float pup9a2;

    public PortCharges(float lightDues, float harborDues, float quayDues, float pilotage, float towage, float pup9a2) {
        this.lightDues = lightDues;
        this.harborDues = harborDues;
        this.quayDues = quayDues;
        this.pilotage = pilotage;
        this.towage = towage;
        this.pup9a2 = pup9a2;
    }

    public void setLightDues(float lightDues) {
        this.lightDues = lightDues;
    }

    public void setHarborDues(float harborDues) {
        this.harborDues = harborDues;
    }

    public void setQuayDues(float quayDues) {
        this.quayDues = quayDues;
    }

    public void setPilotage(float pilotage) {
        this.pilotage = pilotage;
    }

    public void setTowage(float towage) {
        this.towage = towage;
    }

    public void setPup9a2(float pup9a2) {
        this.pup9a2 = pup9a2;
    }

    public String getId() {
        return id;
    }

    public float getLightDues() {
        return lightDues;
    }

    public float getHarborDues() {
        return harborDues;
    }

    public float getQuayDues() {
        return quayDues;
    }

    public float getPilotage() {
        return pilotage;
    }

    public float getTowage() {
        return towage;
    }

    public float getPup9a2() {
        return pup9a2;
    }
}

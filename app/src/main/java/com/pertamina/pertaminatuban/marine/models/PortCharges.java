package com.pertamina.pertaminatuban.marine.models;

public class PortCharges {
    private String id;
    private String foreignKey;

    private float lightDues;
    private float harborDues;
    private float quayDues;
    private float pilotage;
    private float towage;
    private float pup9a2;

    public PortCharges(float lightDues, float harborDues, float quayDues, float pilotage, float towage, float pup9a2) {
        this.lightDues = lightDues;
        this.harborDues = harborDues;
        this.quayDues = quayDues;
        this.pilotage = pilotage;
        this.towage = towage;
        this.pup9a2 = pup9a2;
    }

    public String getId() {
        return id;
    }

    public String getForeignKey() {
        return foreignKey;
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

package com.pertamina.pertaminatuban.marine.models;

public class ShipParticular {
    private String id;
    private String foreignKey;

    private String flag;
    private float dwt;
    private float grt;
    private float loa;
    private String typeCall;
    private String typeActivity;
    private float hireRate;
    private String master;

    public void setId(String id) {
        this.id = id;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getFlag() {
        return flag;
    }

    public float getDwt() {
        return dwt;
    }

    public float getGrt() {
        return grt;
    }

    public float getLoa() {
        return loa;
    }

    public String getTypeCall() {
        return typeCall;
    }

    public String getTypeActivity() {
        return typeActivity;
    }

    public float getHireRate() {
        return hireRate;
    }

    public String getMaster() {
        return master;
    }

    public ShipParticular(String flag, float dwt, float grt, float loa, String typeCall, String typeActivity, float hireRate, String master) {
        this.flag = flag;
        this.dwt = dwt;
        this.grt = grt;
        this.loa = loa;
        this.typeCall = typeCall;
        this.typeActivity = typeActivity;
        this.hireRate = hireRate;
        this.master = master;


    }
}

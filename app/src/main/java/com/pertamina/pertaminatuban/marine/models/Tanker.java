package com.pertamina.pertaminatuban.marine.models;

public class Tanker {
    private String id;
    private String call;
    private String vesselName;

    public Tanker(String id, String call, String vesselName) {
        this.id = id;
        this.call = call;
        this.vesselName = vesselName;
    }

    public String getCall() {
        return call;
    }

    public String getVesselName() {
        return vesselName;
    }
}

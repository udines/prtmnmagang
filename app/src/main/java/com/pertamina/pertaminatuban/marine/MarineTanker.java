package com.pertamina.pertaminatuban.marine;

import java.util.List;

public class MarineTanker {
    private String call;
    private String vesselName;
    private List<String> noBill;

    public MarineTanker(String call, String vesselName, List<String> noBill) {
        this.call = call;
        this.vesselName = vesselName;
        this.noBill = noBill;
    }

    public String getCall() {
        return call;
    }

    public String getVesselName() {
        return vesselName;
    }

    public List<String> getNoBill() {
        return noBill;
    }
}

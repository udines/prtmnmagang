package com.pertamina.pertaminatuban.marine;

import java.util.ArrayList;
import java.util.List;

public class MarineTanker {
    private String call;
    private String vesselName;
    private ArrayList<String> noBill;

    public MarineTanker(String call, String vesselName, ArrayList<String> noBill) {
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

    public ArrayList<String> getNoBill() {
        return noBill;
    }
}

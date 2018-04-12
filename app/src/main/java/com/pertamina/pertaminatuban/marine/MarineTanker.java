package com.pertamina.pertaminatuban.marine;

import java.util.ArrayList;
import java.util.List;

public class MarineTanker {
    private String call;
    private String vesselName;
    private ArrayList<String> noBill;
    private String berthingDate;

    public MarineTanker(String call, String vesselName, ArrayList<String> noBill, String berthingDate) {
        this.call = call;
        this.vesselName = vesselName;
        this.noBill = noBill;
        this.berthingDate = berthingDate;
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

    public String getBerthingDate() {
        return berthingDate;
    }
}

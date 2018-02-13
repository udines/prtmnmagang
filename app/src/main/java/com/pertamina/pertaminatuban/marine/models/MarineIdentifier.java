package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarineIdentifier {
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

    public MarineIdentifier(String bulan, String call_number, String kapal, String periode) {
        this.bulan = bulan;
        this.call_number = call_number;
        this.kapal = kapal;
        this.periode = periode;
    }

    public String getBulan() {
        return bulan;
    }

    public String getCall_number() {
        return call_number;
    }

    public String getKapal() {
        return kapal;
    }

    public String getPeriode() {
        return periode;
    }
}

package com.pertamina.pertaminatuban.finance.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TarifAntarkota {

    @SerializedName("provinsi")
    @Expose
    private String provinsi;

    @SerializedName("kota")
    @Expose
    private String kota;

    @SerializedName("node")
    @Expose
    private String darike;

    @SerializedName("tarif")
    @Expose
    private long tarif;

    public TarifAntarkota(String provinsi, String kota, String darike, long tarif) {
        this.provinsi = provinsi;
        this.kota = kota;
        this.darike = darike;
        this.tarif = tarif;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public String getKota() {
        return kota;
    }

    public String getDarike() {
        return darike;
    }

    public long getTarif() {
        return tarif;
    }
}

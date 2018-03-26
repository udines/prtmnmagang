package com.pertamina.pertaminatuban.operation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistribusiBbm {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("distribusi")
    @Expose
    private String distribusi;

    @SerializedName("transaksi")
    @Expose
    private String transaksi;

    @SerializedName("fuel")
    @Expose
    private String fuel;

    @SerializedName("value")
    @Expose
    private long value;

    public static final String DIST_TANKER = "Via-Tanker";
    public static final String DIST_PIPA = "Via-Pipa";
    public static final String DIST_MOBIL = "Via-Mobil Tangki";
    public static final String TRANS_TANKER = "Tanker";
    public static final String TRANS_PIPA = "Pipe Line";
    public static final String TRANS_MOBIL = "Mobil Tangki";

    public DistribusiBbm() {
    }

    public DistribusiBbm(String date, String suplai, String transaksi, String fuel, long value) {
        this.date = date;
        this.distribusi = suplai;
        this.transaksi = transaksi;
        this.fuel = fuel;
        this.value = value;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getDistribusi() {
        return distribusi;
    }

    public String getTransaksi() {
        return transaksi;
    }

    public String getFuel() {
        return fuel;
    }

    public long getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDistribusi(String distribusi) {
        this.distribusi = distribusi;
    }

    public void setTransaksi(String transaksi) {
        this.transaksi = transaksi;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

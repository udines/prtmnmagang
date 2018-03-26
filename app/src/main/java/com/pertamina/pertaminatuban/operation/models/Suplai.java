package com.pertamina.pertaminatuban.operation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suplai {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("suplai")
    @Expose
    private String suplai;

    @SerializedName("transaksi")
    @Expose
    private String transaksi;

    @SerializedName("fuel")
    @Expose
    private String fuel;

    @SerializedName("value")
    @Expose
    private long value;

    public static final String SUP_EXTANKER = "Ex-Tanker";
    public static final String SUP_EXTPPI = "Ex-TPPI";
    public static final String SUP_EXTWU = "Ex-TWU";
    public static final String TRANS_IMPORT = "Import";
    public static final String TRANS_DOMESTIK = "Domestik";
    public static final String TRANS_PIPE = "Pipe Line";
    public static final String TRANS_MT = "Mobil Tangki";

    public Suplai() {
    }

    public Suplai(String date, String suplai, String transaksi, String fuel, long value) {
        this.date = date;
        this.suplai = suplai;
        this.transaksi = transaksi;
        this.fuel = fuel;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getSuplai() {
        return suplai;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSuplai(String suplai) {
        this.suplai = suplai;
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

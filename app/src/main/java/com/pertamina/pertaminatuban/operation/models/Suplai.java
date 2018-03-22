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
}

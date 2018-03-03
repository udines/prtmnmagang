package com.pertamina.pertaminatuban.finance.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UraianPerjalanan {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("trip_id")
    @Expose
    private String idPerjalanan;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("claim_fee")
    @Expose
    private String jumlah;

    @SerializedName("keterangan_claim")
    @Expose
    private String keterangan;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("claim_type")
    @Expose
    private String claimType;

    public UraianPerjalanan(String idPerjalanan, String date, String jumlah, String keterangan, String claimType) {
        this.idPerjalanan = idPerjalanan;
        this.date = date;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
        this.claimType = claimType;
    }

    public String getIdPerjalanan() {
        return idPerjalanan;
    }

    public String getDate() {
        return date;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getStatus() {
        return status;
    }

    public String getClaimType() {
        return claimType;
    }

    public String getId() {
        return id;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

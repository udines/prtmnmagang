package com.pertamina.pertaminatuban.saranafasilitas.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sarfas {

    public static final String PENERIMAAN = "Penerimaan";
    public static final String PENYALURAN = "Penyaluran";
    public static final String PENIMBUNAN = "Penimbunan";
    public static final String SUPPORTING = "Supporting";

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("nomor")
    @Expose
    private String nomor;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("fungsi")
    @Expose
    private String fungsi;

    @SerializedName("lokasi")
    @Expose
    private String lokasi;

    @SerializedName("tipe")
    @Expose
    private String tipe;

    @SerializedName("spesifikasi")
    @Expose
    private String spesifikasi;

    @SerializedName("merk")
    @Expose
    private String merk;

    @SerializedName("keterangan")
    @Expose
    private String keterangan;

    @SerializedName("tab")
    @Expose
    private String tab;

    public Sarfas() {
    }

    public Sarfas(String date, String nomor, String nama, String fungsi, String lokasi, String tipe, String spesifikasi, String merk, String keterangan) {
        this.date = date;
        this.nomor = nomor;
        this.nama = nama;
        this.fungsi = fungsi;
        this.lokasi = lokasi;
        this.tipe = tipe;
        this.spesifikasi = spesifikasi;
        this.merk = merk;
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFungsi() {
        return fungsi;
    }

    public void setFungsi(String fungsi) {
        this.fungsi = fungsi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getSpesifikasi() {
        return spesifikasi;
    }

    public void setSpesifikasi(String spesifikasi) {
        this.spesifikasi = spesifikasi;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }
}

package com.pertamina.pertaminatuban.finance.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class LaporanPerjalananDinas {

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("username")
    @Expose
    private String noPekerja;

    @SerializedName("id")
    @Expose
    private String noPerjalanan;

    @SerializedName("dalam_rangka")
    @Expose
    private String rangka;

    @SerializedName("tujuan")
    @Expose
    private String tujuan;

    @SerializedName("start_date")
    @Expose
    private String waktuMulai;

    @SerializedName("end_date")
    @Expose
    private String waktuSelesai;

    public LaporanPerjalananDinas(String nama, String noPekerja, String noPerjalanan, String rangka, String tujuan, String waktuMulai, String waktuSelesai) {
        this.nama = nama;
        this.noPekerja = noPekerja;
        this.noPerjalanan = noPerjalanan;
        this.rangka = rangka;
        this.tujuan = tujuan;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
    }

    public String getNama() {
        return nama;
    }

    public String getNoPekerja() {
        return noPekerja;
    }

    public String getNoPerjalanan() {
        return noPerjalanan;
    }

    public String getRangka() {
        return rangka;
    }

    public String getTujuan() {
        return tujuan;
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }
}

package com.pertamina.pertaminatuban.finance.models;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class LaporanPerjalananDinas {
    private String id;
    private String nama;
    private String noPekerja;
    private String noPerjalanan;
    private String rangka;
    private String tujuan;
    private Timestamp waktuMulai;
    private Timestamp waktuSelesai;
    private String totalBiaya;

    public LaporanPerjalananDinas(String nama, String noPekerja, String noPerjalanan, String rangka, String tujuan, Timestamp waktuMulai, Timestamp waktuSelesai, String totalBiaya) {
        this.nama = nama;
        this.noPekerja = noPekerja;
        this.noPerjalanan = noPerjalanan;
        this.rangka = rangka;
        this.tujuan = tujuan;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.totalBiaya = totalBiaya;
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

    public Timestamp getWaktuMulai() {
        return waktuMulai;
    }

    public Timestamp getWaktuSelesai() {
        return waktuSelesai;
    }

    public String getTotalBiaya() {
        return totalBiaya;
    }
}

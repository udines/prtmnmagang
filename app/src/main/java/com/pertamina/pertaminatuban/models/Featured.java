package com.pertamina.pertaminatuban.models;

public class Featured {
    private String judul;
    private String informasi;
    private String deskripsi;

    public Featured() {
    }

    public Featured(String judul, String informasi, String deskripsi) {
        this.judul = judul;
        this.informasi = informasi;
        this.deskripsi = deskripsi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}

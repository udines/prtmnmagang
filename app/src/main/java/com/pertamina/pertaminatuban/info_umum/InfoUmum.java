package com.pertamina.pertaminatuban.info_umum;

public class InfoUmum {
    private String judul;
    private String informasi;
    private String deskripsi;
    private boolean isRead;
    private String date;

    public InfoUmum() {
    }

    public InfoUmum(String judul, String informasi, String deskripsi, boolean isRead, String date) {
        this.judul = judul;
        this.informasi = informasi;
        this.deskripsi = deskripsi;
        this.isRead = isRead;
        this.date = date;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

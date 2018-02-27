package com.pertamina.pertaminatuban.info_umum;

import java.sql.Time;
import java.sql.Timestamp;

public class InfoUmum {
    private String judul;
    private String informasi;
    private String deskripsi;
    private boolean isRead;
    private String time;

    public InfoUmum() {
    }

    public InfoUmum(String judul, String informasi, String deskripsi, boolean isRead, String time) {
        this.judul = judul;
        this.informasi = informasi;
        this.deskripsi = deskripsi;
        this.isRead = isRead;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

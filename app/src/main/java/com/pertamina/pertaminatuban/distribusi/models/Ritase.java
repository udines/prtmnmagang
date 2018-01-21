package com.pertamina.pertaminatuban.distribusi.models;

public class Ritase {
    private String status;
    private String transportir;
    private String nomobil;
    private String date;
    private float kapasitas;
    private float nilai;
    private float total;

    public Ritase(String status, String transportir, String nomobil, String date, float kapasitas, float nilai, float total) {
        this.status = status;
        this.transportir = transportir;
        this.nomobil = nomobil;
        this.date = date;
        this.kapasitas = kapasitas;
        this.nilai = nilai;
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransportir() {
        return transportir;
    }

    public void setTransportir(String transportir) {
        this.transportir = transportir;
    }

    public String getNomobil() {
        return nomobil;
    }

    public void setNomobil(String nomobil) {
        this.nomobil = nomobil;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(float kapasitas) {
        this.kapasitas = kapasitas;
    }

    public float getNilai() {
        return nilai;
    }

    public void setNilai(float nilai) {
        this.nilai = nilai;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}

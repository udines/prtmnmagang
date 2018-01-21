package com.pertamina.pertaminatuban.distribusi.models;

public class Wilayah {
    private String wilayah;
    private String konsumen;
    private String fuel;
    private String date;
    private float nilai;
    private float total;

    public Wilayah(String wilayah, String konsumen, String fuel, float nilai) {
        this.wilayah = wilayah;
        this.konsumen = konsumen;
        this.fuel = fuel;
        this.nilai = nilai;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public String getKonsumen() {
        return konsumen;
    }

    public void setKonsumen(String konsumen) {
        this.konsumen = konsumen;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
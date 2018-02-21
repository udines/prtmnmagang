package com.pertamina.pertaminatuban.distribusi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Time;


public class Opers {

    public static final int TYPE_JUMLAH_KELUAR = 0;
    public static final int TYPE_MIN_JAM_KELUAR = 1;
    public static final int TYPE_MAX_JAM_KELUAR = 2;
    public static final int TYPE_JAM_OPERASIONAL = 3;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("sumJumlahKL")
    @Expose
    private int jumlahKeluar;

    @SerializedName("minJamKeluar")
    @Expose
    private String minJamKeluar;

    @SerializedName("maxJamKeluar")
    @Expose
    private String maxJamKeluar;

    /*@SerializedName("JamOperasional")
    @Expose*/
    private String jamOperasional;

    public Opers() {
    }

    public Opers(int jumlahKeluar, String minJamKeluar, String maxJamKeluar, String jamOperasional) {
        this.jumlahKeluar = jumlahKeluar;
        this.minJamKeluar = minJamKeluar;
        this.maxJamKeluar = maxJamKeluar;
        this.jamOperasional = jamOperasional;
    }

    public Opers(String date, int jumlahKeluar, String minJamKeluar, String maxJamKeluar) {
        this.date = date;
        this.jumlahKeluar = jumlahKeluar;
        this.minJamKeluar = minJamKeluar;
        this.maxJamKeluar = maxJamKeluar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getJumlahKeluar() {
        return jumlahKeluar;
    }

    public void setJumlahKeluar(int jumlahKeluar) {
        this.jumlahKeluar = jumlahKeluar;
    }

    public String getMinJamKeluar() {
        return minJamKeluar;
    }

    public void setMinJamKeluar(String minJamKeluar) {
        this.minJamKeluar = minJamKeluar;
    }

    public String getMaxJamKeluar() {
        return maxJamKeluar;
    }

    public void setMaxJamKeluar(String maxJamKeluar) {
        this.maxJamKeluar = maxJamKeluar;
    }

    public String getJamOperasional() {
        return jamOperasional;
    }

    public void setJamOperasional(String jamOperasional) {
        this.jamOperasional = jamOperasional;
    }

    public static String getTitle(int type) {
        switch (type) {
            case Opers.TYPE_JUMLAH_KELUAR:
                return ("Sum of JUMLAH KL");
            case Opers.TYPE_MIN_JAM_KELUAR:
                return ("Min of JAM KELUAR");
            case Opers.TYPE_MAX_JAM_KELUAR:
                return ("Max of JAM KELUAR");
            case Opers.TYPE_JAM_OPERASIONAL:
                return ("JAM OPERASIONAL");
            default:
                return "Lain-Lain";
        }
    }

    public String getString() {
        return String.valueOf(
                this.date + "\n" +
                this.jumlahKeluar + "\n" +
                this.minJamKeluar + "\n" +
                this.maxJamKeluar + "\n" +
                this.jamOperasional
        );
    }
}

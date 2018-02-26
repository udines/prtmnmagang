package com.pertamina.pertaminatuban.finance.models;

public class UraianPerjalanan {
    private String id;
    private String idPerjalanan;
    private String kodeAnggaran;
    private String costCenter;
    private String kodeBagian;
    private String uraian;
    private String jumlah;
    private String keterangan;

    public UraianPerjalanan(String kodeAnggaran, String costCenter, String kodeBagian, String uraian, String jumlah, String keterangan) {
        this.kodeAnggaran = kodeAnggaran;
        this.costCenter = costCenter;
        this.kodeBagian = kodeBagian;
        this.uraian = uraian;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
    }

    public String getIdPerjalanan() {
        return idPerjalanan;
    }

    public String getKodeAnggaran() {
        return kodeAnggaran;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public String getKodeBagian() {
        return kodeBagian;
    }

    public String getUraian() {
        return uraian;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }
}

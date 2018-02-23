package com.pertamina.pertaminatuban.qualityquantity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkingLoss {
    @SerializedName("month")
    @Expose
    private String date;

    @SerializedName("lokasi")
    @Expose
    private String lokasi;

    @SerializedName("produk")
    @Expose
    private String produk;

    @SerializedName("stock_awal")
    @Expose
    private String stokAwal;

    @SerializedName("act_receipt")
    @Expose
    private String actReceipt;

    @SerializedName("blending_in")
    @Expose
    private String blendingIn;

    @SerializedName("total_tersedia")
    @Expose
    private String totalTersedia;

    @SerializedName("sales_pso")
    @Expose
    private String salesPso;

    @SerializedName("sales_npso")
    @Expose
    private String salesNpso;

    @SerializedName("konsinyasi")
    @Expose
    private String konsinyasi;

    @SerializedName("ownuse_tbbm")
    @Expose
    private String ownUseTbbm;

    @SerializedName("ownuse_kapal")
    @Expose
    private String ownUseKapal;

    @SerializedName("total_penyaluran")
    @Expose
    private String penyaluran;

    @SerializedName("blending_out")
    @Expose
    private String blendingOut;

    @SerializedName("stock_akhir")
    @Expose
    private String stokAkhir;

    @SerializedName("quantity")
    @Expose
    private String wlQuantity;

    @SerializedName("percentage")
    @Expose
    private String wlPercentage;

    public WorkingLoss(String date, String lokasi, String produk, String stokAwal, String actReceipt, String blendingIn, String totalTersedia, String salesPso, String salesNpso, String konsinyasi, String ownUseTbbm, String ownUseKapal, String penyaluran, String blendingOut, String stokAkhir, String wlQuantity, String wlPercentage) {
        this.date = date;
        this.lokasi = lokasi;
        this.produk = produk;
        this.stokAwal = stokAwal;
        this.actReceipt = actReceipt;
        this.blendingIn = blendingIn;
        this.totalTersedia = totalTersedia;
        this.salesPso = salesPso;
        this.salesNpso = salesNpso;
        this.konsinyasi = konsinyasi;
        this.ownUseTbbm = ownUseTbbm;
        this.ownUseKapal = ownUseKapal;
        this.penyaluran = penyaluran;
        this.blendingOut = blendingOut;
        this.stokAkhir = stokAkhir;
        this.wlQuantity = wlQuantity;
        this.wlPercentage = wlPercentage;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getProduk() {
        return produk;
    }

    public String getStokAwal() {
        return stokAwal;
    }

    public String getActReceipt() {
        return actReceipt;
    }

    public String getBlendingIn() {
        return blendingIn;
    }

    public String getTotalTersedia() {
        return totalTersedia;
    }

    public String getSalesPso() {
        return salesPso;
    }

    public String getSalesNpso() {
        return salesNpso;
    }

    public String getKonsinyasi() {
        return konsinyasi;
    }

    public String getOwnUseTbbm() {
        return ownUseTbbm;
    }

    public String getOwnUseKapal() {
        return ownUseKapal;
    }

    public String getPenyaluran() {
        return penyaluran;
    }

    public String getBlendingOut() {
        return blendingOut;
    }

    public String getStokAkhir() {
        return stokAkhir;
    }

    public String getWlQuantity() {
        return wlQuantity;
    }

    public String getWlPercentage() {
        return wlPercentage;
    }
}

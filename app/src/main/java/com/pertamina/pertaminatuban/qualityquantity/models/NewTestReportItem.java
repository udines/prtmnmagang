package com.pertamina.pertaminatuban.qualityquantity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewTestReportItem {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("parameteruji")
    @Expose
    private String parameterUji;

    @SerializedName("hasiluji")
    @Expose
    private String hasilUji;

    @SerializedName("metode")
    @Expose
    private String metode;

    @SerializedName("units")
    @Expose
    private String unit;

    @SerializedName("limits")
    @Expose
    private String limit;

    public NewTestReportItem(String parameterUji, String hasilUji, String metode, String unit, String limit) {
        this.parameterUji = parameterUji;
        this.hasilUji = hasilUji;
        this.metode = metode;
        this.unit = unit;
        this.limit = limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterUji() {
        return parameterUji;
    }

    public void setParameterUji(String parameterUji) {
        this.parameterUji = parameterUji;
    }

    public String getHasilUji() {
        return hasilUji;
    }

    public void setHasilUji(String hasilUji) {
        this.hasilUji = hasilUji;
    }

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}

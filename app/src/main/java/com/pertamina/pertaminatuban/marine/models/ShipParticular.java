package com.pertamina.pertaminatuban.marine.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShipParticular {

    @SerializedName("unique_group")
    @Expose
    private String id;
    @SerializedName("bulan")
    @Expose
    private String bulan;
    @SerializedName("call_tanker")
    @Expose
    private String kapal;
    @SerializedName("periode")
    @Expose
    private String periode;



    @SerializedName("Flag")
    @Expose
    private String flag;
    @SerializedName("DWT")
    @Expose
    private String dwt;
    @SerializedName("GRT")
    @Expose
    private String grt;
    @SerializedName("LOA")
    @Expose
    private String loa;
    @SerializedName("typeBasedCall")
    @Expose
    private String typeCall;
    @SerializedName("typeBasedActivity")
    @Expose
    private String typeActivity;
    @SerializedName("EstHireRate")
    @Expose
    private String hireRate;
    @SerializedName("master")
    @Expose
    private String master;

    public ShipParticular(String flag, String dwt, String grt, String loa, String typeCall, String typeActivity, String hireRate, String master) {
        this.flag = flag;
        this.dwt = dwt;
        this.grt = grt;
        this.loa = loa;
        this.typeCall = typeCall;
        this.typeActivity = typeActivity;
        this.hireRate = hireRate;
        this.master = master;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDwt() {
        return dwt;
    }

    public void setDwt(String dwt) {
        this.dwt = dwt;
    }

    public String getGrt() {
        return grt;
    }

    public void setGrt(String grt) {
        this.grt = grt;
    }

    public String getLoa() {
        return loa;
    }

    public void setLoa(String loa) {
        this.loa = loa;
    }

    public String getTypeCall() {
        return typeCall;
    }

    public void setTypeCall(String typeCall) {
        this.typeCall = typeCall;
    }

    public String getTypeActivity() {
        return typeActivity;
    }

    public void setTypeActivity(String typeActivity) {
        this.typeActivity = typeActivity;
    }

    public String getHireRate() {
        return hireRate;
    }

    public void setHireRate(String hireRate) {
        this.hireRate = hireRate;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}

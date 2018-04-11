package com.pertamina.pertaminatuban.qualityquantity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewTestReportHeader {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("no")
    @Expose
    private String no;

    @SerializedName("product")
    @Expose
    private String produk;

    @SerializedName("dos")
    @Expose
    private String samplingDate;

    @SerializedName("vessel")
    @Expose
    private String vessel;

    @SerializedName("stn")
    @Expose
    private String shipTankNo;

    @SerializedName("pod")
    @Expose
    private String portDestination;

    @SerializedName("shoretank")
    @Expose
    private String shoreTank;

    @SerializedName("exref")
    @Expose
    private String exRef;

    @SerializedName("sample")
    @Expose
    private String sample;

    @SerializedName("exsample")
    @Expose
    private String exSample;

    @SerializedName("table")
    @Expose
    private ArrayList<NewTestReportItem> table;

    public NewTestReportHeader(String no, String produk, String samplingDate, String vessel, String shipTankNo, String portDestination, String shoreTank, String exRef, String sample, String exSample) {
        this.no = no;
        this.produk = produk;
        this.samplingDate = samplingDate;
        this.vessel = vessel;
        this.shipTankNo = shipTankNo;
        this.portDestination = portDestination;
        this.shoreTank = shoreTank;
        this.exRef = exRef;
        this.sample = sample;
        this.exSample = exSample;
    }

    public void addItem(NewTestReportItem item) {
        this.table.add(item);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getSamplingDate() {
        return samplingDate;
    }

    public void setSamplingDate(String samplingDate) {
        this.samplingDate = samplingDate;
    }

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }

    public String getShipTankNo() {
        return shipTankNo;
    }

    public void setShipTankNo(String shipTankNo) {
        this.shipTankNo = shipTankNo;
    }

    public String getPortDestination() {
        return portDestination;
    }

    public void setPortDestination(String portDestination) {
        this.portDestination = portDestination;
    }

    public String getShoreTank() {
        return shoreTank;
    }

    public void setShoreTank(String shoreTank) {
        this.shoreTank = shoreTank;
    }

    public String getExRef() {
        return exRef;
    }

    public void setExRef(String exRef) {
        this.exRef = exRef;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getExSample() {
        return exSample;
    }

    public void setExSample(String exSample) {
        this.exSample = exSample;
    }

    public ArrayList<NewTestReportItem> getTable() {
        return table;
    }

    public void setTable(ArrayList<NewTestReportItem> table) {
        this.table = table;
    }
}

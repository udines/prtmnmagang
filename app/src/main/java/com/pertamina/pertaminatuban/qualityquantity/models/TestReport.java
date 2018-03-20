package com.pertamina.pertaminatuban.qualityquantity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestReport {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("no")
    @Expose
    private String no;

    @SerializedName("dos")
    @Expose
    private String date;

    @SerializedName("vessel")
    @Expose
    private String vessel;

    @SerializedName("batch")
    @Expose
    private String batch;

    @SerializedName("pod")
    @Expose
    private String destination;

    @SerializedName("shoretank")
    @Expose
    private String shoreTank;

    @SerializedName("exref")
    @Expose
    private String exRefinery;

    public TestReport(String no, String date, String vessel, String batch, String destination, String shoreTank, String exRefinery) {
        this.no = no;
        this.date = date;
        this.vessel = vessel;
        this.batch = batch;
        this.destination = destination;
        this.shoreTank = shoreTank;
        this.exRefinery = exRefinery;
    }

    public String getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public String getDate() {
        return date;
    }

    public String getVessel() {
        return vessel;
    }

    public String getBatch() {
        return batch;
    }

    public String getDestination() {
        return destination;
    }

    public String getShoreTank() {
        return shoreTank;
    }

    public String getExRefinery() {
        return exRefinery;
    }
}

package com.pertamina.pertaminatuban.operation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferPipeline {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("quantity")
    @Expose
    private long quantity;

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("stop")
    @Expose
    private String stop;

    @SerializedName("batch")
    @Expose
    private int batch;

    @SerializedName("jumlah")
    @Expose
    private String jumlah;

    @SerializedName("fuel")
    @Expose
    private String fuel;

    public TransferPipeline(long quantity, String start, String stop, int batch, String jumlah, String fuel) {
        this.quantity = quantity;
        this.start = start;
        this.stop = stop;
        this.batch = batch;
        this.jumlah = jumlah;
        this.fuel = fuel;
    }

    public String getId() {
        return id;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

    public int getBatch() {
        return batch;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getFuel() {
        return fuel;
    }
}

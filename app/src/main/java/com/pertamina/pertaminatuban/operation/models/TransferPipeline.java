package com.pertamina.pertaminatuban.operation.models;

public class TransferPipeline {
    private String id;
    private long quantity;
    private String start;
    private String stop;
    private int batch;
    private String jumlah;
    private String fuel;

    public TransferPipeline(long quantity, String start, String stop, int batch, String jumlah, String fuel) {
        this.quantity = quantity;
        this.start = start;
        this.stop = stop;
        this.batch = batch;
        this.jumlah = jumlah;
        this.fuel = fuel;
    }
}

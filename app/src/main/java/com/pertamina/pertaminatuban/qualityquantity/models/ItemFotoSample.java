package com.pertamina.pertaminatuban.qualityquantity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class ItemFotoSample {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("urlGambar")
    private String url;

    @SerializedName("deskripsi")
    @Expose
    private String info;

    @SerializedName("dateTs")
    @Expose
    private Timestamp date;

    public Timestamp getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getInfo() {
        return info;
    }
}

package com.pertamina.pertaminatuban.qualityquantity.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ItemTestReport {

    public static final String TYPE_TRUCKING_LOSS = "Trucking Loss";
    public static final String TYPE_TEST_REPORT = "Test Report";

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("dateTs")
    @Expose
    private Date date;

    @SerializedName("urlPdf")
    @Expose
    private String url;

    @SerializedName("type")
    @Expose
    private String type;

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
